package com.nick.sanz;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author sanzni01  */
public class CreateRSSFeed {
	public static void main(String args[]) throws Exception {
		try {

			String feed = "https://www.google.com/calendar/feeds/pjjfdgelvdjtuvrr89tun3nu7k%40group.calendar.google.com/public/basic?futureevents=true&orderby=starttime&sortorder=ascending&max-results=100&hl=en";
			URL feedUrl = new URL(feed);
			FileWriter writer = new FileWriter("e:\\hos-show-feed.xml");

			writer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
			writer.append("<rss version=\"2.0\">\n");
			writer.append("<channel>\n");
			writer.append("<title>Hooked On Sonics Shows</title>\n");
			writer.append("<link>http://www.hookedonsonicsband.com/</link>\n");
			writer.append("<description>Upcoming Hooked On Sonics Shows</description>\n");


			SyndFeedInput input = new SyndFeedInput();
			SyndFeed sf = input.build(new XmlReader(feedUrl));
			List<String> datesTaken = new ArrayList<String>();
			List<SyndEntry> nextTen = new ArrayList<SyndEntry>();
			List<SyndEntry> entries = sf.getEntries();
			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = entries.get(i);
				if (i < 4){
					nextTen.add(entry);
				}
			}
			Collections.reverse(nextTen);
			for (SyndEntry entry : nextTen) {
				System.out.println(entry.getTitle());
				String description = entry.getDescription().getValue();
				String link = entry.getLink();
				String title = entry.getTitle();
				String venue = title.substring(title.indexOf("Sonics") + 9);
				venue = venue.replace("&#39;", "");
				String showDate = description.substring(6, description.indexOf(","));
				String zipcode = determineZip(description, venue);
				String desc = determineDecs(description, venue);
				String privateGig = "N";
				if (venue.indexOf("Private") > -1) {
					privateGig = "Y";
					zipcode = "60605";
				}

				System.out.println("Show date = " + showDate);
				datesTaken.add(showDate);
				System.out.println();

				// ========== Build CSV File ==========
				writer.append("<item>\n"); // Each item is a show
				writer.append("<title>Hooked On Sonics @ " + venue + " - " + desc + "</title>\n"); // Title that appears in site
				writer.append("<link>" + link + "</link>\n"); // Link
				writer.append("<guid>" + link + "</guid>\n"); // Link
				writer.append("<pubDate>" + showDate.substring(0, 3) + "," + showDate.substring(3) + " 2014 12:00:00 CST</pubDate>\n"); // Show date
				writer.append("<description>" + desc + "</description>\n"); // Show date
				writer.append("</item>\n"); // Each item is a show
				
				// ========== Build CSV File ==========
			}
			writer.append("</channel>\n");
			writer.append("</rss>\n");
			

			writer.flush();
			writer.close();
			System.out.println("COMPLETE - go to e:\\hos-show-feed.xml to get your RSS feed");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String determineDecs(String description, String venue) {
		String desc = "";
		if (!venue.contains("Private")) {
			desc = description.substring(description.indexOf("Where:") + 6, description.lastIndexOf("<br>") - 1);
		}
		return desc;
	}

	private static String determineZip(String description, String venue) {
		String zipcode = "";
		if (venue.equals("Jambalaya")) {
			zipcode = "60175";
		} else if (venue.contains("Captain")) {
			zipcode = "60002";
		} else {

			if (description.indexOf("IL,") != -1) {
				zipcode = description.substring(description.indexOf("IL,") + 4, description.indexOf("IL,") + 9);

			} else {
				zipcode = description.substring(description.indexOf("IL") + 3, description.indexOf("IL") + 8);
			}
		}
		if (!zipcode.startsWith("6")) {
			zipcode = "";
		}
		return zipcode;
	}
}