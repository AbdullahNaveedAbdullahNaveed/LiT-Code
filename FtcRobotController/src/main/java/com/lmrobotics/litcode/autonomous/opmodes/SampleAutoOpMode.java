package com.lmrobotics.litcode.autonomous.opmodes;

import com.lmrobotics.litcode.autonomous.EventManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/** A sample autonomous opmode. */
public class SampleAutoOpMode extends OpMode
{
    private static final String sampleConfigData = "STARTBLOCK; NAVIGATION,EVENT=MoveEvent,TIME=500,MAX_SPEED=1.0; ENDBLOCK;";
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
