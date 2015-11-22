package com.lmrobotics.litcode.autonomous.navigation;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;

/** The base event for all navigation events. */
public class BaseNavigationEvent extends AutonomousEvent
{
    /** The max drive motor power. */
    private float maxPower = 100;
    private long time;

    /** Basic constructor. */
    public BaseNavigationEvent(Type type, float maxPower)
    {
        super(type);
        this.maxPower = maxPower;
    }

    public BaseNavigationEvent(Type type, float maxPower, long time)
    {
        this(type, maxPower);
        this.time = time;
    }

    /** Get the maximum power the drive motors should use during this event. */
    public float getMaxPower()
    {
        return maxPower;
    }

    public long getTime()
    {
        return time;
    }
}
