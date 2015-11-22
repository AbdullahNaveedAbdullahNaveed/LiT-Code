package com.lmrobotics.litcode.devices;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by adsweiger on 11/5/2015.
 */
public class DriveSystem
{
    private DcMotor[] leftMotors;
    private DcMotor[] rightMotors;

    /** Normal constructor, needs the hardware map to get the motors. */
    public DriveSystem(HardwareMap hardwareMap)
    {
        leftMotors = new DcMotor[2];
        leftMotors[0] = hardwareMap.dcMotor.get("frontLeftDrive");
        leftMotors[1] = hardwareMap.dcMotor.get("backLeftDrive");
        rightMotors = new DcMotor[2];
        rightMotors[0] = hardwareMap.dcMotor.get("frontRightDrive");
        rightMotors[1] = hardwareMap.dcMotor.get("backRightDrive");
    }


    /**
     * Sets the power of all drive motors on the left of the robot
     * @param power
     */
    public synchronized void setLeft(float power)
    {
        // for each motor in leftMotors...
        for (DcMotor motor : leftMotors)
        {
            // Set the motor power to convertedPower
            motor.setPower(power);
        }
    }

    /**
     * Sets the power of all right-side drive motors
     * @param power
     */
    public synchronized void setRight(float power)
    {
        // for each motor in leftMotors...
        for (DcMotor motor : rightMotors)
        {
            // Set the motor power to convertedPower
            motor.setPower(power);
        }
    }

    /**
     * Sets both left and right sides
     * @param leftPower
     * @param rightPower
     */
    public synchronized void setPower(float leftPower, float rightPower)
    {
        setLeft(leftPower);
        setRight(rightPower);
    }

    /**
     * TODO document
     */
    public synchronized void stopMotors()
    {
        setPower(0,0);
    }
}
