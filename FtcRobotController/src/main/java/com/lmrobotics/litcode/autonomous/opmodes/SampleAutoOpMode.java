package com.lmrobotics.litcode.autonomous.opmodes;

/** A sample autonomous opmode. */
public class SampleAutoOpMode extends AutoOpModeBase
{

    @Override
    protected void setEventConfig()
    {
        this.configData = "INIT,X=1.0,Y=1.0,HEADING=20,ALLIANCE=RED;"
                + "#! Test comment string;"
                + "STARTBLOCK;"
                + "NAVIGATION,EVENT=MoveEvent,TIME=4000;"
                + "NAVIGATION,EVENT=TurnEvent,TIME=-525;"
                + "NAVIGATION,EVENT=MoveEvent,TIME=375,MAX_SPEED=0.8;"
                + "ENDBLOCK;"
        ;
    }
}
