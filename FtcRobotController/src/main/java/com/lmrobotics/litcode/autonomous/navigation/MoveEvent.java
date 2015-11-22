package com.lmrobotics.litcode.autonomous.navigation;

/** A basic navigation event to move from one point to another. */
public class MoveEvent extends BaseNavigationEvent
{
    /** The x coordinate. */
    private int x = 0;
    /** The y coordinate. */
    private int y = 0;

    /** Basic constructor. */
    public MoveEvent(int x, int y, float maxSpeed)
    {
        super(Type.NAV_MOVE, maxSpeed);
        // Set event attributes
        this.x = x;
        this.y = y;
    }

    public MoveEvent(long time, float maxSpeed)
    {
        super(Type.NAV_MOVE, maxSpeed, time);
    }

    /** Get the target x coordinate of this event. */
    public int getX()
    {
        return x;
    }

    /** Get the target y coordinate of this event. */
    public int getY()
    {
        return y;
    }

    /** Calculate the distance in encoder units.
     * @return the distance in units directly usable with the drive motors.
     */
    public int calcDistance()
    {
        return 1234;
    }

    /** Return the number of encoder units required to turn to the right heading.  This should
     * only be used until we have a HeadingSystem setup.
     * @return the encoder units required to turn to the right heading (negative is CCW)
     */
    public int calcDistanceToTurn()
    {
        return 500;
    }

    /** Calculate the direction to travel in.  Note: Until we have a HeadingSystem setup, use
     * calcDistanceToTurn() instead.
     * @return the heading in degrees, 0 is horizontal-right and CCW is positive
     * (standard unit circle setup).
     */
    public int calcHeading()
    {
        return 180;
    }
}
