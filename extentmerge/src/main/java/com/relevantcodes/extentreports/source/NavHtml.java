package com.relevantcodes.extentreports.source;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentmerge.LogSettings;

public class NavHtml extends LogSettings {
	public static String getReportItemSource() {
		return "<li class='report-item waves-effect' id=''>"
				+ "<a href='#!'>"
						+ "<i class='mdi-action-assignment'></i>"
						+ "<span class='report-date'></span>"
					+ "</a>"
				+ "</li>";
	}
	
	public static String getParsedTime(long millis) {
		Date date = new Date(millis);
		
		SimpleDateFormat sdf = new SimpleDateFormat(getLogDateFormat());
		String dtFormatted = sdf.format(date);
		
		sdf = new SimpleDateFormat(getLogTimeFormat());
		String timeFormatted = sdf.format(date);
		
		return 	"<span class='report-date'>" + dtFormatted + "</span>" +
				"<span class='report-time'> " + timeFormatted + "</span>";
	}
}
