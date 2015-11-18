package com.lmrobotics.litcode.autonomous.navigation;

/** A basic navigation event to turn to a certain heading. */
public class TurnEvent extends BaseNavigationEvent
{
    /** The direction to turn to for this event.
     * @see com.lmrobotics.litcode.devices.HeadingSystem
     */
    private int heading = 0;

    /** Basic constructor. */
    public TurnEvent(int heading, int maxSpeed)
    {
        super(Type.NAV_TURN, maxSpeed);
        this.heading = heading;
    }

    /** Get the target heading for this event. */
    public int getHeading()
    {
        return heading;
    }
}
