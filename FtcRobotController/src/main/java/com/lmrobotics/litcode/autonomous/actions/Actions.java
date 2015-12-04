package com.lmrobotics.litcode.autonomous.actions;

import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.lmrobotics.litcode.autonomous.EPS;

/**
 *To complete events other than navigating around on the field.
 * This can include simple actions, like moving a servo to a set
 * position, or more complex actions, like taking sensor color
 * readings and pressing the correct button on the beacon.
 * Actions could involve giving Navigation an additional
 * coordinate to move to, like moving to the left or right beacon
 * button based on sensor results.
 */
public class Actions extends EPS
{
    /** Normal constructor.
     * @param hardwareMap the device mapping used to get devices and data from
     */
    public Actions(HardwareMap hardwareMap)
    {
        SampleAutoOpMode.telemetryAccess.addData("INFO", "Actions Initialized");
    }

    @Override
    public void init()
    {
        SampleAutoOpMode.telemetryAccess.addData("INFO", "Actions Started");
    }

    @Override
    public void initEvent()
    {
    }

    @Override
    public void oneCycle()
    {
//        // Sample event
//        if (getCurrentEvent().getClass() == SampleActionEvent.class)
//        {
//             doSample((SampleActionEvent) getCurrentEvent());
//        }
//        // Unused event type, move to the next queued event
//        else
//        {
            terminateEarly();
//        }
    }

    @Override
    protected boolean currentEventFinished()
    {
//        // Sample event
//        if (getCurrentEvent().getClass() == SampleActionEvent.class)
//        {
//            return true;
//        }
//        // Unknown event type, return that the current event is done
//        else
//        {
            return true;
//        }
    }
}
