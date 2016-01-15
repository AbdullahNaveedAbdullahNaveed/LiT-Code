package com.lmrobotics.litcode.autonomous.actions.events;

/** Runs for a certain amount of time,  allows actions to be paused, and can be used to pause
 * the entire robot if there are no navigation events to run.
 */
public class PauseEvent extends BaseActionEvent
{
    private long pauseFor = 0;
    private long start = 0;
    /** Basic constructor. */
    public PauseEvent(long pauseFor)
    {
        this.pauseFor = pauseFor;
    }

    @Override
    public void init()
    {
        start = System.currentTimeMillis();
    }

    @Override
    public void oneCycle()
    {
    }

    @Override
    public boolean isFinished()
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - start >= pauseFor)
        {
            return true;
        }
        return false;
    }

    @Override
    public void cleanup()
    {
    }
}
