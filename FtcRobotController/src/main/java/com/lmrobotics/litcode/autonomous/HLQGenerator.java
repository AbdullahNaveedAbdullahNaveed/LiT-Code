package com.lmrobotics.litcode.autonomous;

import com.lmrobotics.litcode.autonomous.navigation.events.MoveEvent;
import com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase;
import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
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
public class HLQGenerator
{
    private static final boolean ALLIANCE_SWITCH_BLUE = false;
    public static boolean allianceSwitchVal = !ALLIANCE_SWITCH_BLUE;
    public static int invalidLines = 0;
    public static int invalidEvents = 0;
    @Deprecated
    public static double startX = 0;
    @Deprecated
    public static double startY = 0;
    @Deprecated
    public static double startHeading = 0;

    /** Generates the HLQ object using the specified config file.
     * <br>
     * **WARNING:** This is not yet implemented, use makeHLQFromString() instead and
     * pass in the config data as a string.
     * @param configName the name of the config file in the eventconfigs directory (without the file
     *                   extension)
     * @return the HLQ object
     */
    public static synchronized HLQ makeHLQFromFile(String configName)
    {
        String rawData = "UNLOADED FILE: " + configName;
        // TODO read data from file and format it as needed
        return makeHLQFromString(rawData);
    }

    /** Generates the HLQ object from the raw config data (data taken directly from  a config file).
     * @param rawData a one line string containing all the config data
     * @return the HLQ object
     */
    public static synchronized HLQ makeHLQFromString(String rawData)
    {
        AutoOpModeBase.telemetryAccess.addData("AS", allianceSwitchVal);
        // Build the queue
        return buildHLQ(rawData);
    }

    /** Creates a HLQ object (A ConcurrentLinkedQueue<EventBlock> object) from the raw data given.
     * @param rawData the config text as a one line string
     * @return the HLQ object
     */
    private static HLQ buildHLQ(String rawData)
    {
		AutoOpModeBase.debugHook = "HLQ Build";
        ConcurrentLinkedQueue<EventBlock> eventQueue = new ConcurrentLinkedQueue<EventBlock>();
        ConcurrentHashMap<HLQ.InitSetting, Object> initSettings = new ConcurrentHashMap<HLQ.InitSetting, Object>();
        // Remove certain whitespace characters then separate data by semicolon
        String[] lines = rawData.replaceAll("[ \n\r\t\b\f]", "").split(";");
        // The lines containing navigation event data for current block
        LinkedList<ConcurrentHashMap<String, String>> navData =
                new LinkedList<ConcurrentHashMap<String, String>>();
        // Line containing actions event data for the current block
        LinkedList<ConcurrentHashMap<String, String>> actData =
                new LinkedList<ConcurrentHashMap<String, String>>();
        for (String line : lines)
        {
            ConcurrentHashMap<String, String> keySet = makeKeySet(line);
            // A comment instruction, ignore it
            if (keySet == null)
            {
                continue;
            }
            // Initial robot data, like starting position, etc.
            else if (keySet.containsKey("INIT"))
            {
                initSettings = makeInitialSettings(keySet);
            }
            // Starting a new block, clear data from previous block
            else if (keySet.containsKey("STARTBLOCK"))
            {
                navData.clear();
                actData.clear();
            }
            // Ending current block, generate the actual EventBlock object and add it to the queue
            else if (keySet.containsKey("ENDBLOCK"))
            {
                eventQueue.add(new EventBlock(navData, actData));
            }
            // Navigation event, add it to the list of nav. event data
            else if (keySet.containsKey("NAVIGATION"))
            {
                navData.add(keySet);
            }
            // Action event, add it to the list of action event data
            else if (keySet.containsKey("ACTION"))
            {
                actData.add(keySet);
            }
            // Unknown line of config data
            else
            {
                invalidLines += 1;
                // TODO Indicate invalid line of data to user
            }
        }
        //        AutoOpModeBase.telemetryAccess.addData("INFO", "Bad -Lines:" + Integer.toString(invalidLines) + " -Events:" + Integer.toString(invalidEvents));
        return new HLQ(initSettings, eventQueue);
    }

