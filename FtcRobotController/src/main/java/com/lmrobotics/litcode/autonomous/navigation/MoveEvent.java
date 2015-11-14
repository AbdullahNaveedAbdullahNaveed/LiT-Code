package com.lmrobotics.litcode.autonomous.navigation;

/** A basic navigation event to move from one point to another. */
public class MoveEvent extends BaseNavigationEvent
{
    /** The x coordinate. */
    private int x = 0;
    /** The y coordinate. */
    private int y = 0;

    /** Basic constructor. */
    public MoveEvent(int x, int y, int maxSpeed)
    {
        super(Type.NAV_MOVE, maxSpeed);
        // Set event attributes
        this.x = x;
        this.y = y;
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
}
