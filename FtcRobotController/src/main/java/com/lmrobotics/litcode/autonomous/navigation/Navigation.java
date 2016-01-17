package com.lmrobotics.litcode.autonomous.navigation;

import com.lmrobotics.litcode.autonomous.navigation.events.BaseNavigationEvent;
import com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase;
import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.lmrobotics.litcode.autonomous.EPS;
import com.lmrobotics.litcode.autonomous.navigation.events.MoveEvent;
import com.lmrobotics.litcode.autonomous.navigation.events.TurnEvent;
import com.lmrobotics.litcode.devices.DriveSystem;

/**
 * To move the robot around on the field.  The Navigation class receives navigation events
 * from the task controller.  The data received (contained inside an object) will include
 * data like target coordinates/angles and max speed.  Navigation will also have to deal
 * with reflecting coordinates and angles depending on which alliance we are on, due to how
 * the field is symmetrical over a center line instead of rotationally symmetrical
 * (like in previous years).
 */
public class Navigation extends EPS
{
    private PositionSystem ps;

    /** Basic constructor. */
    public Navigation(HardwareMap hardwareMap)
    {
        AutoOpModeBase.debugHook = "Navigation Init";
        ps = new PositionSystem();
        AutoOpModeBase.telemetryAccess.addData("INFO", "Navigation Initialized");
    }

    @Override
    public void init()
    {
        AutoOpModeBase.debugHook = "Nav Start";
        AutoOpModeBase.telemetryAccess.addData("INFO", "Navigation Started");
    }

    @Override
    public void initEvent()
    {
        AutoOpModeBase.debugHook = "Nav event Init";
        getCurrentEvent().initEvent();
    }

    @Override
    public void oneCycle()
    {
        AutoOpModeBase.debugHook = "Nav cycle";
        // Invalid navigation event, terminate early and return
        if (getCurrentEvent() == null || !(getCurrentEvent() instanceof BaseNavigationEvent))
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
        if (getCurrentEvent() == null || !(getCurrentEvent() instanceof BaseNavigationEvent))
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
