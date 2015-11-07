package com.lmrobotics.litcode.teleop.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** Enables control of the robot with the gamepad
 */
public class TeleOp extends OpMode
{
    public DriveSystem drive;

    @Override
    public void init()
    {
        int scaleSpeed = 1;
    }

    @Override
    public void loop()
    {
        drive.setleft(gamepad1.left_stick_y / scaleSpeed);
        drive.setright(gamepad1.right_stick_y / scaleSpeed);
        if (gamepad1.a) {
            scaleSpeed =  2;
        }
        else {
            scaleSpeed = 1;
        }
    }

}
