
package com.servermonitor.rest;
/**
 * @author MGobran May 31, 2018
 * 
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path ("PaysafeServerMonitor")
public class MonitorRest {

	
	@PUT
	@Path("start")
	public void startMonitor() {
		
	}
	
	@PUT
	@Path("stop")
	public void stopMonitor() {
		
	}
	
	@PUT
	@Path("start/{millsec}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void startWithInterval(@PathParam("millsec")int millsec) {
		
	}
	
	@GET
	@Path("statistics")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatistics() {
		return null;
	}
}
