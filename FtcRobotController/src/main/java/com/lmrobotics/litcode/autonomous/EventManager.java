package com.lmrobotics.litcode.autonomous;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.navigation.Navigation;
import com.lmrobotics.litcode.autonomous.actions.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** The event manager is the common class used by all autonomous opmodes to control the other
 * parts of the program.  The event manager instantiates (creates an instance of) each EPS
 * (Navigation and Actions), and starts each running in its own thread.  This class uses list(s)
 * of events from a configuration file or object specified by the opmode that creates it.
 * Each configuration can contain a different combination and order of Navigation and Actions
 * events, allowing for multiple different autonomous opmodes to be created fairly easily.
 */
public class EventManager
{
    private ConcurrentLinkedQueue<EventBlock> HLQ;
    private Navigation navigation;
    private Actions actions;

    /** Normal constructor.
     * @param hardwareMap the device mapping used to access motors, servos, sensors, etc.
     * @param config the name of the event config file to use, or the raw config text (see
     *               the rawConfigData parameter)
     * @param rawConfigData true if the config parameter contains raw config text,
     *                      false if the config parameter contains the name of an event config file
     */
    public EventManager(HardwareMap hardwareMap, String config, boolean rawConfigData)
    {
        // Create the HLQ when we were given the raw config text
        if (rawConfigData)
        {
            HLQ = HLQGenerator.makeHLQFromString(config);
        }
        // Create the HLQ when we were given the name of a config file
        else
        {
            HLQ = HLQGenerator.makeHLQFromFile(config);
        }
        navigation = new Navigation(hardwareMap);
        actions = new Actions(hardwareMap);
    }

    public void start()
    {
        // starts navigation and action events
        navigation.start();
        actions.start();
    }

    /**
     * Loop through navigation and actions and handle switching to the next event block.
     */
    public void loop()
    {
        // runs navigation and action events until finished
        navigation.runUnlessDone();
        actions.runUnlessDone();
        // If both navigation and actions done...
        if (navigation.isWaitingForNewEvents() && actions.isWaitingForNewEvents())
        {
            EventBlock nextEB = HLQ.poll();
            // If we just finished the last block of events, stop running
            if (nextEB == null)
            {
                return;
            }
            actions.queueNextBlock(nextEB.getActionEvents());
            // queue the next block of events
            navigation.queueNextBlock(nextEB.getNavEvents());
        }
    }
}