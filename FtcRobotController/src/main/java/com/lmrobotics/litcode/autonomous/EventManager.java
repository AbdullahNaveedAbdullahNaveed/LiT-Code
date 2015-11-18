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
     * @param configName the name of the event config to use
     *                   in the 'eventconfigs' folder
     */
    public EventManager(String configName, HardwareMap hardwareMap)
    {
        // Setup the HLQ
        HLQ = HLQGenerator.makeHLQ(configName);
        navigation = new Navigation(hardwareMap);
        actions = new Actions(hardwareMap);
    }

    /**
     * Loop through navigation and actions and handle switching to the next event block.
     */
    public void start()
    {
        // starts navigation and action events
        navigation.start();
        actions.start();
    }

    public void loop()
    {
        // runs navigation and action events until finished
        navigation.runUnlessDone();
        actions.runUnlessDone();
        // If both navigation and actions done...
        if (navigation.isWaitingForNewEvents() && actions.isWaitingForNewEvents())
        {
            EventBlock nextEB = HLQ.poll();
            actions.queueNextBlock(nextEB.getActionEvents());
            // queue the next block of events
            navigation.queueNextBlock(nextEB.getNavEvents());
        }
    }
}