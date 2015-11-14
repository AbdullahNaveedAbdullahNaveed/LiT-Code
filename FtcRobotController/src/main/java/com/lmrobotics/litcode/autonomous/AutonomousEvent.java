package com.lmrobotics.litcode.autonomous;

/** The interface for all autonomous events. */
public abstract class AutonomousEvent
{
    /** This events type. */
    protected Type type;

    /** The type of this autonomous event. */
    public enum Type
    {
        NAV_MOVE,
        NAV_TURN,
        ACT_SAMPLE
    }

    /** Basic constructor. */
    public AutonomousEvent(Type type)
    {
        setType(type);
    }

    /** Set the type for this event. */
    private void setType(Type type)
    {
        this.type = type;
    }
}
