package com.lmrobotics.litcode.autonomous.navigation;

/**
 * Created by Bryan on 11/19/2015.
 */
public class PositionSystem
{
    private double currX;
    private double currY;

    public PositionSystem(double startX, double startY)
    {
        currX = startX;
        currY = startY;
    }

    public double getX()
    {
        return currX;
    }

    public double getY()
    {
        return currY;
    }

    public void setX(double newX)
    {

    }

    public void setY(double newY)
    {

    }

    public void setPos(double newX, double newY)
    {

    }
}
