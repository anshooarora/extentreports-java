package com.relevantcodes.extentreports.mergetool;

class Extent {
	public static String getPlaceHolder(String name) {
		return "<!--%%" + name.toUpperCase() + "%%-->";
	}
}
