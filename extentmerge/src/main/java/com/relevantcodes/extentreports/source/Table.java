package com.relevantcodes.extentreports.source;

public class Table {
	public static String getRowSource(int cols) {
		String td = "";
		
		for (int ix = 0; ix < cols; ix++) {
			td += "<td></td>";
		}
		
		return "<table><tr>" + td + " </tr></table>";
	}
}
