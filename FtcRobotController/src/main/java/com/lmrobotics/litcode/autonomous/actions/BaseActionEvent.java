package com.lmrobotics.litcode.autonomous.actions;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;

/** The base class for all action events. */
public abstract class BaseActionEvent implements AutonomousEvent
{
    protected Type type;
    public enum Type
    {
        NO_TYPE
    }

    /** Standard setup, takes an argument for the type of event of a child class.
     * @param type the -Type- of the event.
     */
    public BaseActionEvent(Type type)
    {
        setType(type);
    }

    /** Set the type for this event. */
    private void setType(Type type)
    {
        this.type = type;
    }
}
