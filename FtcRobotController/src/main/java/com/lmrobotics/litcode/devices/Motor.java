package com.lmrobotics.litcode.devices;

/**
 * Created by brsadowitz on 10/31/15.
 */
public class Motor
{
    /**
     * The maximum value for motor power.  Useful for algorithms including motor
     * power scaling and course correction. (MAX_POWER * -1 = max power in reverse)
     */
    public static final int MAX_POWER= 100;
    /**
     * Indicate that the motor should behave normally (it�s type doesn�t matter much, but all �MODE_�
     * constants need to be the same type to be passed in to setMode)
     * TODO establish values
     */
//    public static final ??? MODE_NORMAL= ???;
    /**
     * Allows the robot to move the motors in reverse.
     * TODO
     */
//    public static final ??? MODE_REVERSE= ???

    /**
     * Stuff needed to initialize a motor in the FTC libraries, and store info needed to access that
     * motor in instance variables in the object (for example, storing the motor name in a variable).
     * Should initialize the mode to NORMAL_MODE.
     * TODO
     * @param servoName The name the motor was registered with in the robot configuration
     */
    public Motor(String servoName)
    {
        public static final int MODE_NORMAL = 20;
        public static final int MODE_REVERSE = -20;
        setMode(MODE_NORMAL);
    }

    /**
     * Adjust the motor power, and start or stop it when necessary.
     * TODO
     * @param motorName
     */
    public Motor(String motorName,??? mode)
    {
        setMode(mode);
    }

    /**
     * Change how the motor is running (for example reverse it).
     * TODO
     * @param power
     */
    public synchronized void setPower(int power)
    {
        setMode(mode);
    }

    /**
     * Change how the motor is running (for example reverse it).
     * TODO
     */
    public synchronized void setMode(??? mode)
    {
        setMode(mode);
    }

    /**
     *Stop the motor, syntactically equivalent to �setPower(0)�.
     * TODO
     */
    public synchronized void stop()
    {
        setPower(0);
    }
    /**
     * Get the current power level of the motor (with MAX_POWER as the maximum).
     * TODO
     */
    public synchronized void int getPower;

}
