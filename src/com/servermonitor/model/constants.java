
package com.servermonitor.model;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.servermonitor.*;

public interface constants {
	static Map<Date, ServerStatus> statuses = new HashMap<Date, ServerStatus>();
}
