package com.lmrobotics.litcode.autonomous;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Creates High-Level Queue (HLQ) objects (A ConcurrentLinkedQueue<EventBlock> object) used
 * by the EventManager to 'schedule' events in the correct order and correct timing.
 * <br>
 * <b>Config Data Formatting</b>
 * <br>
 * Notes:<br>
 * - All 'commands'/events should end with a semicolon<br>
 * - Event types, block start/stop tags, etc should be all caps<br>
 * - All event details are spearated by  commas (basically, comma-delimited formatting)<br>
 * Syntax:<br>
 * STARTBLOCK : Indicates the start of a new block of events <br>
 * ENDBLOCK : Indicates the end of all events for the current block (since the last STARTBLOCK)<br>
 * EVENT_TYPE,DATA1=123,DATA2=textdata : An event of type EVENT_TYPE with details DATA1 and DATA2<br>
 * Example:<br>
 * STARTBLOCK;<br>
 * NAV_MOVE,X=12.0,Y=13.5,MAX_SPEED=0.5;<br>
 * NAV_TURN,ANGLE=180.0,MAX_SPEED=0.5;<br>
 * ACT_MOVE_SERVO,POS=123;<br>
 * ENDBLOCK;<br>
 * STARTBLOCK;<br>
 * ACT_SCORE_CLIMBERS;<br>
 * ENDBLOCK;<br>
 */
@SuppressWarnings("SpellCheckingInspection")
public class HLQGenerator
{
    public static double startX;
    public static double startY;
    public static double startHeading;

    /** Generates the HLQ object using the specified config file.
     * <br>
     * **WARNING:** This is not yet implemented, use makeHLQFromString() instead and
     * pass in the config data as a string.
     * @param configName the name of the config file in the eventconfigs directory (without the file
     *                   extension)
     * @return the HLQ object
     */
    public static synchronized ConcurrentLinkedQueue<EventBlock> makeHLQFromFile(String configName)
    {
        String rawData = "UNLOADED FILE: " + configName;
        // TODO read data from file and format it as needed
        return makeHLQFromString(rawData);
    }

    /** Generates the HLQ object from the raw config data (data taken directly from  a config file).
     * @param rawData a one line string containing all the config data
     * @return the HLQ object
     */
    public static synchronized ConcurrentLinkedQueue<EventBlock> makeHLQFromString(String rawData)
    {
        // Build the queue
        return buildHLQ(rawData);
    }

    /** Creates a HLQ object (A ConcurrentLinkedQueue<EventBlock> object) from the raw data given.
     * @param rawData the config text as a one line string
     * @return the HLQ object
     */
    private static ConcurrentLinkedQueue<EventBlock> buildHLQ(String rawData)
    {
        ConcurrentLinkedQueue<EventBlock> newHLQ = new ConcurrentLinkedQueue<EventBlock>();
        // Separate data by semicolon
        String[] lines = rawData.split(";");
        // The lines containing navigation event data for current block
        LinkedList<String> navData = new LinkedList<String>();
        // Line containing actions event data for the current block
        LinkedList<String> actData = new LinkedList<String>();
        for (String line : lines)
        {
            // Starting a new block, clear data from previous block
            if (line.contains("STARTBLOCK"))
            {
                navData.clear();
                actData.clear();
            }
            // Ending current block, generate the actual EventBlock object and add it to the queue
            else if (line.contains("ENDBLOCK"))
            {
                newHLQ.add(new EventBlock(navData, actData));
            }
            // Otherwise add the event (if any) on that line to the data for the current queue
            else
            {
                // Check for navigation events
                for (String type : AutonomousEvent.getNavEventTypes())
                {
                    if(line.contains(type))
                    {
                        navData.add(line);
                    }
                }
                // Check for action events
                for (String type : AutonomousEvent.getActEventTypes())
                {
                    if (line.contains(type))
                    {
                        actData.add(line);
                    }
                }
            }
        }
        return newHLQ;
    }
}
