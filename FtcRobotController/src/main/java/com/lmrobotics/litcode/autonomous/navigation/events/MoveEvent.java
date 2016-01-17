package com.lmrobotics.litcode.autonomous.navigation.events;

/** A basic navigation event to move from one point to another. */
public class MoveEvent extends BaseNavigationEvent
{
    /** The x coordinate. */
    private double x = 0;
    /** The y coordinate. */
    private double y = 0;

    /** Basic constructor. */
    public MoveEvent(double x, double y, double maxSpeed)
    {
        super(maxSpeed);
        // Set event attributes
        this.x = x;
        this.y = y;
    }

    public MoveEvent(long time, double maxSpeed)
    {
        super(maxSpeed, time);
    }

    /** Get the target x coordinate of this event. */
    public double getX()
    {
        return x;
    }

    /** Get the target y coordinate of this event. */
    public double getY()
    {
        return y;
    }

    /** Calculate the distance in encoder units.
     * @return the distance in units directly usable with the drive motors.
     */
    public int calcDistance()
    {
        // TODO implement calculations
        return 1000;
    }

    /** Return the number of encoder units required to turn to the right heading.  This should
     * only be used until we have a HeadingSystem setup.
     * @return the encoder units required to turn to the right heading (negative is CCW)
     */
    public int calcDistanceToTurn()
    {
        if (isUsingTime())
        {
            return 0;
        }
        else
        {
            // TODO implement calculations
            return 0;
        }
    }

    /** Calculate the direction to travel in.  Note: Until we have a HeadingSystem setup, use
     * calcDistanceToTurn() instead.
     * @return the heading in degrees, 0 is horizontal-right and CCW is positive
     * (standard unit circle setup).
     */
    public int calcHeading()
    {
        // TODO implement
        // TODO Flip angle based on alliance initial setting
        return 180;
    }

    @Override
    public void init()
    {
    }

    @Override
    public void oneCycle()
    {
        com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase.debugHook = "Nav move";
        // Time-based movement
        if (isUsingTime())
        {
            // backwards
            if (getTime() < 0)
            {
                drive.setPower(-1.0f, -1.0f);
            }
            // forwards
            else
            {
                drive.setPower(1.0, 0.45);
            }
        }
        // Coordinate-based movement
        else
        {
            // TODO replace code with coordinate-based implementation
        }
    }

    @Override
    public boolean isFinished()
    {
        long now = System.currentTimeMillis();
        long elapsed = now - getStartTime();
        boolean result = false;
        // TODO check if the event is coordinate or time based then check if done
        if( elapsed >= Math.abs(getTime()))
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
