package com.lmrobotics.litcode.autonomous;

/** The interface for all autonomous events. */
public abstract class AutonomousEvent
{
    /** The system time in milliseconds when this event started running. */
    private long startTime = -1;
    /** Basic constructor. */
    public AutonomousEvent()
    {
    }

    /** Get the time at which this event started running.
     * @return the system time in milliseconds that this event started, or -1 if not yet started
     */
    public final long getStartTime()
    {
        return startTime;
    }

    /** Initialize the event, is called once before the event begins running. */
    public final void initEvent()
    {
        startTime = System.currentTimeMillis();
        // Sub class event initialization
        init();
    }

    /** Initialization for each specific event type. */
    protected abstract void init();

    /** Run one cycle of this event. Update motors, sensors, etc. in here. */
    public abstract void oneCycle();

    /** Check if this event is finished running. */
    public abstract boolean isFinished();

    public abstract void cleanup();
}
