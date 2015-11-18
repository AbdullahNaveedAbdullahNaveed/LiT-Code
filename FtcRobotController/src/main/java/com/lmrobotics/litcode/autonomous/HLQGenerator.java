package com.lmrobotics.litcode.autonomous;

import android.util.Xml;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Bryan on 11/7/2015.
 */
public class HLQGenerator
{
    /** Generates the HLQ object using the specified config file.
     * @param configName the name of the config file (relative to the config dir and
     *                   minus the extension
     * @return the HLQ object
     */
    public static ConcurrentLinkedQueue<EventBlock> makeHLQ(String configName)
    {
        XmlPullParser parser = Xml.newPullParser();
        try
        {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
        }
        catch (Exception e)
        {
            System.out.println("Unable to load event config file: '" + configName + "'");
        }
        return buildHLQ(readFeed(parser));
    }

    private static ConcurrentLinkedQueue<EventBlock> buildHLQ(List xmlData)
    {
        ConcurrentLinkedQueue<EventBlock> newHLQ = new ConcurrentLinkedQueue<EventBlock>();
        return newHLQ;
    }

    private static List readFeed(XmlPullParser parser)
    {
        List entries = new ArrayList();
        return entries;
    }
}
