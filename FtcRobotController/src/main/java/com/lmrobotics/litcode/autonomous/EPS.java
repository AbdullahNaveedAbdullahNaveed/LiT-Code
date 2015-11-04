package com.lmrobotics.litcode.autonomous;

/** EPS – Event Processing Subsystem.  Provide a base class used by classes like Navigation
 * and Actions.  This class manages the task control aspects common to classes that process
 * actions, like queueing events, processing through the current event, etc.
 */
public abstract class EPS implements Runnable
{
    /** The thread object that will be used. */
    private Thread thread;

    /** Basic constructor. */
    public EPS()
    {

    }

    public abstract void loop();
    /** Implemented by subclasses, does any startup specific to a subclass. */
    public abstract void startUp():

    /** Starts up the thread for this EPS and calls the subclass method for unique setup. */
    public void start()
    {
        startUp();
        thread.start();
    }

    @Override
    public void run()
    {
        while (true)
        {
            loop();
            // Pause thread here?
        }
    }
}
