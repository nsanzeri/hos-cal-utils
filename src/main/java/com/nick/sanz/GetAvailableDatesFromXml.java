package com.nick.sanz;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nick.sanz.caltools.GoogleCalendarRetriever;

public class GetAvailableDatesFromXml {
	public static void main(String args[]) throws Exception {
		List<String> datesTaken = new ArrayList<String>();
		List<String> guysOutList = new ArrayList<String>();
		GoogleCalendarRetriever googCalRetriever = new GoogleCalendarRetriever();
		JsonObject futureDates = googCalRetriever.getFutureDatesJson(300);
		JsonArray bookings = (JsonArray) futureDates.get("items"); 
		
		JsonObject guysOut = googCalRetriever.getGuysOutDatesJson(300);
		JsonArray guysOutDates = (JsonArray) guysOut.get("items"); 
		printUpcomingGigs(bookings, datesTaken);
		printUpcomingGuysOut(guysOutDates, guysOutList);
		printAvailableDates(datesTaken, guysOutList);
		
//		JsonObject myCal = googCalRetriever.getMyDates(300);
//		JsonArray myCalDates = (JsonArray) guysOut.get("items"); 
//		printUpcomingGigs(bookings, datesTaken);
//		printUpcomingGuysOut(guysOutDates, guysOutList);
//		printAvailableDates(datesTaken, guysOutList);
	}

	private static void printAvailableDates(List<String> datesTaken, List<String> guysOutList) {
		System.out.println("\n****************************************************\n");
		Month monthenum = null;
		Calendar cal = Calendar.getInstance();
		int monthHold = cal.get(Calendar.MONTH);
		int yearHold = cal.get(Calendar.YEAR);;

		System.out.println("Hooked On Sonics - Available Dates");
		System.out.println("");
		
		System.out.println(monthenum.make(cal.get(Calendar.MONTH) + 1));
		System.out.println("----------------------");

		for (int i = 0; i < 365; i++) {
			if (cal.get(Calendar.MONTH) != monthHold) {
				if (cal.get(Calendar.YEAR) != yearHold) {
					yearHold = cal.get(Calendar.YEAR);
				}
				System.out.println("");
				System.out.println("");
				System.out.println(monthenum.make(cal.get(Calendar.MONTH) + 1) + " - " + cal.get(Calendar.YEAR));
				System.out.println("------------");
				monthHold = cal.get(Calendar.MONTH);
			}

			// get the day of the week for the current day
			int day = cal.get(Calendar.DAY_OF_WEEK);
			// check if it is a Saturday or Sunday
			String searchDate = "";
			if (day == Calendar.FRIDAY) {
				dayCheck(datesTaken, guysOutList, cal, "Fri");
			}
			if (day == Calendar.SATURDAY) {
				dayCheck(datesTaken, guysOutList, cal, "Sat");
			}


			// advance to the next day
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	private static void dayCheck(List<String> datesTaken, List<String> guysOutList, Calendar cal, String day) {
		String searchDate;
		searchDate = day + ", " + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + cal.get(Calendar.DAY_OF_MONTH);
		if (!datesTaken.contains(searchDate) && !guysOutList.contains(searchDate)) {
			System.out.println(searchDate);
		}
	}

	private static void printUpcomingGigs(JsonArray bookings, List datesTaken) {
		int gigCount = 1;
		for (JsonElement jsonElement : bookings) {
			JsonObject dateObj = jsonElement.getAsJsonObject(); 
			String summary = dateObj.get("summary").getAsString();
			JsonObject startDate = (JsonObject) dateObj.get("start"); 
			String dateTime = startDate.get("dateTime").getAsString();
			ZonedDateTime gigTime = ZonedDateTime.parse(dateTime);
		    DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM d");
		    String out = gigTime.format(format);
		    System.out.println(gigCount + ". " + out + " " + summary);
		    datesTaken.add(out);
		    gigCount++;
		}
	}
	
	private static void printUpcomingGuysOut(JsonArray bookings, List datesTaken) {
		System.out.println("\n****************************************************\n");
		int gigCount = 1;
		for (JsonElement jsonElement : bookings) {
			JsonObject dateObj = jsonElement.getAsJsonObject(); 
			String summary = dateObj.get("summary").getAsString();
			JsonObject startDate = (JsonObject) dateObj.get("start"); 
			JsonElement jsonDate = startDate.get("date");
			String dateTime = null;
			ZonedDateTime gigTime = null;
			if (jsonDate != null){
				dateTime = jsonDate.getAsString();
				gigTime = ZonedDateTime.parse(dateTime + "T22:00:00-06:00");
			}else{
				dateTime = startDate.get("dateTime").getAsString();
				gigTime = ZonedDateTime.parse(dateTime);
			}
			
		    DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM d");
		    String out = gigTime.format(format);
		    System.out.println(gigCount + ". " + out + " " + summary);
		    datesTaken.add(out);
		    gigCount++;
		}
	}
}