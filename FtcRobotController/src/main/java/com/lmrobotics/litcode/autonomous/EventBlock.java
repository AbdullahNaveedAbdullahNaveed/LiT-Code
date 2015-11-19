package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Represents one block of events for the High Level Queue (HLQ) in EventManager. */
public class EventBlock
{
    /** The navigation events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> navEvents;
    /** The action events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> actionEvents;

    /** Normal constructor, takes a string of xml data from inside the open/close tags for
     * eventblock.
     * @param navData the Navigation events data
     * @param actData the Actions events data
     */
    public EventBlock(LinkedList<String> navData, LinkedList<String> actData)
    {
        // Generate the navigation event queue
        navEvents = generateNavEvents(navData);
        actionEvents = generateActionEvents(actData);
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
     * @param navData the text data to generate from
     */
    private ConcurrentLinkedQueue<AutonomousEvent> generateNavEvents(LinkedList<String> navData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        // TODO generate events from data
        return events;
    }

    /** Generates the low level queue (LLQ) of action events for this block.
     * @param actData the text data to generate from
     */
    private ConcurrentLinkedQueue<AutonomousEvent> generateActionEvents(LinkedList<String> actData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        // TODO generate events from data
        return events;
    }
}
