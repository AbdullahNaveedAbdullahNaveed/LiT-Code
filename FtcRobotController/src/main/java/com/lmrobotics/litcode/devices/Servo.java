package com.lmrobotics.litcode.devices;

/** Provide a layer over the servo classes provided by the FTC libraries.
 * The intent and advantages of this class are similar if not identical
 * to the Motor class (see its class definition for details).
 */
public class Servo
{
    /** Provide the maximum position value a servo can be set to.
     * TODO determine what max position should be.
     */
    public static final int MAX_POS = 20;
    /**
     * The lowest position value a servo can be set to.
     * TODO determine what min position should be.
     */
    public static final int MIN_POS = 0;

    /** Constructor will set starting position and recording
     * what the servo will be accessing
     * @param servoName The name the servo was registered with in the
     *                  robot configuration
     * @param startPos The position the servo should be initialized to.
     */
    public Servo(String servoName,int startPos)
    {

    }

    /** To set the position the servo will move to.
     * This should return immediately after setting the servo target;
     * it should not wait for the servo to stop moving.
     */
    public synchronized void setTarget(int targetPos)
    {

    }

    /** Get what position the servo is at when this method is called.
     */
    public synchronized int getCurrentPos()
    {

    }

    /** Gets the last target position the servo was set to
     * (see also: getCurrentPos)
     */
    public synchronized int getTargetPos()
    {

    }
}
