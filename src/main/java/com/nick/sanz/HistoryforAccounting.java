package com.nick.sanz;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nick.sanz.caltools.Gig;
import com.nick.sanz.caltools.GoogleCalendarRetriever;

public class HistoryforAccounting {
	public static void main(String args[]) throws Exception {

		GoogleCalendarRetriever googCalRetriever = new GoogleCalendarRetriever();
		JsonObject pastDates = googCalRetriever.getPastDatesJson();
		JsonArray bookings = (JsonArray) pastDates.get("items");
		List<Gig> gigList = googCalRetriever.buildGigList(bookings);
		List<String> dateList = new ArrayList<String>();
		List<String> summaryList = new ArrayList<String>();
		String holdYr = "";
		int gigCount = 1;
		try {
			for (Gig gig : gigList) {
//				if (gig.getSummary() != null && (gig.getSummary().contains("Jambalaya") || gig.getSummary().contains("Pheasant"))) {
					String currYr = gig.getDateTimeYear().substring(gig.getDateTimeYear().lastIndexOf(" "));
					if (!holdYr.equals(currYr)) {
						System.out.println("\n" + currYr);
						System.out.println("====================");
						String mmDDyyyy = getCalendarFromString(gig.getDateTimeYear());
						dateList.add(mmDDyyyy);
						summaryList.add(gig.getSummary());
//						System.out.println(gigCount + ". " + gig.getDateTimeYear() + " -- " + gig.getSummary());
						System.out.println(mmDDyyyy + " -- " + gig.getSummary());
						System.out.println(mmDDyyyy + " -- " + gig.getLocation());
						holdYr = currYr;
						gigCount = 1;
					} else {
						String mmDDyyyy = getCalendarFromString(gig.getDateTimeYear());
						dateList.add(mmDDyyyy);
						summaryList.add(gig.getSummary());
						System.out.println(mmDDyyyy + " -- " + gig.getSummary());
						System.out.println(mmDDyyyy + " -- " + gig.getLocation());
//						System.out.println(gigCount + ". " + gig.getDateTimeYear() + " -- " + gig.getSummary());
					}
					gigCount++;
//				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String string : dateList) {
			System.out.println(string);
		}
		for (String string : summaryList) {
			System.out.println(string);
		}

	}

	private static String getCalendarFromString(String dateTimeYear) {
		String date = dateTimeYear.substring(dateTimeYear.indexOf(",") + 2);
		Calendar cal = Calendar.getInstance();
		Month monthEnum = Month.parse(date.substring(0, 3));
		int month = monthEnum.index - 1;
		cal.set(cal.MONTH, month);
		cal.set(cal.DATE, Integer.parseInt(date.substring(date.indexOf(" ") + 1, date.lastIndexOf(" "))));
		cal.set(cal.YEAR, Integer.parseInt(date.substring(date.lastIndexOf(" ") + 1)));
//		System.out.println(cal.toString());
//		System.out.println(cal.getTime());
		return new SimpleDateFormat( "MM/dd/yyyy" ).format( cal.getTime()) ;
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