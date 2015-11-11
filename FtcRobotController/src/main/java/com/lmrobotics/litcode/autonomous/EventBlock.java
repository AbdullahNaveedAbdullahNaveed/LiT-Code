package com.lmrobotics.litcode.autonomous;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.actions.BaseActionEvent;
import com.lmrobotics.litcode.autonomous.navigation.BaseNavigationEvent;

/** Represents one block of events for the High Level Queue (HLQ) in EventManager. */
public class EventBlock
{
    /** The navigation events for this block. */
    private ConcurrentLinkedQueue<BaseNavigationEvent> navEvents;
    /** The action events for this block. */
    private ConcurrentLinkedQueue<BaseActionEvent> actionEvents;

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
    public ConcurrentLinkedQueue<BaseNavigationEvent> getNavEvents()
    {
        return navEvents;
    }

    /** Get the low level queue of action events for this event block. */
    public ConcurrentLinkedQueue<BaseActionEvent> getActionEvents()
    {
        return actionEvents;
    }

    /** Generates the low level queue (LLQ) of navigation events for this block.
     * @param xmlData the xml data to generate from
     */
    private ConcurrentLinkedQueue<BaseNavigationEvent> generateNavEvents(String xmlData)
    {
        ConcurrentLinkedQueue<BaseNavigationEvent> events =
                new ConcurrentLinkedQueue<BaseNavigationEvent>();
        // TODO generate events from xml data
        return events;
    }

    /** Generates the low level queue (LLQ) of action events for this block.
     * @param xmlData the xml data to generate from
     */
    private ConcurrentLinkedQueue<BaseActionEvent> generateActionEvents(String xmlData)
    {
        ConcurrentLinkedQueue<BaseActionEvent> events =
                new ConcurrentLinkedQueue<BaseActionEvent>();
        // TODO generate events from xml data
        return events;
    }
}
