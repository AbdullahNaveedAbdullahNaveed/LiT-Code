package com.lmrobotics.litcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.lmrobotics.litcode.devices.DriveSystem;
import com.lmrobotics.litcode.devices.ArmSystem;

/** Enables control of the robot with the gamepad
 */
public class TeleOp extends OpMode
{
    public DriveSystem drive;

    public ArmSystem arm;

    float scaleSpeed = 1.0f;

    @Override
    public void init()
    {
        drive = new DriveSystem(hardwareMap);
    }

    @Override
    public void loop()
    {
        drive.setLeft(gamepad1.left_stick_y / scaleSpeed);
        drive.setRight(gamepad1.right_stick_y / scaleSpeed);
        if (gamepad1.a)
        {
            scaleSpeed =  2.0f;
        }
        else
        {
            scaleSpeed = 1.0f;
        }
//        if (gamepad1.right_trigger)
//        {
//            arm.armUp(20);
//        }
//        else if (gamepad1.left_trigger)
//        {
//           arm.armDown(-20);
//        }
//        else
//        {
//          arm.armUp(0);
//        }
    }
}
