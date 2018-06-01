
package com.servermonitor.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Globals {
	public static Map<Date, ServerStatus> statuses = 
			java.util.Collections.synchronizedMap(new HashMap<Date, ServerStatus>());
	public static boolean monitorIsRunning = false;
	
	
	
	/**
	 * 
	 * @return Map<Date,ServerStatus>, a sorted by date version of statuses
	 */
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
