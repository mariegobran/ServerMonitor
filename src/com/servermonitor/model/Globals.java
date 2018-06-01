
package com.servermonitor.model;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.servermonitor.*;

public class Globals {
	public static Map<Date, ServerStatus> statuses = new HashMap<Date, ServerStatus>();
	public static boolean monitorIsRunning = false;
}
