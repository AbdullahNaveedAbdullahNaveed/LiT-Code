package com.lmrobotics.litcode.autonomous.navigation.events;

/** A basic navigation event to turn to a certain heading. */
public class TurnEvent extends BaseNavigationEvent
{
    /** The direction to turn to for this event.
     * @see com.lmrobotics.litcode.devices.HeadingSystem
     */
    private int heading = 0;

    /** Basic constructor. */
    public TurnEvent(int heading, double maxSpeed)
    {
        super(Type.NAV_TURN, maxSpeed);
        this.heading = heading;
    }

    public TurnEvent(double maxSpeed, long time)
    {
        super(Type.NAV_TURN, maxSpeed, time);
    }

    /** Return the number of encoder units required to turn to the right heading.  This should
     * only be used until we have a HeadingSystem setup.
     * @return the encoder units required to turn to the right heading (negative is CCW)
     */
    public int calcDistanceToTurn()
    {
        return 500;
    }

    /** Get the target heading for this event.  Note: Until we have a HeadingSystem setup, use
     * calcDistanceToTurn() instead of this.
     */
    public int getHeading()
    {
        return heading;
    }
}
