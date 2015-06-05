package com.relevantcodes.extentreports.source;

public class ExtentFlag {
	public static String getPlaceHolder(String flag) {
		return "<!--%%" + flag.toUpperCase() + "%%-->";
	}
}
