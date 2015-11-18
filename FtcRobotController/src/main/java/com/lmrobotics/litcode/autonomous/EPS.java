package com.lmrobotics.litcode.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.concurrent.ConcurrentLinkedQueue;

/** EPS – Event Processing Subsystem.  Provide a base class used by classes like Navigation
 * and Actions.  This class manages the task control aspects common to classes that process
 * actions, like queueing events, processing through the current event, etc.
 */
public abstract class EPS
{
    /** The queue of events for an EPS. */
    protected ConcurrentLinkedQueue<AutonomousEvent> queue;
    /** If the EPS is waiting for a new block of events to be queued. */
    private boolean waitingForNewEvents;
    /** The current event to run. */
    private AutonomousEvent currentEvent;

    /** Normal EPS setup. */
    public EPS()
    {
        queue = new ConcurrentLinkedQueue<AutonomousEvent>();
        waitForNewEvents();
    }

    /** The code that should be executed once per cycle.  Do NOT create a long loop or
     * infinite loop in this function; this will prevent the rest of the autonomous
     * program from running.
     */
    protected abstract void oneCycle();
    /** Does any startup operations specific to a subclass. */
    protected abstract void init();
    /** Check if the current event is finished. */
    protected abstract boolean currentEventFinished();

    /** Starts up the thread for this EPS and calls the subclass method for unique setup. */
    public void start()
    {
        // Initialize the child class
        init();
    }

    /** Runs one cycle of events for this system, unless this system specifies it is waiting
     * for new events to be added to its queue.
     */
    public void runUnlessDone()
    {
        if (!isWaitingForNewEvents())
        {
            run();
        }
    }

    /** Run one cycle of this system, using runUnlessDone() should be preferred/used instead. */
    public void run()
    {
        // Run one iteration of the operations in the child class
        oneCycle();
    }

    /** Checks if this EPS is waiting for new events to be added to its queue. */
    public boolean isWaitingForNewEvents()
    {
        return waitingForNewEvents;
    }

    /** Queue the next block of events for this EPS. */
    public void queueNextBlock(ConcurrentLinkedQueue<AutonomousEvent> newQueue)
    {
        // Set the new queue
        queue = newQueue;
        // Restart cycling of this system
        restartSystem();
    }

    /** Gets the current event. */
    protected AutonomousEvent getCurrentEvent()
    {
        return currentEvent;
    }

    /** Gets the next event from the queue and sets it to currentEvent. Will set the
     * current event to null if the queue is empty.
     */
    protected void setNextEvent()
    {
        // Get and set the next event
        currentEvent = queue.poll();
    }

    /** Set this system is a suspended state, which continues until new events have been queued.
     * IMPORTANT: One this method has been called, this system's oneCycle() method will NOT be
     * called again until the event manager has queued new events.
     */
    protected void waitForNewEvents()
    {
        waitingForNewEvents = true;
    }

    /** Re-starts the cycling of this system after cycling was stopped by waitForNewEvents(). */
    private void restartSystem()
    {
        waitingForNewEvents = false;
    }
}
