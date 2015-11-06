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

    }

    @Override
    public void loop()
    {
    drive.setleft(gamepad1.left_stick_y);
    drive.setright(gamepad1.right_stick_y);
    }

}
