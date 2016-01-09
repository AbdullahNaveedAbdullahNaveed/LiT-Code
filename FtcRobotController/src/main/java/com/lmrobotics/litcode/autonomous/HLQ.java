package com.lmrobotics.litcode.autonomous;

import com.lmrobotics.litcode.autonomous.opmodes.SampleAutoOpMode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HLQ
{
	private ConcurrentHashMap<InitSetting, Object> initSettings;
	private ConcurrentLinkedQueue<EventBlock> eventQueue;

	public enum InitSetting
	{
		X("X"),
		Y("Y"),
		HEADING("HEADING"),
		ALLIANCE("ALLIANCE");

		private String name;

		InitSetting(String name)
		{
			this.name = name;
		}

		@Override public String toString()
		{
			return this.name;
		}
	}

	public HLQ(ConcurrentHashMap<InitSetting, Object> init, ConcurrentLinkedQueue<EventBlock> queue)
	{
		initSettings = init;
		for (InitSetting s : InitSetting.values())
		{
			if (!(initSettings.containsKey(s)))
			{
                SampleAutoOpMode.telemetryAccess.addData(
                        "WARNING",
                        "Initial setting \'"
                                + s.toString()
                                + "\' was not set."
                );
			}
		}
		eventQueue = queue;
	}

    /** Get the next event block.
     * @return the next EventBlock in this high level queue
     */
	public EventBlock getNextEventBlock()
	{
		return eventQueue.poll();
	}

	/** Get the specified InitSetting.
	 * @param setting the HLQ.InitSetting to get
	 * @return the (Object-wrapped) value of the specified setting, or null
     *      if the specified setting was not setup
	 */
	public Object getInitialSetting(InitSetting setting)
	{
		Object value = initSettings.get(setting);
		if (value == null)
		{
            SampleAutoOpMode.telemetryAccess.addData(
                    "WARNING",
                    "Attempted to get uninitialized initial setting "
                            + "\'"
                            + setting.toString()
                            + "\'."
            );
		}
		return value;
	}

	@Override
	public String toString()
	{
		String str = "HLQ:";
		for (EventBlock eb : eventQueue)
		{
			str += eb.toString();
		}
		return str;

	}
}
