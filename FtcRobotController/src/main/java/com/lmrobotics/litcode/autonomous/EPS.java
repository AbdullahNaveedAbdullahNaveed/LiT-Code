package com.lmrobotics.litcode.autonomous;

import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;

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
    private boolean waitingForNewEvents = false;
    /** The current event to run. */
    private AutonomousEvent currentEvent;
    /** Used to indicate when the current event should terminate early regardless of if
     * it is done.
     */
    private boolean terminateEarly = false;

    /** Normal EPS setup. */
    public EPS()
    {
        queue = new ConcurrentLinkedQueue<AutonomousEvent>();
    }

    /** The code that should be executed once per cycle.  Do NOT create a long loop or
     * infinite loop in this function; this will prevent the rest of the autonomous
     * program from running.
     */
    protected abstract void oneCycle();
    /** Does any startup operations specific to a subclass. */
    protected abstract void init();
    /** Do any startup for a specific event. */
    protected abstract void initEvent();
    /** Check if the current event is finished. Should also do anything to "clean up"
     * after a finished event, like stopping motors or resetting certain values.
     */
    protected abstract boolean currentEventFinished();

    /** Starts up the thread for this EPS and calls the subclass method for unique setup. */
    public void start()
    {
        // Set the first event
        setNextEvent();
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
        // Check if the current event is finished, and move to the next one
        if (currentEventFinished() || terminateEarly)
        {
            // Set the next event
            setNextEvent();
            // Queue has emptied, return
            if (getCurrentEvent() == null)
            {
                waitForNewEvents();
                return;
            }
            // initialize the new event
            initEvent();
            SampleAutoOpMode.telemetryAccess.addData("INFO", "Starting Event: " + getCurrentEvent().getClass().getSimpleName());
        }
        // Run one cycle
        else
        {
            // Child class cycling
            oneCycle();
        }
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
        setNextEvent();
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
        terminateEarly = false;
        waitingForNewEvents = false;
        // Get and set the next event
        currentEvent = queue.poll();
    }

    /** Used to indicate when the current event should terminate early regardless of if
     * it is done. Should be used only when absolutely necessary (like an invalid event),
     * as this will skip any cleanup operations for the current event.
     */
    protected void terminateEarly()
    {
        terminateEarly = true;
    }

    /** Set this system in a suspended state, which continues until new events have been queued.
     * IMPORTANT: One this method has been called, this system's oneCycle() method will NOT be
     * called again until the event manager has queued new events.
     */
    private void waitForNewEvents()
    {
//        SampleAutoOpMode.telemetryAccess.addData("INFO", "Previous Event Finished");
        waitingForNewEvents = true;
    }
}
