package com.lmrobotics.litcode.autonomous.navigation;

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
    /** The drive system used to control the drive system. */
    private DriveSystem drive;
    private PositionSystem ps;
    private long start;

    /** Basic constructor. */
    public Navigation(HardwareMap hardwareMap)
    {
        // Initialize the drive system object
        drive = new DriveSystem(hardwareMap);
        ps = new PositionSystem();
        SampleAutoOpMode.telemetryAccess.addData("INFO", "Navigation Initialized");
    }

    @Override
    public void init()
    {
        // TODO are we going to use this?
        SampleAutoOpMode.telemetryAccess.addData("INFO", "Navigation Started");
    }

    @Override
    public void initEvent()
    {
        // Used for time-based events
        start = System.currentTimeMillis();
    }

    @Override
    public void oneCycle()
    {
        // Normal coordinate movement event
        if (getCurrentEvent().getClass() == MoveEvent.class)
        {
            doMove((MoveEvent) getCurrentEvent());
        }
        // Turning on the spot
        else if (getCurrentEvent().getClass() == TurnEvent.class)
        {
            doTurn((TurnEvent) getCurrentEvent());
        }
        // Unused event type, move to the next queued event
        else
        {
            terminateEarly();
        }
    }

    @Override
    protected boolean currentEventFinished()
    {
        long now = System.currentTimeMillis();
        long elapsed = now - start;
        boolean result = false;
        SampleAutoOpMode.telemetryAccess.addData("Elapsed", elapsed);
        // Queue has been emptied
        if (getCurrentEvent() == null)
        {
            result = true;
        }
        // Moving around on the field event
        if (getCurrentEvent().getClass() == MoveEvent.class)
        {
            MoveEvent e = (MoveEvent) getCurrentEvent();
            // TODO check if the event is coordinate or time based then check if done
            if( elapsed >= Math.abs(e.getTime()))
            {
                result = true;
                drive.stopMotors();
            }
        }
        // Turning on the spot
        else if (getCurrentEvent().getClass() == TurnEvent.class)
        {
            TurnEvent e = (TurnEvent) getCurrentEvent();
            // TODO check if the event is angle or time based then check if done
            if( elapsed >= Math.abs(e.getTime()))
            {
                result = true;
                drive.stopMotors();
            }
        }
        // Unknown event type, return that the current event is done
        else
        {
            // If return false in this case then the event will never be considered complete.
            // False means event is not completed.
            result = true;
            drive.stopMotors();
        }
        return result;
    }

    /** Move around on the field, meaning move from (x1,y1) to (x2,y2).  If it is very
     * far off of the correct heading/direction, it will stop and basically do the same
     * as turning on the spot (doTurn()) until it is close enough to the desired heading.
     */
    private void doMove(MoveEvent event)
    {
        // Time-based movement
        if (event.isUsingTime())
        {
            // backwards
            if (event.getTime() < 0)
            {
                drive.setPower(-1.0f, -1.0f);
            }
            // forwards
            else
            {
                drive.setPower(1.0f, 1.0f);
            }
        }
        // Coordinate-based movement
        else
        {
            // TODO replace code with coordinate-based implementation
        }
    }

    /** Turn to a specific heading without moving forward or backward. */
    private void doTurn(TurnEvent event)
    {
        // Time-based turning
        if (event.isUsingTime())
        {
            // Left
            if (event.getTime() < 0)
            {
                drive.setPower(-1.0f, 1.0f);
            }
            // Right
            else
            {
                drive.setPower(1.0f, -1.0f);
            }
        }
        // Angle-based turning
        else
        {
            // TODO replace code with heading-based implementation
        }
    }
}
