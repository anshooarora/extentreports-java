package com.relevantcodes.extentreports.converters;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.relevantcodes.extentreports.utils.FileReaderEx;

public class TimeConverter {
	private static final String HOURS_PATTERN = "\\d+h";
	private static final String MINUTES_PATTERN = "\\d+m";
	private static final String SECONDS_PATTERN = "\\d+s";
	private static final String MILLIS_PATTERN = "\\d+ms";
	
	private Document doc;
	    	
	public long getLastRunDurationMillis() {
		if (doc == null) {
			return 0;
		}
		
		Elements totalTimeTakenCurrent = doc.select(".suite-total-time-current-value");
		Elements totalTimeTakenOverall = doc.select(".suite-total-time-overall-value");

		String time;
		
		if (totalTimeTakenOverall != null && totalTimeTakenOverall.size() > 0 && !totalTimeTakenOverall.first().text().trim().equals("")) {
			time = totalTimeTakenOverall.first().text();
		}
		else if (totalTimeTakenCurrent != null && totalTimeTakenCurrent.size() > 0 && !totalTimeTakenCurrent.first().text().trim().equals("")) {
			time = totalTimeTakenCurrent.first().text();
		}
		else {
			return 0;
		}
		
		try {
			int hours = Integer.parseInt(parseValue(parseValue(time, HOURS_PATTERN), "\\d+"));
			int minutes = Integer.parseInt(parseValue(parseValue(time, MINUTES_PATTERN), "\\d+"));
			int seconds = Integer.parseInt(parseValue(parseValue(time, SECONDS_PATTERN), "\\d+"));
			int millis = Integer.parseInt(parseValue(parseValue(time, MILLIS_PATTERN), "\\d+"));
			
			millis = millis + ((3600 * hours + 60 * minutes + seconds) * 1000);
			return millis;
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	public TimeConverter(String filePath) {
		if (doc == null) {
			try {
				String source = FileReaderEx.readAllText(new File(filePath));
				doc = Jsoup.parse(source);
			}
			catch (Exception e) { }
		}
	}
	
	private String parseValue(String text, String format) {
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(text);
		
		if (matcher.find()) {
			return matcher.group();
		}
		
		return null;
	}
}
