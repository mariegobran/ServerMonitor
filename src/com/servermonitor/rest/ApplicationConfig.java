
package com.servermonitor.rest;
/**
 * @author MGobran May 31, 2018
 * 
 */
import java.util.Set;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("PaysafeServerMonitor")
public class ApplicationConfig extends Application{
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		addRestResourceClasses(resources);
		return resources;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(com.servermonitor.rest.MonitorRest.class);
	}
}
