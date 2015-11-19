package com.lmrobotics.litcode.autonomous.opmodes;

import com.lmrobotics.litcode.autonomous.EventManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** A sample autonomous opmode. */
public class SampleAutoOpMode extends OpMode
{
    private static final String sampleConfigData = "STARTBLOCK; NAV_MOVE,X=12.0,Y=13.5,MAX_SPEED=0.5; NAV_TURN,ANGLE=180.0,MAX_SPEED=0.5; ACT_SAMPLE; ENDBLOCK;";
    private EventManager em;

    @Override
    public void init()
    {
        em = new EventManager(hardwareMap, sampleConfigData, true);
    }

    @Override
    public void start()
    {
        em.start();
    }

    @Override
    public void loop()
    {
        em.loop();
    }
}
