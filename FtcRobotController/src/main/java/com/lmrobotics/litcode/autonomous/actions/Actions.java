package com.lmrobotics.litcode.autonomous.actions;

import com.lmrobotics.litcode.autonomous.actions.events.BaseActionEvent;
import com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase;
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
        AutoOpModeBase.debugHook = "Act Init";
        AutoOpModeBase.telemetryAccess.addData("INFO", "Actions Initialized");
    }

    @Override
    public void init()
    {
        AutoOpModeBase.debugHook = "Act Start";
        AutoOpModeBase.telemetryAccess.addData("INFO", "Actions Started");
    }

    @Override
    public void initEvent()
    {
        AutoOpModeBase.debugHook = "Act event Init";
        getCurrentEvent().initEvent();
    }

    @Override
    public void oneCycle()
    {
        AutoOpModeBase.debugHook = "Act cycle";
        // Invalid action event, terminate early and return
        if (getCurrentEvent() == null || !(getCurrentEvent() instanceof BaseActionEvent))
        {
            terminateEarly();
            return;
        }
        // Otherwise run one cycle of the event
        else
        {
            getCurrentEvent().oneCycle();
        }
    }

    @Override
    protected boolean currentEventFinished()
    {
        boolean result = false;
        // No event set
        if (getCurrentEvent() == null || !(getCurrentEvent() instanceof BaseActionEvent))
        {
            result = true;
        }
        // Ask the current event if it is finished
        else
        {
            result = getCurrentEvent().isFinished();
        }
        return result;
    }
}
