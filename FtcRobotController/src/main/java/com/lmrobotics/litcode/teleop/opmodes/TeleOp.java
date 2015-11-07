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

    int scaleSpeed = 1;

    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {
        drive.setLeft(gamepad1.left_stick_y / scaleSpeed);
        drive.setRight(gamepad1.right_stick_y / scaleSpeed);
        if (gamepad1.a) {
            scaleSpeed =  2;
        }
        else {
            scaleSpeed = 1;
        }

        arm.armUp(gamepad1.right_trigger);
        arm.armDown(gamepad1.left_trigger);
    }

}
