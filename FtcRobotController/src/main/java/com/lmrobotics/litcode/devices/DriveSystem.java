package com.lmrobotics.litcode.devices;

import com.lmrobotics.litcode.autonomous.opmodes.AutoOpModeBase;
import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by adsweiger on 11/5/2015.
 */
public class DriveSystem
{
    /** Used so that the static setup function will only setup stuff once. */
    private static boolean isSetup = false;
    /** The left drive motors. */
    private static DcMotor[] leftMotors = new DcMotor[2];
    /** The right drive motors. */
    private static DcMotor[] rightMotors = new DcMotor[2];

    /** Initial setup for the drive system. */
    public static void setup(HardwareMap hardwareMap)
    {
        // Return if setup is already complete
        if (isSetup)
        {
            return;
        }
        isSetup = true;
        // Get the left motors
        leftMotors[0] = hardwareMap.dcMotor.get("frontLeftDrive");
        leftMotors[1] = hardwareMap.dcMotor.get("backLeftDrive");
        // Get the right motors
        rightMotors[0] = hardwareMap.dcMotor.get("frontRightDrive");
        rightMotors[1] = hardwareMap.dcMotor.get("backRightDrive");
        // Reverse motors as necessary here
        for (DcMotor motor : leftMotors)
        {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /** Legacy constructor, used for the initial setup of the drive system.
     * Use DriveSystem.setup(hardwareMap) then new DriveSystem()
     * @param hardwareMap the hardware map to get drive motors from
     */
    @Deprecated
    public DriveSystem(HardwareMap hardwareMap)
    {
        setup(hardwareMap);
    }

    /** Basic constructor, use this to gain access to the drive system once it has been setup. */
    public DriveSystem()
    {
    }

    /**
     * Sets the power of all drive motors on the left of the robot
     * @param power
     */
    public synchronized void setLeft(float power)
    {
        if (!isSetup)
        {
            AutoOpModeBase.telemetryAccess.addData(
                    "WARNING",
                    "Attempted to access drive system before setup."
            );
            return;
        }
        // for each motor in leftMotors...
        for (DcMotor motor : leftMotors)
        {
            // Set the motor power to convertedPower
            motor.setPower(power);
        }
    }

    /**
     * @param power
     */
    public synchronized void setLeft(double power)
    {
        setLeft((float) power);
    }

    /** Sets the power of all right-side drive motors
     * @param power
     */
    public synchronized void setRight(float power)
    {
        if (!isSetup)
        {
            AutoOpModeBase.telemetryAccess.addData(
                    "WARNING",
                    "Attempted to access drive system before setup."
            );
            return;
        }
        // for each motor in leftMotors...
        for (DcMotor motor : rightMotors)
        {
            // Set the motor power to convertedPower
            motor.setPower(power);
        }
    }

    /**
     *
     * @param power
     */
    public synchronized void setRight(double power)
    {
        setRight((float) power);
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

    public synchronized void setPower(double leftPower, double rightPower)
    {
        setLeft(leftPower);
        setRight(rightPower);
    }

    /**
     * TODO document
     */
    public synchronized void stopMotors()
    {
        setPower(0, 0);
    }
}
