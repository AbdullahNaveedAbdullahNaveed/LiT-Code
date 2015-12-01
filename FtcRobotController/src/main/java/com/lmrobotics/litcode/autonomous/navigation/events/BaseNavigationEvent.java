package com.lmrobotics.litcode.autonomous.navigation.events;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;

/** The base event for all navigation events. */
public class BaseNavigationEvent extends AutonomousEvent
{
    /** The max drive motor power. */
    private double maxPower = 1.0;
    private long time;
    /** Indicates if this event is based on timing or coordinates. */
    private boolean usingTime = false;

    /** Basic constructor. */
    public BaseNavigationEvent(Type type, double maxPower)
    {
        super(type);
        this.maxPower = maxPower;
    }

    /** Timing-based event constructor. */
    public BaseNavigationEvent(Type type, double maxPower, long time)
    {
        this(type, maxPower);
        this.time = time;
        usingTime = true;
    }

    public boolean isUsingTime()
    {
        return usingTime;
    }

    /** Get the maximum power the drive motors should use during this event. */
    public double getMaxPower()
    {
        return maxPower;
    }

    public long getTime()
    {
        return time;
    }
}
