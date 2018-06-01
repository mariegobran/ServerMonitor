package com.servermonitor.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.servermonitor.model.*;

/**
 *@author MGobran May 30, 2018
 *
 **/

public class MonitorApp {
	public final static Logger logger = LoggerFactory.getLogger(MonitorApp.class);

	public static void main(String[] args) {

		BufferedReader in = null;
		try {
			
			
			startMonitor(1000,"https://api.test.paysafe.com/accountmanagement/monitor");
			

		} catch (IOException e) {
			logger.error("IOException: "+e.getMessage());
		}catch (JSONException e) {
			logger.error("JSONException: "+ e.getMessage());

		}finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("IOException while closing InputStream: "+e.getMessage());;
				}
			}
		}

		// List Entries after the monitor stops
		Iterator<Map.Entry<Date, ServerStatus>> entries = Globals.statuses.entrySet().iterator();

		while (entries.hasNext()) {
			Map.Entry<Date, ServerStatus> entry = entries.next();
			Date time = (Date) entry.getKey();
			ServerStatus status= (ServerStatus) entry.getValue();
			System.out.println("Key = " + time + ", Value = " + status.getStatus());
		}

	}


	public static HttpURLConnection connectToAPI(String url, Logger log) throws IOException {
		HttpURLConnection con = null;
		try {
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();

		} catch (MalformedURLException e) {
			throw new MalformedURLException(e.getMessage());

		}
		return con;
	}


	public static void startMonitor(int interval, String url) throws IOException, JSONException {
		BufferedReader in = null;

		try {
			
			Globals.monitorIsRunning = true;
			int running =0;
			while(Globals.monitorIsRunning) {
				HttpURLConnection con = MonitorApp.connectToAPI(url, logger);
				// optional default is GET
				con.setRequestMethod("GET");
				//add request header
				con.setRequestProperty("User-Agent", "Mozilla/5.0");
				//int responseCode = con.getResponseCode();

				in = new BufferedReader(
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
				Globals.statuses.put(new Date(),new ServerStatus(JsonResponse.getString("status")));
				
				if(running >30) {
					Globals.monitorIsRunning = false;
				}else {
					running++;
				}

			}
		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage() + e.getStackTrace());
		}finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("IOException while closing InputStream: "+e.getMessage());;
				}
			}
		}

	}}

