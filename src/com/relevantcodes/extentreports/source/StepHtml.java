package com.relevantcodes.extentreports.source;

public class StepHtml {
	public static String get(int colSpan) {
		if (colSpan == 2) {
			return "<tr>" +
						"<td><!--%%TIMESTAMP%%--></td>" +
						"<td title='<!--%%STEPSTATUSU%%-->' class='status <!--%%STEPSTATUS%%-->'><i class='fa fa-<!--%%STATUSICON%%-->'></i></td>" +
						"<td class='step-details' colspan='2'><!--%%DETAILS%%--></td>" +
					"</tr>";
			
		}
		
		return "<tr>" +
					"<td><!--%%TIMESTAMP%%--></td>" +
					"<td title='<!--%%STEPSTATUSU%%-->' class='status <!--%%STEPSTATUS%%-->'><i class='fa fa-<!--%%STATUSICON%%-->'></i></td>" +
					"<td class='step-name'><!--%%STEPNAME%%--></td>" +
					"<td class='step-details'><!--%%DETAILS%%--></td>" +
				"</tr>";
	}
}
