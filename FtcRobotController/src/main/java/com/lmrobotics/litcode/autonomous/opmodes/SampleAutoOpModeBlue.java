package com.lmrobotics.litcode.autonomous.opmodes;

/** A sample autonomous opmode for the blue side of the field. */
public class SampleAutoOpModeBlue extends AutoOpModeBase
{
    @Override
    protected void setEventConfig()
    {
        this.configData =
                "INIT,X=1.0,Y=1.0,HEADING=20,ALLIANCE=BLUE;"
                        + "STARTBLOCK;"
                        + "NAVIGATION,EVENT=MoveEvent,TIME=4000;"
                        + "NAVIGATION,EVENT=TurnEvent,TIME=525;"
                        + "NAVIGATION,EVENT=MoveEvent,TIME=375,MAX_SPEED=0.8;"
                        + "ENDBLOCK;"
        ;
    }
}
