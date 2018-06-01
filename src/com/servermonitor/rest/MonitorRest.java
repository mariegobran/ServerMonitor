
package com.servermonitor.rest;
import java.io.IOException;

/**
 * @author MGobran May 31, 2018
 * 
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servermonitor.core.MonitorApp;
import com.servermonitor.model.Globals;

@Path ("PaysafeServerMonitor")
public class MonitorRest {

	
	@PUT
	@Path("start")
	public void startMonitor() {
		startWithInterval(1000);
	}
	
	@PUT
	@Path("stop")
	public void stopMonitor() {
		Globals.monitorIsRunning = false;
	}
	
	@PUT
	@Path("start/{millsec}")
	public void startWithInterval(@PathParam("millsec")int millsec) {
		try {
			MonitorApp.startMonitor(millsec, "https://api.test.paysafe.com/accountmanagement/monitor");
		} catch ( IOException e) {
			MonitorApp.logger.error("IOException: " + e.getMessage());
		} catch ( JSONException e) {
			MonitorApp.logger.error("JSONException: " + e.getMessage());
		}
	}
	
	@GET
	@Path("statistics")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatistics() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String result = gson.toJson(Globals.statuses);
		return result;
	}
}
