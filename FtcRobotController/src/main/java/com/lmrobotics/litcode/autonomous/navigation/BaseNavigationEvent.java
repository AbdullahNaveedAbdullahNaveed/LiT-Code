package com.lmrobotics.litcode.autonomous.navigation;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;

/** The base event for all navigation events. */
public class BaseNavigationEvent extends AutonomousEvent
{
    /** The max drive motor power. */
    private int maxPower = 100;

    /** Basic constructor. */
    public BaseNavigationEvent(Type type, int maxPower)
    {
        super(type);
        this.maxPower = maxPower;
    }

    /** Get the maximum power the drive motors should use during this event. */
    public int getMaxPower()
    {
        return maxPower;
    }
}
