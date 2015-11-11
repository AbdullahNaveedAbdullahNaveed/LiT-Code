package com.lmrobotics.litcode.devices;

import com.lmrobotics.litcode.devices.Motor;
/**
 * Created by adsweiger on 11/5/2015.
 */
public class DriveSystem
{
    /**
     * Sets the power of all drive motors on the left of the robot
     * @param power
     */

    public synchronized void setLeft(int power)
    {

    }

    /**
     * Sets the power of all right-side drive motors
     * @param power
     */

    public synchronized void setRight(int power)
    {

    }

    /**
     * Sets both left and right sides
     * @param leftPower
     * @param rightPower
     */

    public synchronized void setPower(int leftPower, int rightPower)
    {

    }
    
    public static final int GAME_PAD_MAX= 128;
    public static final int GAME_PAD_MIN= -128;
    public static final double MOTOR_MAX= GAME_PAD_MAX/1.28;
    public static final double MOTOR_MIN= GAME_PAD_MIN/1.28;
}
