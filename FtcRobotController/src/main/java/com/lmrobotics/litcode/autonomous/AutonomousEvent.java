package com.lmrobotics.litcode.autonomous;

/** The interface for all autonomous events. */
public abstract class AutonomousEvent
{
    /** This events type. */
    protected Type type;

    // TODO find a better way to do this using try/catch
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

    /** Get what type of autonomous event this is. */
    public Type getType()
    {
        return type;
    }

    /** Set the type for this event. */
    private void setType(Type type)
    {
        this.type = type;
    }
}
