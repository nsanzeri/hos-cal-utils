package com.nick.sanz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nick.sanz.caltools.Gig;
import com.nick.sanz.caltools.GoogleCalendarRetriever;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;

/**
 * @author sanzni01 Access token:
 *         AAACEdEose0cBAHmsErKLE9zC8kAhLAJq6xj1q9cZBEnQGCmPnSBRVmbf3zVMllo5Xa4nOfXc3HZBhK2hZAUaiTWyp6rXKgM0oZBTPbUK1tCZAuEjuDOjW
 * 
 *         Get it from:https://developers.facebook.com/tools/explorer
 * 
 */
public class CreateFbEvents {
	private static String myToken = "CAACEdEose0cBADJy2wc4bNY6npa1cBDljlqNKdlfaTdrUn2uNDw55LCsGTb7cFx1GV5aVUli9bQKCZBSYp7IOoXHyOiPgD5vTCNr8VC5T4i49flCva36MEIlM8RNAFjfqQNrTCO58m10jzfiva2pKC6D4kMClOuugSvFRKSS5zZAgy4PjgDnVCuzhGGCyPxWiyKZByfwWQaeTWzPNAW";
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		try {

			GoogleCalendarRetriever googCalRetriever = new GoogleCalendarRetriever();
			JsonObject futureDates = googCalRetriever.getFutureDatesJson(2);
			JsonArray bookings = (JsonArray) futureDates.get("items"); 
			List<Gig> gigList = googCalRetriever.buildGigList(bookings);
			
			
			FacebookClient facebookClient = new DefaultFacebookClient(myToken);
			User user = facebookClient.fetchObject("me", User.class);
			Page page = facebookClient.fetchObject("hookedonsonicsband", Page.class);

			System.out.println("User name: " + user.getName());
			System.out.println("Page likes: " + page.getLikes());

			for (Gig gig : gigList) {
				FacebookType publishEventResponse = facebookClient.publish("132220862483/events", FacebookType.class, 
						Parameter.with("name", gig.getSummary()), 
						Parameter.with("start_time", gig.getDateTime()), 
						Parameter.with("location", gig.getLocation()), 
						Parameter.with("description", gig.getDescription()));

				System.out.println("Published event ID: " + publishEventResponse.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
