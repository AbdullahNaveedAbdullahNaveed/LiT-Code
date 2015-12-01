package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lmrobotics.litcode.autonomous.navigation.events.*;

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
        // Parse each event section
        for (String line : navData)
        {
            // Split the data at commas
            String[] data = line.split(",");
            // Get the max speed value
            double maxSpeed = 1.0;
            try
            {
                maxSpeed = Double.parseDouble(findKeyValue(data, "MAX_SPEED", "1.0"));
            }
            catch (NumberFormatException e)
            {
                maxSpeed = 1.0;
            }
            // Navigation move event data
            if (line.contains("NAV_MOVE"))
            {
                // Timing-based movement
                if (line.contains("TIME"))
                {
                    long time = 0;
                    try
                    {
                        time = Long.parseLong(findKeyValue(data, "TIME", "0"));
                    }
                    catch (NumberFormatException e)
                    {
                        // TODO display warning for invalid time parameter
                        time = 0;
                    }
                    // Queue the event
                    events.add(new MoveEvent(time, maxSpeed));
                }
                // Coordinate-based movement
                else
                {
                    try
                    {
                        // TODO make this try to parse as integer if parseDouble fails
                        double x = Double.parseDouble(findKeyValue(data, "X"));
                        double y = Double.parseDouble(findKeyValue(data, "Y"));
                        // Queue the event
                        events.add(new MoveEvent(x, y, maxSpeed));
                    }
                    // An x or y value could not be found
                    catch (NullPointerException e)
                    {
                        // TODO display warning for invalid parameter
                    }
                    // The x or y coordinate text was not entered correctly
                    catch (NumberFormatException e)
                    {
                        // TODO display warning for invalid parameter
                    }
                }
            }
            // Navigation turn event data
            else if (line.contains("NAV_TURN"))
            {
                // Timing-based turn
                if (line.contains("TIME"))
                {
                    long time = 0;
                    try
                    {
                        time = Long.parseLong(findKeyValue(data, "TIME", "0"));
                    }
                    catch (NumberFormatException e)
                    {
                        // TODO display warning for invalid time parameter
                        time = 0;
                    }
                    events.add(new TurnEvent(maxSpeed, time));
                }
                else
                {
                    try
                    {
                        int heading = Integer.parseInt(findKeyValue(data, "HEADING"));
                        events.add(new TurnEvent(heading, maxSpeed));
                    }
                    catch (NullPointerException e)
                    {
                        // TODO display warning for invalid parameter
                    }
                    catch (NumberFormatException e)
                    {
                        // TODO display warning for invalid parameter
                    }
                }
            }
        }
        return events;
    }

    /** Generates the low level queue (LLQ) of action events for this block.
     * @param actData the text data to generate from
     */
    private ConcurrentLinkedQueue<AutonomousEvent> generateActionEvents(LinkedList<String> actData)
    {
        ConcurrentLinkedQueue<AutonomousEvent> events =
                new ConcurrentLinkedQueue<AutonomousEvent>();
        for (String line : actData)
        {
            if (line.contains("ACT_SAMPLE"))
            {
                // Do nothing for now
            }
        }
        return events;
    }

    /** Find the value for a key=value pair, returns null if value could not be found. */
    private String findKeyValue(String[] pieces, String key)
    {
        return findKeyValue(pieces, key, null);
    }

    /** Find the value for a key=value pair, takes an argument for a default value if the
     * value could not be found.
     * @param pieces the list of pieces containing at lease 1 key=value pair
     * @param key the identifier to look for
     * @param defaultValue returned if the value of the key could not be found
     * @return the value part of a key=value pair, or defaultValue if key=value is not found
     */
    private String findKeyValue(String[] pieces, String key, String defaultValue)
    {
        String value = defaultValue;
        for (String piece : pieces)
        {
            // TODO implement
        }
        return value;
    }
}
