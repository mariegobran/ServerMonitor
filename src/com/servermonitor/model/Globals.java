
package com.servermonitor.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servermonitor.*;

public class Globals {
	public static Map<Date, ServerStatus> statuses = 
			java.util.Collections.synchronizedMap(new HashMap<Date, ServerStatus>());
	public static boolean monitorIsRunning = false;
	
	
	public static Map<Date, ServerStatus> sortStatuses(){
		Map<Date, ServerStatus> sortedStatuses = new TreeMap<Date, ServerStatus>();
		
		ArrayList<Date> sortedDates = new ArrayList<Date>(statuses.keySet());
		Collections.sort(sortedDates);
		
		Iterator<Date> Dates = sortedDates.iterator();
		
		synchronized (Globals.class) {
		while(Dates.hasNext()) {
			
				Date currentDate = Dates.next();
				sortedStatuses.put(currentDate, statuses.get(currentDate));
			}
		}
		
		return sortedStatuses;
		
	}
}
