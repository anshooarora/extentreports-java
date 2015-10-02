package com.relevantcodes.extentmerge;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.relevantcodes.extentreports.TestBuilder;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.NavHtml;
import com.relevantcodes.extentreports.source.ReportSummaryView;
import com.relevantcodes.extentreports.source.Table;
import com.relevantcodes.extentreports.source.TestView;
import com.relevantcodes.extentreports.utils.DateTimeUtil;
import com.relevantcodes.extentreports.utils.Resources;
import com.relevantcodes.extentreports.utils.Writer;

class ReportBuilder {
	private List<Report> reportList;

	private Document extentMergeDoc;

	private String reportDate = null;
	
	
	public ReportBuilder(List<Report> reportList) {
		this.reportList = reportList;
	}
	
	public void build(String outputFile) {
		String sourceFile = "com/relevantcodes/extentmerge/resources/Template.html";
		String extentSource = Resources.getText(sourceFile);
		
		extentMergeDoc = Jsoup.parse(extentSource);
		
		for (Report report : reportList) {
			Document reportView = Jsoup.parseBodyFragment(TestView.getSource());

			reportDate = DateTimeUtil.getFormattedDateTime(
							report.getStartedTime().getTime(), 
							LogSettings.getLogDateTimeFormat()
						);

			for (Test test : report.getTestList()) {
				Element htmlTest = TestBuilder.getHTMLTest(test);
				
				reportView
					.select(".test-collection")
					.first()
						.appendChild(htmlTest);
			}
			
			extentMergeDoc
				.select("#run-summary-view tbody")
				.first()
					.append(getReportSummary(report, reportView).outerHtml());
			
			reportView
				.select(".report-view")
				.first()
				.addClass(report.getId().toString());
			
			reportView
				.select(".report-date")
				.first()
				.attr("id", reportDate);
			
			extentMergeDoc
				.select("#report-view")
				.first()
					.prependChild(reportView.select(".report-view").first());
		}
		
		buildDashboardEntities();
		buildTrendsTable();
		buildSideNavLinks(reportList);

		//.replace("\n", "")
		//.replace("\r", "")
		//.replace("    ", "")
		//.replace("\t",  "")
		Writer.getInstance().write(
					new File(outputFile), 
					Parser.unescapeEntities(
							extentMergeDoc
								.outerHtml(), 
							true
					)
			);
	}
	
	private void buildSideNavLinks(List<Report> reportList) {
		Element li;
		
		for (Report report : reportList) {
			li = Jsoup.parseBodyFragment(NavHtml.getReportItemSource()).select(".report-item").first();
			
			li
				.attr("id", report.getId().toString())
				.select(".report-date")
					.first()
					.text(NavHtml.getParsedTime(report.getStartedTime().getTime()));
			
			// adds side-nav link for the report
			// uses its date-time stamp
			// yyyy-MM-dd hh:mm:ss
			extentMergeDoc
				.select(".side-nav .reports-placeholder")
				.first()
					.after(li.select(".report-item").first());
		}
	}
	
	private void buildDashboardEntities() {
		int testsCount = extentMergeDoc.select(".test").size();
		extentMergeDoc
			.select("#dashboard-view .total-tests > span")
			.first()
				.text(String.valueOf(testsCount));
		
		int testsPassed = extentMergeDoc.select(".test.pass").size();
		extentMergeDoc
			.select("#dashboard-view .total-tests-passed > span")
			.first()
				.text(String.valueOf(testsPassed));
		
		int testsFailed = extentMergeDoc.select(".test.fail, .test.fatal").size();
		extentMergeDoc
			.select("#dashboard-view .total-tests-failed > span")
			.first()
				.text(String.valueOf(testsFailed));
		
		int stepsCount = extentMergeDoc.select("td.status").size();
		extentMergeDoc
			.select("#dashboard-view .total-steps > span")
			.first()
				.text(String.valueOf(stepsCount));
		
		int stepsPassed = extentMergeDoc.select("td.status.pass").size();
		extentMergeDoc
			.select("#dashboard-view .total-steps-passed > span")
			.first()
				.text(String.valueOf(stepsPassed));
		
		int stepsFailed = extentMergeDoc.select("td.status.fail, td.status.fatal").size();
		extentMergeDoc
			.select("#dashboard-view .total-steps-failed > span")
			.first()
				.text(String.valueOf(stepsFailed));
	}
	
	private void buildTrendsTable() {
		Element tbody = extentMergeDoc.select("#trends-view .pass-trends tbody").first();
		Element row;
		int ix = 0;
		
		for (Map.Entry<String, Integer> entry : TestTrends.getTopPassed().entrySet()) {
			row = Jsoup.parseBodyFragment(Table.getRowSource(2)).select("tr").first();
			
			row.select("td:first-child").first().text(entry.getKey());
			row.select("td:nth-child(2)").first().text(String.valueOf(entry.getValue()));
			
			tbody.appendChild(row);

			if (ix++ == 9) {
				break;
			}
		}
		
		ix = 0;
		
		tbody = extentMergeDoc.select("#trends-view .fail-trends tbody").first();
		
		for (Map.Entry<String, Integer> entry : TestTrends.getTopFailed().entrySet()) {
			row = Jsoup.parseBodyFragment(Table.getRowSource(2)).select("tr").first();
			
			row.select("td:first-child").first().text(entry.getKey());
			row.select("td:nth-child(2)").first().text(String.valueOf(entry.getValue()));
			
			tbody.appendChild(row);
			
			if (ix++ == 9) {
				break;
			}
		}
	}

	private Document getReportSummary(Report report, Document reportView) {
		Document reportSummaryView = Jsoup.parseBodyFragment(ReportSummaryView.getSource());
		
		// report-date
		reportSummaryView
			.select(".report-date > span")
			.first()
				.text(reportDate);
		
		// report-source (@see SourceType)
		reportSummaryView
			.select(".report-source > span")
			.first()
				.addClass(report.getSourceType().toString().toLowerCase())
				.text(report.getSourceType().toString());
		
		// report run duration
		reportSummaryView
			.select(".run-duration")
			.first()
				.text(DateTimeUtil.getDiff(report.getEndedTime(), report.getStartedTime()));
		
		// tests count in report
		reportSummaryView
			.select(".tests-count")
			.first()
				.text(String.valueOf(reportView.select(".test").size()));
		
		// progress bar
		reportSummaryView
			.select(".report-progress")
			.first()
				.text(ReportSummaryView.getProgressBar(reportView));
		
		return reportSummaryView;
	}
}
