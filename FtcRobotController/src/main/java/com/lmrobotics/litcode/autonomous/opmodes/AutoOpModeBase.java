package com.lmrobotics.litcode.autonomous.opmodes;

import com.lmrobotics.litcode.autonomous.EventManager;
import com.lmrobotics.litcode.autonomous.HLQGenerator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.robocol.Telemetry;

/** The base class for all autonomous opmodes we make, this is setup to work with
 * our event-driven system.
 */
public abstract class AutoOpModeBase extends OpMode
{
    /** Enables/Disables certain debugging-related parts of the code. */
    public static final boolean DEBUG_ENABLED = false;

    /** Provides easy telemetry access to all classes. */
    public static Telemetry telemetryAccess;
    /** This is printed out when DEBUG_ENABLED is true and an Exception occurs.  Used for
     * locating unhandled exceptions in the code.  Any class can set this.
     */
    public static String debugHook = "Not yet set";

    /** This should be set by subclasses that want to directly specify event config text
     * instead of loading the text from a config file.
     */
    protected String configData = null;
    /** Subclasses that want to load event config data from files should set this to the
     * name of the event config file to load (the file must be placed in the "eventconfigs" package
     * for this to work).
     */
    protected String configFileName = null;
    /** Does the actual managing of the autonomous systems & events, this opmode subclass
     * is mostly just a thin interface from the OpMode to the EventManager that makes it easier
     * to setup multiple autonomous configurations/modes.
     */
    private EventManager em;
    /** Used so that once one exception has been caught no other code that could cause exceptions
     * will be run, because later exceptions could be cause by an earlier issue and could be harder
     * to debug.
     */
    private boolean bugOccurred = false;

    @Override
    public void init()
    {
        // Setup telemetry access
        telemetryAccess = telemetry;
        HLQGenerator.allianceSwitchVal = hardwareMap.digitalChannel.get("allianceSwitch").getState();
        // If a bug has already occurred, return to prevent a downstream bug from occurring
        if (bugOccurred)
        {
            return;
        }
        debugHook = "Opmode init";
        try
        {
            // Have a subclass set its configData or configFileName
            setEventConfig();
            // Raw config data was given by subclass
            if (configData != null)
            {
                em = new EventManager(hardwareMap, configData, true);
            }
            // Event config file name was given by subclass
            else if (configFileName != null)
            {
                em = new EventManager(hardwareMap, configFileName, false);
            }
            // No event config specified, raise an exception
            else
            {
                debugHook = "No event config";
                Exception e = new Exception("No event config was specified by opmode");
                throw e;
            }g
        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    /** Used by subclasses to specify event config data, either the event config text
     * or the name of an event config file in the "eventconfig" package.
     */
    protected abstract void setEventConfig();

    @Override
    public void start()
    {
        if (bugOccurred)
        {
            return;
        }
        debugHook = "Opmode start";
        try
        {
            // Start the event manager
            em.start();
        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    @Override
    public void loop()
    {
        if (bugOccurred)
        {
            return;
        }
        debugHook = "Opmode loop";
        try
        {
            // Run the event manager loop
            em.loop();
        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    /** Handles an exception that occurred by providing output data to the user,
     * if DEBUG_ENABLED is true it prints out the debugHook, otherwise it prints
     * out the exception stack trace.
     * @param e the Exception that was caught
     */
    private void handleException(Exception e)
    {
        bugOccurred = true;
        // Debug is enabled, display the debug hook
        if (DEBUG_ENABLED)
        {
            telemetryAccess.addData("DebugHk", debugHook);
        }
        // Debug not enabled, display the stack trace
        else
        {
            // TODO test this
            telemetry.addData("An Exception Occurred", e.getStackTrace().toString());
        }
    }
}
