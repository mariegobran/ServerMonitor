package com.servermonitor.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.servermonitor.model.*;

/**
 *@author MGobran May 30, 2018
 *
 **/

public class MonitorApp {

	public static void main(String[] args) {
		Map<Date, ServerStatus> statuses = new HashMap<Date, ServerStatus>();

		try {
			String url = "https://api.test.paysafe.com/accountmanagement/monitor";
			URL obj = new URL(url);
			int running =1;
			while(running<11) {
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				// optional default is GET
				con.setRequestMethod("GET");
				//add request header
				con.setRequestProperty("User-Agent", "Mozilla/5.0");
				//			int responseCode = con.getResponseCode();

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				//Read JSON response 
				JSONObject JsonResponse = new JSONObject(response.toString());

				// adding a new status
				statuses.put(new Date(),new ServerStatus(JsonResponse.getString("status")));

				running++;

			}

			// List Entries
			Iterator<Map.Entry<Date, ServerStatus>> entries = statuses.entrySet().iterator();

			while (entries.hasNext()) {
				Map.Entry<Date, ServerStatus> entry = entries.next();
				Date time = (Date) entry.getKey();
				ServerStatus status= (ServerStatus) entry.getValue();
				System.out.println("Key = " + time + ", Value = " + status.getStatus());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
