package com.lmrobotics.litcode.autonomous;

import java.util.concurrent.ConcurrentLinkedQueue;

/** The event manager is the common class used by all autonomous opmodes to control the other
 * parts of the program.  The event manager instantiates (creates an instance of) each EPS
 * (Navigation and Actions), and starts each running in its own thread.  This class uses list(s)
 * of events from a configuration file or object specified by the opmode that creates it.
 * Each configuration can contain a different combination and order of Navigation and Actions
 * events, allowing for multiple different autonomous opmodes to be created fairly easily.
 */
public class EventManager
{
    private ConcurrentLinkedQueue<EventBlock> HLQ;

    public EventManager(String configName)
    {
        HLQ = HLQGenerator.makeHLQ(configName);
    }

    /** Start the navigation and action systems. */
    public void start()
    {

    }

    /** Loop through navigation and actions and handle switching to the next event block. */
    public void loop()
    {

    }
}
