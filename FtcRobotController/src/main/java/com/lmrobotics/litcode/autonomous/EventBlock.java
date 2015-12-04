package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.navigation.events.*;
import com.lmrobotics.litcode.autonomous.HLQGenerator;

/** Represents one block of events for the High Level Queue (HLQ) in EventManager. */
public class EventBlock
{
    /** Used during event setup to indicate no valid coordinate was found. */
    private static final double INVALID_COORDINATE = -1000000000.0;
    /** Used during event setup to indicate no valid angle was found. */
    private static final int INVALID_ANGLE = (int) INVALID_COORDINATE;
    /** The navigation events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> navEvents;
    /** The action events for this block. */
    private ConcurrentLinkedQueue<AutonomousEvent> actionEvents;

    /** Normal constructor, takes a string of xml data from inside the open/close tags for
     * eventblock.
     * @param navData the Navigation events data
     * @param actData the Actions events data
     */
    public EventBlock(LinkedList<String[]> navData, LinkedList<String[]> actData)
    {
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
    private ConcurrentLinkedQueue<AutonomousEvent> generateNavEvents(LinkedList<String[]> navData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        events.add(new MoveEvent(2000, 1.0));
        // Parse each event section
        for (String[] eventData : navData)
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
    private ConcurrentLinkedQueue<AutonomousEvent> generateActionEvents(LinkedList<String[]> actData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        for (String[] eventData : actData)
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

    private AutonomousEvent generateNavEvent(String[] keys)
    {
        double maxSpeed = 1.0;
        // Get the max speed value, leave as default if not specified
        if (HLQGenerator.containsKey(keys, "MAX_SPEED"))
        {
            maxSpeed = toDouble(HLQGenerator.findKeyValue(keys, "MAX_SPEED", "1.0"), 1.0);
        }
        // Check if the event uses timing or coordinates/angles
        boolean isTimeBased = HLQGenerator.containsKey(keys, "TIME");
        // Get the event type
        String eventType = HLQGenerator.findKeyValue(keys, "EVENT");
        // Movement event data
        if (eventType == MoveEvent.class.getSimpleName())
        {
            // Create a move event using time
            if (isTimeBased)
            {
                long time;
                // Try to get the time value
                time = toInteger(HLQGenerator.findKeyValue(keys, "TIME", "0"));
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
                x = toDouble(HLQGenerator.findKeyValue(keys, "X", "ERROR"), INVALID_COORDINATE);
                y = toDouble(HLQGenerator.findKeyValue(keys, "Y", "ERROR"), INVALID_COORDINATE);
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
        else if (eventType == TurnEvent.class.getSimpleName())
        {
            // Create a turn event using time
            if (isTimeBased)
            {
                long time;
                // Try to get the time value
                time = toInteger(HLQGenerator.findKeyValue(keys, "TIME", "0"));
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
                angle = toInteger(HLQGenerator.findKeyValue(keys, "ANGLE", "ERROR"), INVALID_ANGLE);
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
    private AutonomousEvent generateActionEvent(String[] keys)
    {
        // TODO implement
        return null;
    }

    /** Parse and return an integer from the specified string.
     * @param fromString the string to try to parse an integer from
     * @return an integer parsed from the string
     * @throws NumberFormatException when the string is not a valid integer
     */
    private int toInteger(String fromString) throws NumberFormatException
    {
        return Integer.parseInt(fromString);
    }

    /** Parse and return an integer from the specified string.
     * @param fromString the string to try to parse an integer from
     * @param defaultValue the default value to return if the string could not be parsed
     * @return an integer parsed from fromString or defaultValue as appropriate
     */
    private int toInteger(String fromString, int defaultValue)
    {
        try
        {
            return toInteger(fromString);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    /** Fairly safe way to parse a string to get a double value.
     * @param fromString the string to parse
     * @param defaultValue the value to return if the string could not be parsed as a number
     * @return fromString parsed as a double, or defaultValue if fromString isn't a number
     */
    private double toDouble(String fromString, double defaultValue)
    {
        double value;
        try
        {
            // If value contains a decimal, try to parse it as a double
            if (fromString.contains("."))
            {
                value = Double.parseDouble(fromString);
            }
            // Doesn't contain a decimal, try to parse it as an integer
            else
            {
                value = (double) toInteger(fromString);
            }
        }
        // Invalid number, return the specified default value
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
        return value;
    }
}
