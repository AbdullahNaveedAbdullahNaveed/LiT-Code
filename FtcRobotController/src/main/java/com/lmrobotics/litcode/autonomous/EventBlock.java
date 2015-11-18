package com.lmrobotics.litcode.autonomous;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.actions.BaseActionEvent;
import com.lmrobotics.litcode.autonomous.navigation.BaseNavigationEvent;

/** Represents one block of events for the High Level Queue (HLQ) in EventManager. */
public class EventBlock
{
    /** The navigation events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> navEvents;
    /** The action events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> actionEvents;

    /** Normal constructor, takes a string of xml data from inside the open/close tags for
     * eventblock.
     * @param xmlData the xml data as a String
     */
    public EventBlock(String xmlData)
    {
        // TODO separate out the navigation portion of the data
        String xmlNav = "";
        // TODO separate out actions portion of the data
        String xmlActions = "";
        // Genrate the navigation event queue
        navEvents = generateNavEvents(xmlNav);
        actionEvents = generateActionEvents(xmlActions);
    }

    /** Get the low level queue of navigation events for this event block. */
    public ConcurrentLinkedQueue<AutonomousEvent> getNavEvents()
    {
        return navEvents;
    }

    /** Get the low level queue of action events for this event block. */
    public ConcurrentLinkedQueue<AutonomousEvent> getActionEvents()
    {
        return actionEvents;
    }

    /** Generates the low level queue (LLQ) of navigation events for this block.
     * @param xmlData the xml data to generate from
     */
    private ConcurrentLinkedQueue<AutonomousEvent> generateNavEvents(String xmlData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        // TODO generate events from xml data
        return events;
    }

    /** Generates the low level queue (LLQ) of action events for this block.
     * @param xmlData the xml data to generate from
     */
    private ConcurrentLinkedQueue<AutonomousEvent> generateActionEvents(String xmlData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        // TODO generate events from xml data
        return events;
    }
}
