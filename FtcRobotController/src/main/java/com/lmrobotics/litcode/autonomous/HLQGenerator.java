package com.lmrobotics.litcode.autonomous;

import java.util.concurrent.ConcurrentLinkedQueue;

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
        ConcurrentLinkedQueue<EventBlock> newHLQ = new ConcurrentLinkedQueue<EventBlock>();
        return newHLQ;
    }
}
