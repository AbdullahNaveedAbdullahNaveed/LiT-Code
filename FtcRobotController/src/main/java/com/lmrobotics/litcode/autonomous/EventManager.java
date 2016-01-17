package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.navigation.Navigation;
import com.lmrobotics.litcode.autonomous.actions.Actions;
import com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase;
import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;
import com.lmrobotics.litcode.devices.DriveSystem;
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
    private HLQ HLQ;
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
        AutoOpModeBase.telemetryAccess.addData("INFO", "Initializing Event Manager...");
        // Initialize the drive system
        DriveSystem.setup(hardwareMap);
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
        AutoOpModeBase.debugHook = "EM init EPSs";
        navigation = new Navigation(hardwareMap);
        actions = new Actions(hardwareMap);
//        AutoOpModeBase.telemetryAccess.addData("INFO", "Event Manager Initialized");
    }

    public void start()
    {
        AutoOpModeBase.telemetryAccess.addData("INFO", "Starting...");
        // Starts navigation and action systems
        navigation.start();
        actions.start();
    }

    /**
     * Loop through navigation and actions and handle switching to the next event block.
     */
    public void loop()
    {
        AutoOpModeBase.debugHook = "em loop";
        // If both navigation and actions done...
        if (navigation.isWaitingForNewEvents() && actions.isWaitingForNewEvents())
        {
            // Get next event block
            EventBlock nextEB = HLQ.getNextEventBlock();
            // If we just finished the last block of events, stop running
            if (nextEB == null)
            {
                AutoOpModeBase.telemetryAccess.addData("INFO", "All Events Finshed");
                return;
            }
            // Queue the next sets of events
            actions.queueNextBlock(nextEB.getActionEvents());
            navigation.queueNextBlock(nextEB.getNavEvents());
        }
        // runs navigation and action events unless finished
        navigation.runUnlessDone();
        actions.runUnlessDone();
    }
}