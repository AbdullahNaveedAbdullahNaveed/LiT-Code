package com.lmrobotics.litcode.devices;

import com.qualcomm.robotcore.hardware.HardwareMap;

/** Provide a standard way to determine the direction the robot is facing.  This could
 * use a gyroscope, compass, another sensor, or a combination of sensors.  Regardless of
 * how it works internally, the main method to get the heading should return a value in a
 * predetermined range and type (like a float/double in the range of 0-359 degrees).
 */
public class HeadingSystem
{
    /** Basic constructor, needs the hardware map to setup access to the used sensors. */
    public HeadingSystem(HardwareMap hardwareMap)
    {

    }
}
