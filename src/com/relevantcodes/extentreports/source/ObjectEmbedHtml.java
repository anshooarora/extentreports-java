package com.relevantcodes.extentreports.source;

public class ObjectEmbedHtml {
	public static String getColumn() {
		return "<div class='col l3 m6 s12'>" +
					"<div class='card-panel'>" +
						"<span class='panel-name'><!--%%IMAGESVIEWPARAM%%--></span>" +
						"<span class='panel-object'><!--%%IMAGESVIEWVALUE%%--></span>" +
					"</div>" +
				"</div>";
	}
	
	public static String getFullWidth() {
		return "<div class='col s12'>" +
				"<div class='card-panel'>" +
					"<span class='panel-lead'><!--%%IMAGESVIEWVALUE%%--></span>" +
				"</div>" +
			"</div>";
	}
}
