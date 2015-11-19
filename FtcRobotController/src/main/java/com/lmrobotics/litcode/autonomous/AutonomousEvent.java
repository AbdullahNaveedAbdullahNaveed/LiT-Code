package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;

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

    /** Gets a list of strings representing all navigation event type names.
     * @return a linked list of strings representing the corresponding types
     */
    public static LinkedList<String> getNavEventTypes()
    {
        LinkedList<String> types = new LinkedList<String>();
        types.add("NAV_MOVE");
        types.add("NAV_TURN");
        return types;
    }

    /** Gets a list of strings representing all actions event type names.
     * @return a linked list of strings representing the corresponding types
     */
    public static LinkedList<String> getActEventTypes()
    {
        LinkedList<String> types = new LinkedList<String>();
        types.add("ACT_SAMPLE");
        return types;
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
