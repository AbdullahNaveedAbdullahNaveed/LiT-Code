package com.lmrobotics.litcode.devices;

import com.qualcomm.hardware.HiTechnicNxtGyroSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LegacyModule;

/** Provide a standard way to determine the direction the robot is facing.  This could
 * use a gyroscope, compass, another sensor, or a combination of sensors.  Regardless of
 * how it works internally, the main method to get the heading should return a value in a
 * predetermined range and type (like a float/double in the range of 0-359 degrees).
 */
public class HeadingSystem
{
    private static final long updateInterval = 100;
    private static final double GYRO_OFFSET = 583;
    private HiTechnicNxtGyroSensor gyro;
    private static int initialHeading = 0;
    private static double heading = initialHeading;
    private long lastUpdate = 0;

    /** Basic constructor, needs the hardware map to setup access to the used sensors. */
    public HeadingSystem(HardwareMap hardwareMap)
    {
        gyro = (HiTechnicNxtGyroSensor) hardwareMap.gyroSensor.get("LGyro");
        getHeading();
    }

    public synchronized double getHeading()
    {
        long currTime = System.currentTimeMillis();
        if (currTime-lastUpdate >= updateInterval)
        {
            double adjustment = gyro.getRotation()-GYRO_OFFSET;
            if (Math.abs(adjustment) > 1.5)
            {
                heading += (gyro.getRotation()-GYRO_OFFSET);
            }
            lastUpdate = currTime;
        }
        return heading;
    }
}
