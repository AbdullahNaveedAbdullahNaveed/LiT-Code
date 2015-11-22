package com.lmrobotics.litcode.autonomous.navigation;

/**
 * Created by Bryan on 11/19/2015.
 */
public class PositionSystem
{
    private static double initialX;
    private static double initialY;
    private double currX;
    private double currY;

    public PositionSystem()
    {
        setX(initialX);
        setY(initialY);
    }

    /** Set the initial X position of the robot. */
    public static void setInitialX(double startX)
    {
        initialX = startX;
    }

    /** Set the initial Y position of the robot. */
    public static void setInitialY(double startY)
    {
        initialY = startY;
    }

    /** Get the current/target X position of the robot. */
    public double getX()
    {
        return currX;
    }

    /** Get the current/target Y position of the robot. */
    public double getY()
    {
        return currY;
    }

    /** Set the current/target X position of the robot. */
    public void setX(double newX)
    {
        currX = newX;
    }

    /** Set the current/target Y position of the robot. */
    public void setY(double newY)
    {
        currY = newY;
    }

    /** Set both the current/target X and Y positions. */
    public void setPos(double newX, double newY)
    {
        setX(newX);
        setY(newY);
    }
}
