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
 * INIT,X=12,Y=13,HEADING=135 : Inital settings for position, etc. can be placed anywhere,
 *     but for cleanliness should be the first line/piece of the configuration.<br>
 * STARTBLOCK : Indicates the start of a new block of events <br>
 * ENDBLOCK : Indicates the end of all events for the current block (since the last STARTBLOCK)<br>
 * [NAVIGATION or ACTION],EVENT=[Event class name],DATA1=123,DATA2=textdata : An event of type EVENT_TYPE with details DATA1 and DATA2<br>
 * Example:<br>
 * INIT,X=12,Y=13,HEADING=135;<br>
 * STARTBLOCK;<br>
 * NAVIGATION,EVENT=MoveEvent,X=12.0,Y=13.5,MAX_SPEED=0.5;<br>
 * NAVIGATION,EVENT=TurnEvent,ANGLE=180.0,MAX_SPEED=0.5;<br>
 * ACTION,EVENT=SampleActionEvent;<br>
 * ENDBLOCK;<br>
 * STARTBLOCK;<br>
 * NAVIGATION,EVENT=MoveEvent,TIME=-100;<br>
 * ENDBLOCK;<br>
 */
@SuppressWarnings("SpellCheckingInspection")
public class HLQGenerator
{
    public static double startX = 0;
    public static double startY = 0;
    public static double startHeading = 0;

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
        // Remove certain whitespace characters then separate data by semicolon
        String[] lines = rawData.replaceAll("[ \n\r\t\b\f]", "").split(";");
        // The lines containing navigation event data for current block
        LinkedList<String[]> navData = new LinkedList<String[]>();
        // Line containing actions event data for the current block
        LinkedList<String[]> actData = new LinkedList<String[]>();
        for (String line : lines)
        {
            String[] keys = line.split(",");
            if (containsKey(keys, "INIT"))
            {
                // TODO parse line to get initial settings
            }
            // Starting a new block, clear data from previous block
            else if (containsKey(keys, "STARTBLOCK"))
            {
                navData.clear();
                actData.clear();
            }
            // Ending current block, generate the actual EventBlock object and add it to the queue
            else if (containsKey(keys, "ENDBLOCK"))
            {
                newHLQ.add(new EventBlock(navData, actData));
            }
            // Navigation event, add it to the list of nav. event data
            else if (containsKey(keys, "NAVIGATION"))
            {
                navData.add(keys);
            }
            // Action event, add it to the list of action event data
            else if (containsKey(keys, "ACTION"))
            {
                actData.add(keys);
            }
            // Unknown line of config data
            else
            {
                // TODO Indicate invalid line of data to user
            }
        }
        return newHLQ;
    }

    /** Check if the given list of keys and key:value pairs contains the specified key.
     * @param keys the list of keys and key:value pairs
     * @param key the key to look for
     * @return true if the key is in keys, false if it is not
     */
    public static boolean containsKey(String[] keys, String key)
    {
        // Search for the key
        for (String piece : keys)
        {
            String currKey;
            // A key:value pair
            if (piece.contains("="))
            {
                currKey = piece.split("=")[0];
            }
            // A key with no value
            else
            {
                currKey = piece;
            }
            // Remove whitespace characters that may have been in the key:value pair
            currKey = currKey.replace(" ", "");
            // Check if this key value is the key we are checking for
            if (currKey == key)
            {
                return true;
            }
        }
        // Key not found
        return false;
    }

    /** Find the value for a key=value pair, returns null if value could not be found.
     * @param keys the list of keys and key:value pairs
     * @param key the key to get a value from
     * @return the value associated with the key
     */
    public static String findKeyValue(String[] keys, String key)
    {
        return findKeyValue(keys, key, null);
    }

    /** Find the value for a key=value pair, takes an argument for a default value if the
     * value could not be found.
     * @param pieces the list of pieces containing at lease 1 key=value pair
     * @param key the identifier to look for
     * @param defaultValue returned if the value of the key could not be found
     * @return the value part of a key=value pair, or defaultValue if key=value is not found
     */
    public static String findKeyValue(String[] pieces, String key, String defaultValue)
    {
        String value = defaultValue;
        for (String piece : pieces)
        {
            // TODO implement
        }
        return value;
    }
}
