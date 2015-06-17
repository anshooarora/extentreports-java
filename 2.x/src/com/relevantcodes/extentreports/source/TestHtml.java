package com.relevantcodes.extentreports.source;

public class TestHtml {
	public static String getSource(int cols) {
		String colStepName = "";
		
		if (cols == 4) {
			colStepName = "<th>StepName</th>";
		}
		
		return "<div class='row'>" +
					"<div class='col s12'>" +
					"<div class='test card-panel <!--%%TESTSTATUS%%-->'>" +
						"<div class='test-head'>" +
							"<div class='test-name left'>" +
								"<!--%%TESTNAME%%-->" +
							"</div>" +
							"<div class='right'>" +
								"<span class='test-started-time label'><!--%%TESTSTARTTIME%%--></span>" +
								"<span class='test-ended-time label'><!--%%TESTENDTIME%%--></span>" +
								"<span class='test-status label <!--%%TESTSTATUS%%-->'><!--%%TESTSTATUS%%--></span>" +
							"</div>" +
							"<div class='test-desc' <!--%%DESCVIS%%-->>" +
								"<span><!--%%TESTDESCRIPTION%%--></span>" +
							"</div>" +
						"</div>" +
						"<div class='test-body'>" +
							"<table class='bordered table-results'>" +
								"<thead>" +
									"<tr>" +
										"<th>Timestamp</th>" +
										"<th>Status</th>" +
										colStepName +
										"<th>Details</th>" +
									"</tr>" +
								"</thead>" +
							"<tbody>" +
								"<!--%%STEP%%-->" +
							"</tbody>" +
							"</table>" +
						"</div>" +
					"</div>" +
				"</div>" +
			"</div>";
	}
}
