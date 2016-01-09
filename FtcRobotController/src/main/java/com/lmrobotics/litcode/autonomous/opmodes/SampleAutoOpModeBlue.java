package com.lmrobotics.litcode.autonomous.opmodes;

import com.lmrobotics.litcode.autonomous.EventManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.robocol.Telemetry;

/** A sample autonomous opmode. */
public class SampleAutoOpModeBlue extends OpMode
{
    /** */
    public static final boolean DEBUG_ENABLED = true;
    private static final String sampleConfigData =
            "INIT,X=1.0,Y=1.0,HEADING=20,ALLIANCE=BLUE;"
                    + "STARTBLOCK;"
                    + "NAVIGATION,EVENT=MoveEvent,TIME=4000;"
                    + "NAVIGATION,EVENT=TurnEvent,TIME=525;"
                    + "NAVIGATION,EVENT=MoveEvent,TIME=375,MAX_SPEED=0.8;"
                    + "ENDBLOCK;"
            ;
    private EventManager em;
    /** Public program-wide access to the telemetry. */
    public static Telemetry telemetryAccess;
    public static String debugHook;
    private boolean bugOccurred = false;

    @Override
    public void init()
    {
        telemetryAccess = telemetry;
        // TODO clean this up
        if (DEBUG_ENABLED)
        {
            if (bugOccurred) return;
            try
            {
                debugHook = "Opmode init";
                em = new EventManager(hardwareMap, sampleConfigData, true);
            }
            catch (Exception e)
            {
                bugOccurred = true;
                telemetryAccess.addData("DebugHk", debugHook);
                return;
            }
        }
        else
        {
            em = new EventManager(hardwareMap, sampleConfigData, true);
        }
    }

    @Override
    public void start()
    {
        if (DEBUG_ENABLED)
        {
            if (bugOccurred) return;
            try
            {
                debugHook = "Opmode start";
                em.start();
            }
            catch (Exception e)
            {
                bugOccurred = true;
                telemetryAccess.addData("DebugHk", debugHook);
                return;
            }
        }
        else
        {
            em.start();
        }
    }

    @Override
    public void loop()
    {
        if (DEBUG_ENABLED)
        {
            if (bugOccurred) return;
            try
            {
                debugHook = "Opmode loop";
                em.loop();
            }
            catch (Exception e)
            {
                bugOccurred = true;
                telemetryAccess.addData("DebugHk", debugHook);
                return;
            }
        }
        else
        {
            em.loop();
        }
    }
}
