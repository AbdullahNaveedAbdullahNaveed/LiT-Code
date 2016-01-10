package com.lmrobotics.litcode.autonomous.navigation.events;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;
import com.lmrobotics.litcode.devices.DriveSystem;

/** The base event for all navigation events. */
public abstract class BaseNavigationEvent extends AutonomousEvent
{
    /** The max drive motor power. */
    private double maxPower = 1.0;
    private long time;
    /** Easy access to the drive system. */
    protected DriveSystem drive;
    /** Indicates if this event is based on timing or coordinates. */
    private boolean usingTime = false;

    /** Basic constructor. */
    public BaseNavigationEvent(double maxPower)
    {
        drive = new DriveSystem();
        this.maxPower = maxPower;
    }

    /** Timing-based event constructor. */
    public BaseNavigationEvent(double maxPower, long time)
    {
        this(maxPower);
        this.time = time;
        usingTime = true;
    }

    /** Check if this event uses timing to move.
     * @return true if event is time-based, false otherwise
     */
    public boolean isUsingTime()
    {
        return usingTime;
    }

    /** Get the maximum power the drive motors should use during this event. */
    public double getMaxPower()
    {
        return maxPower;
    }

    /** Used with timing-based navigation to get the length to move/turn/etc. */
    public long getTime()
    {
        return time;
    }
}
