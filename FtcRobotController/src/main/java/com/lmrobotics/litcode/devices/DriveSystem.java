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
        leftMotors = new DcMotor[3];
        leftMotors[1] = hardwareMap.dcMotor.get("middleLeftDriveMotor");
        leftMotors[2] = hardwareMap.dcMotor.get("frontLeftDriveMotor");
        leftMotors[3] = hardwareMap.dcMotor.get("backLeftDriveMotor");
        rightMotors = new DcMotor[3];
        rightMotors[1] = hardwareMap.dcMotor.get("middleRightDriveMotor");
        rightMotors[2] = hardwareMap.dcMotor.get("frontRightDriveMotor");
        rightMotors[3] = hardwareMap.dcMotor.get("backRightDriveMotor");
    }


    /**
     * Sets the power of all drive motors on the left of the robot
     * @param power
     */
    public synchronized void setLeft(int power)
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
    public synchronized void setRight(int power)
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
    public synchronized void setPower(int leftPower, int rightPower)
    {
        setLeft(leftPower);
        setRight(rightPower);
    }
}
