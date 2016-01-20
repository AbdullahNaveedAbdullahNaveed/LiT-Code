package com.lmrobotics.litcode.autonomous.navigation.events;

/** A basic navigation event to turn to a certain heading. */
public class TurnEvent extends BaseNavigationEvent
{
    /** The direction to turn to for this event.
     * @see com.lmrobotics.litcode.devices.HeadingSystem
     */
    private int heading = 0;

    /** Basic constructor. */
    public TurnEvent(int heading, double maxSpeed)
    {
        super(maxSpeed);
        this.heading = heading;
    }

    /** Time-based turning constructor. */
    public TurnEvent(double maxSpeed, long time)
    {
        super(maxSpeed, time);
    }

    /** Return the number of encoder units required to turn to the right heading.  This should
     * only be used until we have a HeadingSystem setup.
     * @return the encoder units required to turn to the right heading (negative is CCW)
     */
    public int calcDistanceToTurn()
    {
        return 500;
    }

    /** Get the target heading for this event.  Note: Until we have a HeadingSystem setup, use
     * calcDistanceToTurn() instead of this.
     */
    public int getHeading()
    {
        // TODO Flip angle based on alliance initial setting
        return heading;
    }

    @Override
    protected void init()
    {
    }

    @Override
    public void oneCycle()
    {
        com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase.debugHook = "Nav turn";
        // Time-based turning
        if (isUsingTime())
        {
            // Left
            if (getTime() < 0)
            {
                drive.setPower(-1.0f, 1.0f);
            }
            // Right
            else
            {
                drive.setPower(1.0f, -1.0f);
            }
        }
        // Angle-based turning
        else
        {
            // TODO replace code with heading-based implementation
        }
    }

    @Override
    public boolean isFinished()
    {
        long now = System.currentTimeMillis();
        long elapsed = now - getStartTime();
        boolean result = false;
        // TODO check if the event is angle or time based then check if done
        if(elapsed >= Math.abs(getTime()))
        {
            result = true;
        }
        return result;
    }

    @Override
    public void cleanup()
    {
        drive.stopMotors();
    }
}
