
package com.servermonitor.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.Date;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;


import com.servermonitor.core.MonitorApp;
import com.servermonitor.model.ServerStatus;
import com.servermonitor.model.constants;

public class MonitorNewEntryTask extends TimerTask {

	//TODO the next line is a temporary variable to limit the numbers of API calls
	static int numOfCalls = 20;
	static int currentStatusNumber=0;
	
	//TODO set and get numOfCalls & reset currentStatusNumber;
	
	@Override
	public void run() {

		try {
			if (currentStatusNumber >= numOfCalls) {
				this.cancel();
			}
			//Called each time when 1000 milliseconds (1 second) (the period parameter)
			HttpURLConnection con = MonitorApp.connectToAPI("https://api.test.paysafe.com/accountmanagement/monitor");
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

	}

}
