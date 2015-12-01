package com.lmrobotics.litcode.autonomous.actions.events;

import com.lmrobotics.litcode.autonomous.AutonomousEvent;
import com.lmrobotics.litcode.autonomous.AutonomousEvent.*;

/** The base class for all action events. */
public abstract class BaseActionEvent extends AutonomousEvent
{
    /** Standard setup, takes an argument for the type of event of a child class.
     * @param type the -Type- of the event.
     */
    public BaseActionEvent(Type type)
    {
        super(type);
    }
}
