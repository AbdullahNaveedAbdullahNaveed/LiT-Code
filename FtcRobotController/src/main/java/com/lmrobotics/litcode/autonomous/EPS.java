package com.lmrobotics.litcode.autonomous;

import com.lmrobotics.litcode.ThreadClock;

import java.util.concurrent.ConcurrentLinkedQueue;

/** EPS – Event Processing Subsystem.  Provide a base class used by classes like Navigation
 * and Actions.  This class manages the task control aspects common to classes that process
 * actions, like queueing events, processing through the current event, etc.
 */
public abstract class EPS implements Runnable
{
    /** The thread object that will be used. */
    protected Thread thread;
    /** Thread time manager. */
    protected ThreadClock clock;
    /** The queue of events for an EPS. */
    public volatile ConcurrentLinkedQueue<AutonomousEvent> queue;
    /** If this EPS should keep running or stop. */
    protected boolean keepRunning = true;

    /** Normal EPS setup. */
    public EPS()
    {
        this(10);
    }

    /** Allows the cycle interval (milliseconds per cycle) to be changed.
     * @param cycleInterval the minimum time per cycle (in milliseconds)
     */
    public EPS(int cycleInterval)
    {
        queue = new ConcurrentLinkedQueue<AutonomousEvent>();
        clock = new ThreadClock(cycleInterval);
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
        // Start the thread execution
        thread.start();
    }

    @Override
    public void run()
    {
        while (keepRunning)
        {
            // Make sure we stop ASAP
            if (!keepRunning)
            {
                break;
            }
            // Run one iteration of the operations in the child class
            oneCycle();
            // Finish this cycle and start the next
            clock.nextCycle();
        }
    }

    /** Stop this EPS from running. */
    public void stop()
    {
        keepRunning = false;
    }
}
