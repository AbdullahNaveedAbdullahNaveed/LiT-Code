package com.lmrobotics.litcode.autonomous.navigation;

import com.lmrobotics.litcode.autonomous.navigation.events.BaseNavigationEvent;
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
    /** The object used to control the drive system. */
    private DriveSystem drive;
    private PositionSystem ps;
    private long start;

    /** Basic constructor. */
    public Navigation(HardwareMap hardwareMap)
    {
        com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode.debugHook = "Navigation Init";
        // Initialize the drive system object
        drive = new DriveSystem(hardwareMap);
        drive.setMotorsReverse();
        ps = new PositionSystem();
        SampleAutoOpMode.telemetryAccess.addData("INFO", "Navigation Initialized");
    }

    @Override
    public void init()
    {
        com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode.debugHook = "Nav Start";
        SampleAutoOpMode.telemetryAccess.addData("INFO", "Navigation Started");
    }

    @Override
    public void initEvent()
    {
        com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode.debugHook = "Nav event Init";
        getCurrentEvent().initEvent();
    }

    @Override
    public void oneCycle()
    {
        com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode.debugHook = "Nav cycle";
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
