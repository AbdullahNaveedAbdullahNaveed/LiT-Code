package com.lmrobotics.litcode.autonomous.actions.events;

/** Runs for a certain amount of time,  allows actions to be paused, and can be used to pause
 * the entire robot if there are no navigation events to run.
 */
public class PauseEvent extends BaseActionEvent
{
    /** Basic constructor. */
    public PauseEvent(long pauseFor)
    {
        // TODO Implement
    }

    @Override
    public void init()
    {
        // TODO Implement
    }

    @Override
    public void oneCycle()
    {
    }

    @Override
    public boolean isFinished()
    {
        // TODO Implement
        return true;
    }

    @Override
    public void cleanup()
    {
    }
}
