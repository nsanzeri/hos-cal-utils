package com.nick.sanz;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nick.sanz.caltools.Gig;
import com.nick.sanz.caltools.GoogleCalendarRetriever;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class TodayInHistory {
	public static void main(String args[]) throws Exception {

		GoogleCalendarRetriever googCalRetriever = new GoogleCalendarRetriever();
		JsonObject pastDates = googCalRetriever.getPastDatesJson();
		JsonArray bookings = (JsonArray) pastDates.get("items");
		List<Gig> gigList = googCalRetriever.buildGigList(bookings);
		String holdYr = "";
		int gigCount = 1;
		try {
			for (Gig gig : gigList) {
//				if (gig.getSummary() != null && (gig.getSummary().contains("Jambalaya") || gig.getSummary().contains("Pheasant"))) {
					String currYr = gig.getDateTimeYear().substring(gig.getDateTimeYear().lastIndexOf(" "));
					if (!holdYr.equals(currYr)) {
						System.out.println("\n" + currYr);
						System.out.println("====================");
						System.out.println(gigCount + ". " + gig.getDateTimeYear() + " -- " + gig.getSummary());
						holdYr = currYr;
						gigCount = 1;
					} else {
						System.out.println(gigCount + ". " + gig.getDateTimeYear() + " -- " + gig.getSummary());
					}
					gigCount++;
//				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +0);
		StringBuffer sb = new StringBuffer();
		
		for (int i = 1; i < 60; i++) {
			int dayOfMonth = cal.get(Calendar.DATE);
			Boolean firstDate = true;
			String holdYear = "2002";
			Month month = Month.make(cal.get(Calendar.MONTH) + 1);
			String monthShort = month.toShortString();
			for (Gig gig : gigList) {
				String dateTimeYear = gig.getDateTimeYear();
				String gigYear = dateTimeYear.substring(dateTimeYear.lastIndexOf(" ") + 1);
				if (dateTimeYear.contains(" " + dayOfMonth + " ") && dateTimeYear.contains(monthShort)) {
					if (firstDate) {
						sb.append("\nToday in Hooked On Sonics history: ");
						sb.append(monthShort + " " + dayOfMonth + "\n");
						sb.append("---------------------------------------------------------\n");
					}
					firstDate = false;
					if (gig.getLocation() != null){
						sb.append(gigYear + " - " + gig.getSummary() + " (" + gig.getLocation() + ")\n");	
					}else{
						sb.append(gigYear + " - " + gig.getSummary() + "\n");
					}
					
					sb.append("\t" + gig.getDescription() + "\n\n");
				}
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		sb.append("#HookedOnSonicsHistory\n\n");
		System.out.println("============= Historic events ==========================");
		System.out.println(sb.toString());
		System.out.println("============= Historic events - END ==========================");

	}

	public enum Month {
		Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(10), Nov(11), Dec(12);

		private static DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		private static final int[] LAST_DAY_OF_MONTH = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		public int index;

		Month(int index) {
			this.index = index;
		}

		public static Month make(int monthIndex) {
			for (Month m : Month.values()) {
				if (m.index == monthIndex)
					return m;
			}
			throw new IllegalArgumentException("Invalid month index " + monthIndex);
		}

		public int lastDay() {
			return LAST_DAY_OF_MONTH[index];
		}

		public int quarter() {
			return 1 + (index - 1) / 3;
		}

		public String toString() {
			return dateFormatSymbols.getMonths()[index - 1];
		}

		public String toShortString() {
			return dateFormatSymbols.getShortMonths()[index - 1];
		}

		public static Month parse(String s) {
			s = s.trim();
			for (Month m : Month.values())
				if (m.matches(s))
					return m;

			try {
				return make(Integer.parseInt(s));
			} catch (NumberFormatException e) {
			}
			throw new IllegalArgumentException("Invalid month " + s);
		}

		private boolean matches(String s) {
			return s.equalsIgnoreCase(toString()) || s.equalsIgnoreCase(toShortString());
		}
	}

}