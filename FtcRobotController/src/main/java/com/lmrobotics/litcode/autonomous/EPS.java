package com.lmrobotics.litcode.autonomous;

import java.util.concurrent.ConcurrentLinkedQueue;

/** EPS – Event Processing Subsystem.  Provide a base class used by classes like Navigation
 * and Actions.  This class manages the task control aspects common to classes that process
 * actions, like queueing events, processing through the current event, etc.
 */
public abstract class EPS implements Runnable
{
    /** The queue of events for an EPS. */
    public volatile ConcurrentLinkedQueue<AutonomousEvent> queue;

    /** Normal EPS setup. */
    public EPS()
    {
        queue = new ConcurrentLinkedQueue<AutonomousEvent>();
    }

    /** The code that should be executed once per cycle.  This is called iteratively from
     * another method, so do NOT create a long/infinite loop inside of it.
     */
    public abstract void oneCycle();
    /** Does any startup operations specific to a subclass. */
    public abstract void init();

    /** Starts up the thread for this EPS and calls the subclass method for unique setup. */
    public void start()
    {
        // Initialize the child class
        init();
    }

    @Override
    public void run()
    {
        // Run one iteration of the operations in the child class
        oneCycle();
    }

    /** Queue the next block of events for this EPS. */
    public void queueNextBlock(ConcurrentLinkedQueue<AutonomousEvent> newQueue)
    {
        queue = newQueue;
    }
}
