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
//        drive = new DriveSystem(this.hardwareMap);
//        arm = new ArmSystem(this.hardwareMap);
    }

    @Override
    public void loop()
    {
        drive.setLeft((int)(gamepad1.left_stick_y / scaleSpeed));
        drive.setRight((int)(gamepad1.right_stick_y / scaleSpeed));
        if (gamepad1.a)
        {
            scaleSpeed =  2;
        }
        else
        {
            scaleSpeed = 1;
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
        telemetry.addData("Text", "***left stick***" + String.format("%.2f", gamepad1.left_stick_y));
        telemetry.addData("Text", "***right stick***" + String.format("%.2f", gamepad1.right_stick_y));
    }

}
