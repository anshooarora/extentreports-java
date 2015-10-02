package com.relevantcodes.extentreports.source;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ReportSummaryView {
	public static String getSource() {
		return "<table><tr>" +
					"<td class='report-date'><span class='label date'></span></td>" +
					"<td class='report-source'><span class='label text-white'></span></td>" +
					"<td class='run-duration'></td>" +
					"<td class='tests-count'></td>" +
					"<td class='report-progress'></td>" +
				"</tr></table>";
	}
	
	public static String getProgressBar(Document reportView) {
		float allTests = reportView.select(".test-collection > li").size();
		
		Elements passed = reportView.select(".test-collection > .test.pass");
		int passedCount = passed != null ? passed.size() : 0;
		float passedPercentage = passedCount != 0 ? Math.round((passedCount / allTests) * 100) : passedCount;
		
		Elements failed = reportView.select(".test-collection > .test.fail, .test-collection > .test.fatal");
		int failedCount = failed != null ? failed.size() : 0;
		float failedPercentage = failed != null ? Math.round((failedCount / allTests) * 100) : 0; 

		Elements skipped = reportView.select(".test-collection > .test.skip");
		int skippedCount = skipped != null ? skipped.size() : 0;
		float skippedPercentage = failed != null ? Math.round((skippedCount / allTests) * 100) : 0; 
		
		Elements others = reportView.select(".test-collection > .test.error, .test-collection > .test.warning, .test-collection > .test.unknown");
		int othersCount = others != null ? others.size() : 0;
		float othersPercentage = others != null ? Math.round((othersCount / allTests) * 100) : 0; 
		
		return "<div class='progress2'>" +
					"<div class='progress-bar2 progress-bar-success progress-bar-striped' style='width: " + passedPercentage + "%'>" +
						"<span class='sr-only'>" + passedCount +"</span>" +
					"</div>" +
					"<div class='progress-bar2 progress-bar-skip progress-bar-striped' style='width: " + skippedPercentage + "%'>" +
						"<span class='sr-only'>" + skippedCount +"</span>" +
					"</div>" +
					"<div class='progress-bar2 progress-bar-warning progress-bar-striped' style='width: " + othersPercentage + "%'>" +
						"<span class='sr-only'>" + othersCount +"</span>" +
					"</div>" +
					"<div class='progress-bar2 progress-bar-danger progress-bar-striped' style='width: " + failedPercentage + "%'>" +
						"<span class='sr-only'>" + failedCount +"</span>" +
					"</div>" +
				"</div>";
	}
}
