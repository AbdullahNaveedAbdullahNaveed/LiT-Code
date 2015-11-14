package com.lmrobotics.litcode.autonomous.navigation;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;
import com.lmrobotics.litcode.autonomous.EPS;
import com.lmrobotics.litcode.devices.DriveSystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * TODO document this
 */
public class Navigation extends EPS
{
    /** The drive system used to control the drive system. */
    private DriveSystem drive;

    public Navigation(HardwareMap hardwareMap)
    {
        drive = new DriveSystem(hardwareMap);
    }

    @Override
    public void init()
    {
        // Set the first event
        setNextEvent();
    }

    @Override
    public void oneCycle()
    {
        // If the current event is none (our queue is empty), wait for new events
        if (getCurrentEvent() == null)
        {
            waitForNewEvents();
        }
        // Check if the current event is finished, and move to the next one
        else if (currentEventFinished())
        {
            setNextEvent();
        }
        // Otherwise do a cycle for the current event
        else
        {
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
                    setNextEvent();
                    break;
            }
        }
    }

    @Override
    protected boolean currentEventFinished()
    {
        // x/y coordinate movement event
        if (getCurrentEvent().getType() == AutonomousEvent.Type.NAV_MOVE)
        {
            // TODO implement checking to see if event is done
            return true;
        }
        // Angle turning on the spot
        else if (getCurrentEvent().getType() == AutonomousEvent.Type.NAV_TURN)
        {
            // TODO implement checking to see if event is done
            return true;
        }
        // Unknown event type, return that the current event is done
        else
        {
            return true;
        }
    }

    /** Move around on the field, meaning move from (x1,y1) to (x2,y2).  If it is very
     * far off of the correct heading/direction, it will stop and basically do the same
     * as turning on the spot (doTurn()) until it is close enough to the desired heading.
     */
    private void doMove(MoveEvent event)
    {
        // TODO implement moving around
    }

    /** Turn to a specific heading without moving forward or backward. */
    private void doTurn(TurnEvent event)
    {
        // TODO implement turning on the spot
    }
}
