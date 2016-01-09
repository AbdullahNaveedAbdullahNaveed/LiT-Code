package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.navigation.events.*;

/** Represents one block of events for the High Level Queue (HLQ) in EventManager. */
public class EventBlock
{
    /** Used during event setup to indicate no valid coordinate was found. */
    private static final double INVALID_COORDINATE = -1000000000.0;
    /** Used during event setup to indicate no valid angle was found. */
    private static final int INVALID_ANGLE = -1000000000;
    /** The navigation events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> navEvents;
    /** The action events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> actionEvents;

    /** Normal constructor, takes a string of xml data from inside the open/close tags for
     * eventblock.
     * @param navData the Navigation events data
     * @param actData the Actions events data
     */
    public EventBlock(
            LinkedList<ConcurrentHashMap<String, String>> navData,
            LinkedList<ConcurrentHashMap<String, String>> actData
    )
    {
        navEvents = new ConcurrentLinkedQueue<AutonomousEvent>();
        actionEvents = new ConcurrentLinkedQueue<AutonomousEvent>();
        // Generate the navigation event queue
        navEvents = generateNavEvents(navData);
        // Generate the actions event queue
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
    private ConcurrentLinkedQueue<AutonomousEvent> generateNavEvents(
            LinkedList<ConcurrentHashMap<String, String>> navData
    )
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        // Parse each event section
        for (ConcurrentHashMap<String, String> eventData : navData)
        {
            AutonomousEvent event = generateNavEvent(eventData);
            // Valid event created, queue it
            if (event != null)
            {
                events.add(event);
            }
            else
            {
                HLQGenerator.invalidEvents += 1;
            }
        }
        return events;
    }

    /** Generates the low level queue (LLQ) of action events for this block.
     * @param actData the text data to generate from
     */
    private ConcurrentLinkedQueue<AutonomousEvent> generateActionEvents(
            LinkedList<ConcurrentHashMap<String, String>> actData
    )
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        for (ConcurrentHashMap<String, String> eventData : actData)
        {
            AutonomousEvent event = generateActionEvent(eventData);
            // Valid event created, queue it
            if (event != null)
            {
                events.add(event);
            }
        }
        return events;
    }

    private AutonomousEvent generateNavEvent(
            ConcurrentHashMap<String,
                    String> keys
    )
    {
        double maxSpeed = 1.0;
        // Get the max speed value, leave as default if not specified
        if (keys.containsKey("MAX_SPEED"))
        {
            maxSpeed = HLQGenerator.toDouble(getOrDefault(keys, "MAX_SPEED", "1.0"), 1.0);
        }
        // Check if the event uses timing or coordinates/angles
        boolean isTimeBased = keys.containsKey("TIME");
        // Get the event type
        String eventType = getOrDefault(keys, "EVENT", null);
        // Movement event data
        if (eventType.equals(MoveEvent.class.getSimpleName()))
        {
            // Create a move event using time
            if (isTimeBased)
            {
                long time;
                // Try to get the time value
                time = HLQGenerator.toInteger(getOrDefault(keys, "TIME", "0"));
                // Invalid or zero time entered
                if (time == 0)
                {
                    // TODO indicate invalid time to user
                    return null;
                }
                // Create and return the new event
                else
                {
                    return new MoveEvent(time, maxSpeed);
                }
            }
            // Create a move event using coordinates
            else
            {
                double x;
                double y;
                // Try to get the coordinates
                x = HLQGenerator.toDouble(getOrDefault(keys, "X", "ERROR"), INVALID_COORDINATE);
                y = HLQGenerator.toDouble(getOrDefault(keys, "Y", "ERROR"), INVALID_COORDINATE);
                // Invalid number entered for x
                if (x == INVALID_COORDINATE)
                {
                    // TODO indicate invalid x value to user
                    return null;
                }
                // Invalid number entered for y
                else if (y == INVALID_COORDINATE)
                {
                    // TODO indicate invalid y value to user
                    return null;
                }
                // Create and return the new event
                else
                {
                    return new MoveEvent(x, y, maxSpeed);
                }
            }
        }
        // Turn event data
        else if (eventType.equals(TurnEvent.class.getSimpleName()))
        {
            // Create a turn event using time
            if (isTimeBased)
            {
                long time;
                // Try to get the time value
                time = (long) HLQGenerator.toInteger(getOrDefault(keys, "TIME", "0"));
                // Invalid or zero time entered
                if (time == 0)
                {
                    // TODO indicate invalid time to user
                    return null;
                }
                // Create and return the new event
                else
                {
                    return new TurnEvent(maxSpeed, time);
                }
            }
            // Create a turn event using angle/heading
            else
            {
                int angle;
                // Try to get the coordinates
                angle = HLQGenerator.toInteger(getOrDefault(keys, "ANGLE", "ERROR"), INVALID_ANGLE);
                // Invalid angle entered
                if (angle == INVALID_ANGLE)
                {
                    // TODO indicate invalid angle value to user
                    return null;
                }
                // Create and return the new event
                else
                {
                    return new TurnEvent(angle, maxSpeed);
                }
            }
        }
        // Unknown navigation event type, return null
        else
        {
            return null;
        }
    }

    /** Creates and returns an action event class from the specified key data.
     * @param keys the list of key and key/value pairs to generate the event
     * @return an action event object created from the keys data
     */
    private AutonomousEvent generateActionEvent(ConcurrentHashMap<String, String> keys)
    {
        // TODO implement
        return null;
    }

    /** Gets the specified key or returns the default value if it could not be found.  This is
     * needed because this version of android does not appear to have implemented this function
     * for concurrent hash maps.
     * @param keys the hash map of keys
     * @param key the key to look for
     * @param defaultVal the value to return if key was not found
     * @return
     */
    private String getOrDefault(ConcurrentHashMap<String, String> keys, String key, String defaultVal)
    {
        // Get the value
        String val = keys.get(key);
        // Key not found, return the default value
        if (val == null)
        {
            return defaultVal;
        }
        // Key exists, return its value
        else
        {
            return val;
        }
    }
}
