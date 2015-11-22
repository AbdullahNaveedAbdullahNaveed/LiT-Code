package com.lmrobotics.litcode.autonomous.navigation;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;
import com.lmrobotics.litcode.autonomous.EPS;
import com.lmrobotics.litcode.devices.DriveSystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    }

    @Override
    public void init()
    {
        // TODO are we going to use this?
    }

    @Override
    public void initEvent()
    {
        start = System.currentTimeMillis();
    }

    @Override
    public void oneCycle()
    {
        // Determine which type the current event is and run it
        switch (getCurrentEvent().getType())
        {
            // Normal coordinate movement event
            case NAV_MOVE:
                doMove((MoveEvent)getCurrentEvent());
                break;
            // Turning on the spot
            case NAV_TURN:
                doTurn((TurnEvent)getCurrentEvent());
                break;
            // Unused event type, move to the next queued event
            default:
                terminateEarly();
                break;
        }
    }

    @Override
    protected boolean currentEventFinished()
    {
        // Cast the current event to a base navigation event
        BaseNavigationEvent event = (BaseNavigationEvent)getCurrentEvent();
        long now = System.currentTimeMillis();
        long elapsed = now - start;
        boolean result = false;
        // x/y coordinate movement event
        if (event.getType() == AutonomousEvent.Type.NAV_MOVE)
        {
            MoveEvent e = (MoveEvent)event;
            if( elapsed >= Math.abs(e.getTime()))
            {
                result = true;
                drive.stopMotors();
            }
        }
        // Angle turning on the spot
        else if (event.getType() == AutonomousEvent.Type.NAV_TURN)
        {
            TurnEvent e = (TurnEvent)event;
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
        // TODO replace code with legit implemention
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

    /** Turn to a specific heading without moving forward or backward. */
    private void doTurn(TurnEvent event)
    {
        // TODO replace code with legit implemention
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
}
