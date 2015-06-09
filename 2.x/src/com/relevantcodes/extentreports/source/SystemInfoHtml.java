package com.relevantcodes.extentreports.source;

public class SystemInfoHtml {
	public static String getColumn() {
		return "<div class='col l3 m6 s12'>" +
					"<div class='card-panel'>" +
						"<span class='panel-name'><!--%%SYSTEMINFOPARAM%%--></span>" +
						"<span class='panel-lead'><!--%%SYSTEMINFOVALUE%%--></span>" +
					"</div>" +
				"</div>";
	}
}
