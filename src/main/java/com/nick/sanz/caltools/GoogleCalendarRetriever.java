package com.nick.sanz.caltools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author nsanzeri
 * Description:
 * Go to https://developers.google.com/google-apps/calendar/v3/reference/calendarList/list#examples
 * to get the calendar list
 * 
 * "items": [
  {

   "kind": "calendar#calendarListEntry",
   "etag": "\"1457323160632000\"",
   "id": "1dh0kfekld97cus67i6dpgibc0@group.calendar.google.com",
   "summary": "Payment Schedule",
   "timeZone": "America/Chicago",
   "colorId": "18",
   "backgroundColor": "#b99aff",
   "foregroundColor": "#000000",
   "accessRole": "owner",
   "defaultReminders": [
   ]
  },
 
  {

   "kind": "calendar#calendarListEntry",
   "etag": "\"1457323162296000\"",
   "id": "7o72ec9h5qv6fkjcqfv4u4so9suuudih@import.calendar.google.com",
   "summary": "Hooked On Sonics Concerts",
   "timeZone": "UTC",
   "colorId": "15",
   "backgroundColor": "#9fc6e7",
   "foregroundColor": "#000000",
   "accessRole": "reader",
   "defaultReminders": [
   ]
  },
  {

   "kind": "calendar#calendarListEntry",
   "etag": "\"1467052611415000\"",
   "id": "nsanzeri@gmail.com",
   "summary": "Nick Sanzeri",
   "timeZone": "America/Chicago",
   "colorId": "10",
   "backgroundColor": "#b3dc6c",
   "foregroundColor": "#000000",
   "selected": true,
   "accessRole": "owner",
   "defaultReminders": [
    {
     "method": "popup",
     "minutes": 10
    }
   ],
   "notificationSettings": {
    "notifications": [
     {
      "type": "eventCreation",
      "method": "email"
     },
     {
      "type": "eventChange",
      "method": "email"
     },
     {
      "type": "eventCancellation",
      "method": "email"
     }
    ]
   },
   "primary": true
  },
  {

   "kind": "calendar#calendarListEntry",
   "etag": "\"1461940849373000\"",
   "id": "3o68ijavuma7ctlinmijmp934g@group.calendar.google.com",
   "summary": "Brian Gary Nick - out",
   "description": "Add our vacation days to this calendar",
   "timeZone": "America/Chicago",
   "colorId": "3",
   "backgroundColor": "#f83a22",
   "foregroundColor": "#000000",
   "selected": true,
   "accessRole": "owner",
   "defaultReminders": [
   ]
  },
 *
 */
public class GoogleCalendarRetriever {
	Calendar cal = Calendar.getInstance(); 

	public JsonObject getPastDatesJson() throws MalformedURLException, IOException {
		String sURL = "https://www.googleapis.com/calendar/v3/calendars/pjjfdgelvdjtuvrr89tun3nu7k%40group.calendar.google.com/events?maxResults=10000&orderBy=startTime&singleEvents=true&timeMin=2001-01-01T10%3A00%3A00-07%3A00&key=AIzaSyC5FUd7RDTh6pzjKmK6vw7UOqzlXT6KZwI&jsoncallback=?";

		// Connect to the URL using java's native library
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser(); // from gson
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
		JsonObject rootobj = root.getAsJsonObject();
		return rootobj;
	}
	
	public JsonObject getFutureDatesJson(int maxResults) throws MalformedURLException, IOException {
		String sURL = "https://www.googleapis.com/calendar/v3/calendars/pjjfdgelvdjtuvrr89tun3nu7k%40group.calendar.google.com/events?maxResults=" + maxResults + "&orderBy=startTime&singleEvents=true&timeMin=" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "T10%3A00%3A00-07%3A00&key=AIzaSyC5FUd7RDTh6pzjKmK6vw7UOqzlXT6KZwI&jsoncallback=?";

		// Connect to the URL using java's native library
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser(); // from gson
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
		JsonObject rootobj = root.getAsJsonObject();
		return rootobj;
	}
	
	public JsonObject getGuysOutDatesJson(int maxResults) throws MalformedURLException, IOException {
		
		String sURL = "https://www.googleapis.com/calendar/v3/calendars/3o68ijavuma7ctlinmijmp934g%40group.calendar.google.com/events?maxResults=" + maxResults + "&orderBy=startTime&singleEvents=true&timeMin=" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "T10%3A00%3A00-07%3A00&key=AIzaSyC5FUd7RDTh6pzjKmK6vw7UOqzlXT6KZwI&jsoncallback=?";

		// Connect to the URL using java's native library
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser(); // from gson
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
		JsonObject rootobj = root.getAsJsonObject();
		return rootobj;
	}
	
	public JsonObject getMyDates(int maxResults) throws MalformedURLException, IOException {
		
		String sURL = "https://www.googleapis.com/calendar/v3/calendars/nsanzeri%40gmail.com/events?maxResults=" + maxResults + "&orderBy=startTime&singleEvents=true&timeMin=" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "T10%3A00%3A00-07%3A00&key=AIzaSyC5FUd7RDTh6pzjKmK6vw7UOqzlXT6KZwI&jsoncallback=?";

		// Connect to the URL using java's native library
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser(); // from gson
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
		JsonObject rootobj = root.getAsJsonObject();
		return rootobj;
	}
	
	
	public List<Gig> buildGigList(JsonArray bookings) {
		List<Gig> gigList = new ArrayList<Gig>();
		for (JsonElement jsonElement : bookings) {
			Gig gig = new Gig();
			JsonObject gigJsonObj = jsonElement.getAsJsonObject();
			if(gigJsonObj.get("summary") != null){
				gig.setSummary(gigJsonObj.get("summary").getAsString());
			}
			JsonObject startDate = (JsonObject) gigJsonObj.get("start"); 
			String dateTime = startDate.get("dateTime").getAsString();
			ZonedDateTime gigTime = ZonedDateTime.parse(dateTime);
		    DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM d");
		    DateTimeFormatter formatWithYear = DateTimeFormatter.ofPattern("E, MMM d YYYY");
		    gig.setDateTime(gigTime.format(format));
		    gig.setDateTimeYear(gigTime.format(formatWithYear));
		    if(gigJsonObj.get("location") != null){
		    	gig.setLocation(gigJsonObj.get("location").getAsString());
		    }
		    if(gigJsonObj.get("description") != null){
			    gig.setDescription(gigJsonObj.get("description").getAsString());		    	
		    }
		    gigList.add(gig);
		}
		return gigList;
	}
}
