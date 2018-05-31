
package com.servermonitor.model;
/**
 * 
 * @author MGobran May 31, 2018
 *
 */
public class ServerStatus {
	private String status;
	
	public ServerStatus(String status) {
		setStatus(status);
		
	}
	
	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		
		return this.status;
	}
}