    /**
     * @param cmdString the comma-delimited set of key and key:value pairs
     * @return a map with no-value keys (STARTBLOCK, etc) having an empty
     * 		string for a value, or null if the string indicates a comment
     */
    private static ConcurrentHashMap<String, String> makeKeySet(String cmdString)
    {
        ConcurrentHashMap<String, String> keys = new ConcurrentHashMap<String, String>();
        // String is a comment; this line should be ignored, so return null
        if (cmdString.startsWith("#!"))
        {
            AutoOpModeBase.telemetryAccess.addData("INFO", "Encountered Comment line.");
            return null;
        }
        String[] pairs = cmdString.split(",");
        for (String pair : pairs)
        {
            if (pair.contains("="))
            {
                String[] key_val = pair.split("=");
                if (key_val.length <= 1)
                {
                    AutoOpModeBase.telemetryAccess.addData(
                            "WARNING",
                            "\'"
                                    + pair
                                    + "\' is not a valid config parameter."
                    );
                    continue;
                }
                else
                {
                    keys.put(key_val[0], key_val[1]);
                }
            }
            else
            {
                keys.put(pair, "");
            }
        }
        return keys;
    }

    private static ConcurrentHashMap<HLQ.InitSetting, Object> makeInitialSettings(ConcurrentHashMap<String, String> keys)
    {
        ConcurrentHashMap<HLQ.InitSetting, Object> settings =
                new ConcurrentHashMap<HLQ.InitSetting, Object>();
        // Set the alliance init setting to the switch value
        String alliance = "red";
        if (allianceSwitchVal == ALLIANCE_SWITCH_BLUE)
        {
            alliance = "blue";
        }
        settings.put(HLQ.InitSetting.ALLIANCE, alliance);
        // All other init values
        for (HLQ.InitSetting is : HLQ.InitSetting.values())
        {
            if (keys.containsKey(is.toString()))
            {
                String keyVal = keys.get(is.toString());
                Object value;
                try
                {
                    switch (is)
                    {
                        case HEADING:
                            value = new Integer(toInteger(keyVal));
                            break;
                        case X:
                            // Fall Through
                        case Y:
                            value = new Double(toDouble(keyVal));
                            break;
                        case ALLIANCE:
                            value = new String(keyVal);
                            break;
                        default:
                            continue;
                    }
                    settings.put(is, value);
                }
                catch (NumberFormatException e)
                {
                    AutoOpModeBase.telemetryAccess.addData(
                            "WARNING",
                            "INIT value \'"
                                    + is.toString()
                                    + "="
                                    + keyVal
                                    + "\' was not formatted correctly."
                    );
                }
            }
        }
        return settings;
    }

    /** Parse and return an integer from the specified string.
     * @param fromString the string to try to parse an integer from
     * @return an integer parsed from the string
     * @throws NumberFormatException when the string is not a valid integer
     */
    public static int toInteger(String fromString) throws NumberFormatException
    {
        return Integer.parseInt(fromString);
    }

    /** Parse and return an integer from the specified string.
     * @param fromString the string to try to parse an integer from
     * @param defaultValue the default value to return if the string could not be parsed
     * @return an integer parsed from fromString or defaultValue as appropriate
     */
    public static int toInteger(String fromString, int defaultValue)
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

    /** Parse and return a double from the specified string.
     * @param fromString the string to try to parse a double from
     * @return a double parsed from the string
     * @throws NumberFormatException when the string is not a valid double
     */
    public static double toDouble(String fromString) throws NumberFormatException
    {
        return Double.parseDouble(fromString);
    }

    /** Fairly safe way to parse a string to get a double value.
     * @param fromString the string to parse
     * @param defaultValue the value to return if the string could not be parsed as a number
     * @return fromString parsed as a double, or defaultValue if fromString isn't a number
     */
    public static double toDouble(String fromString, double defaultValue)
    {
        double value;
        try
        {
            // If value contains a decimal, try to parse it as a double
            if (fromString.contains("."))
            {
                value = toDouble(fromString);
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
