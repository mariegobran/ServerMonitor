package com.servermonitor.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.servermonitor.model.*;
import com.servermonitor.controller.*;
import com.servermonitor.*;

/**
 *@author MGobran May 30, 2018
 *
 **/

public class MonitorApp {

	public static HttpURLConnection connectToAPI(String url) throws IOException {
		HttpURLConnection con = null;
		try {
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();

		} catch (MalformedURLException e) {
			e.printStackTrace();

		}
		return con;
	}



	public static void main(String[] args) {
		
		
		
		try {
			//TODO the next line is a temporary variable to limit the numbers of API calls
			int numOfCalls = 20;
			
			long interval = 1000;
			long startTime=0;
			//Declare the timer
			Timer t = new Timer();

			TimerTask task = new TimerTask() {

				int currentStatusNumber=0;
				public void run() {
					try {
						if (currentStatusNumber >= numOfCalls) {
							t.cancel();
						}
						//Called each time when 1000 milliseconds (1 second) (the period parameter)
						HttpURLConnection con = connectToAPI("https://api.test.paysafe.com/accountmanagement/monitor");
						// optional default is GET
						con.setRequestMethod("GET");
						//add request header
						con.setRequestProperty("User-Agent", "Mozilla/5.0");
						//int responseCode = con.getResponseCode();

						BufferedReader in = new BufferedReader(
								new InputStreamReader(con.getInputStream()));

						StringBuffer response = new StringBuffer();
						String inputLine;
						
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
						//Read JSON response 
						JSONObject JsonResponse = new JSONObject(response.toString());

						// adding a new status
						constants.statuses.put(new Date(),new ServerStatus(JsonResponse.getString("status")));
						currentStatusNumber++;
					} catch (ProtocolException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}};



				//Set the schedule function and rate
				t.scheduleAtFixedRate(task,
						//Set how long before to start calling the TimerTask (in milliseconds)
						startTime,
						//Set the amount of time between each execution (in milliseconds)
						interval);




		} catch (Exception e) {
			e.printStackTrace();
		}

		// List Entries
		Iterator<Map.Entry<Date, ServerStatus>> entries = constants.statuses.entrySet().iterator();

		while (entries.hasNext()) {
			Map.Entry<Date, ServerStatus> entry = entries.next();
			Date time = (Date) entry.getKey();
			ServerStatus status= (ServerStatus) entry.getValue();
			System.out.println("Key = " + time + ", Value = " + status.getStatus());
		}



	}

}


