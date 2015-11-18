package com.lmrobotics.litcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.HardwareMap;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;
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

    }

    @Override
    public void init()
    {
    }

    @Override
    public void oneCycle()
    {
        // Determine which type the current event is and run it
        switch (getCurrentEvent().getType())
        {
            // Sample event
            case ACT_SAMPLE:
                // doSample((???)getCurrentEvent());
                break;
            // Unused event type, move to the next queued event
            default:
                terminateEarly();
                break;
        }
    }

    @Override
    protected EventState currentEventFinished()
    {
        // Sample event
        if (getCurrentEvent().getType() == AutonomousEvent.Type.ACT_SAMPLE)
        {
            return(getCurrentEvent().getState);
        }
        // Unknown event type, return that the current event is done
        else
        {
            return(getCurrentEvent().getState);
        }
    }
}
