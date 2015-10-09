/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.TestBuilder;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.NavHtml;
import com.relevantcodes.extentreports.source.ReportSummaryView;
import com.relevantcodes.extentreports.source.Table;
import com.relevantcodes.extentreports.source.ReportView;
import com.relevantcodes.extentreports.utils.DateTimeUtil;
import com.relevantcodes.extentreports.utils.Resources;
import com.relevantcodes.extentreports.utils.Writer;

class ReportBuilder {
    private List<Report> reportList;
    private Document extentMergeDoc;

    private String reportDate = null;
    private String outputFile = null;    
    
    public ReportBuilder(List<Report> reportList) {
        this.reportList = reportList;
    }
    
    public void createDocument(String outputFile) {
    	this.outputFile = outputFile;
    	
    	String sourceFile = "com/relevantcodes/extentmerge/resources/Template.html";
        String extentSource = Resources.getText(sourceFile);
        
        extentMergeDoc = Jsoup.parse(extentSource);
        
        if (reportList.size() == 0) {
            Logger.info("There were no valid Extent .HTM/HTML or .DB files found in your input source");
            Logger.info("No merged report will be created");
            return;
        }
        
        reportList = getSortedReportList(reportList);
        
        for (Report report : reportList) {
            Document reportView = Jsoup.parseBodyFragment(ReportView.getSource());

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
            
            // adds individual report summary to Dashboard
            // Date | Source | Duration | Test Count | Summary
            extentMergeDoc
                .select("#run-summary-view tbody")
                .first()
                    .prepend(getReportSummary(report, reportView).outerHtml());
            
            reportView
                .select(".report-view")
                .first()
                .addClass(report.getId().toString())
                .addClass(report.getEndedStatus().toString())
                .attr("reportid", report.getId().toString());
            
            // adds a hidden element under ReportList (side-nav, report-name by date-time)
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
        buildPassedTrendsTable();
        buildFailedTrendsTable();
        buildSideNavLinks(reportList);

        extentMergeDoc
            .select("#logs-view .card-panel")
            .first()
                .append(getExtentMergeLogs());
    }
    
    public void customize(String cssFilePath, String jsFilePath) {
    	if (cssFilePath != null && !cssFilePath.equals("")) {
    		cssFilePath = cssFilePath.trim();
    		
    		String cssLink = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
    		
    		if (cssFilePath.substring(0, 1).equals(new String(".")) || cssFilePath.substring(0, 1).equals(new String("/")) || cssFilePath.substring(0, 4).equals("http"))
            	cssLink = "<link href='" + cssFilePath + "' rel='stylesheet' type='text/css' />";
    		
    		extentMergeDoc.select("style").last().after(cssLink);
    	}
    	
    	if (jsFilePath != null && !jsFilePath.equals("")) {    		
    		jsFilePath = jsFilePath.trim();
    		
    		String jsLink = "<script src='file://" + jsFilePath + "'></script>";

            if (jsFilePath.substring(0, 1).equals(new String(".")) || jsFilePath.substring(0, 1).equals(new String("/")) || jsFilePath.substring(0, 4).equals("http"))
                jsLink = "<script src='" + jsFilePath + "'></script>";
            
            extentMergeDoc.select("script").last().after(jsLink); 
    	}
    }
    
    public void writeFile() {
    	Writer.getInstance().write(
                new File(outputFile), 
                Parser.unescapeEntities(
                        extentMergeDoc
                            .outerHtml(), 
                        true
                )
        );
    }
    
    private List<Report> getSortedReportList(List<Report> reportList) {
        // sort all reports by dates
        Map<Date, UUID> map = new TreeMap<Date, UUID>(Collections.reverseOrder());
        for (Report report : reportList) {
            map.put(report.getStartedTime(), report.getId());
        }
        
        // create new list and add all reports (sorted)
        List<Report> sortedReportList = new ArrayList<Report>();
        for (Map.Entry<Date, UUID> entry : map.entrySet()) {
            for (Report report : reportList) {
                if (report.getStartedTime().getTime() == entry.getKey().getTime()) {
                    sortedReportList.add(report);
                }
            }
        }
        
        return sortedReportList;
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
    
    // creates entries for the following entries in Dashboard view:
    //    Total Tests
    //    Total Tests Passed
    //    Total Tests Failed
    //    Total Steps
    //    Total Steps Passed
    //    Total Steps Failed
    private void buildDashboardEntities() {
        int testsCount = extentMergeDoc.select(""
                + ".node-list > li, "
                + ".test:not(.hasChildren)").size();
        extentMergeDoc
            .select("#dashboard-view .total-tests > span")
            .first()
                .text(String.valueOf(testsCount));
        
        int testsPassed = extentMergeDoc.select(""
                + ".node-list > li > .test-node.pass, "
                + ".test:not(.hasChildren).pass").size();
        extentMergeDoc
            .select("#dashboard-view .total-tests-passed > span")
            .first()
                .text(String.valueOf(testsPassed));
        
        int testsFailed = extentMergeDoc.select(""
                + ".node-list > li > .test-node.fail, "
                + ".test:not(.hasChildren).fail, "
                + ".node-list > li > .test-node.fatal, "
                + ".test:not(.hasChildren).fatal").size();
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
    
    // Builds the trends table for passed trends
    private void buildPassedTrendsTable() {
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
    }
    
    // Builds the trends table for failed trends
    private void buildFailedTrendsTable() {
        Element tbody = extentMergeDoc.select("#trends-view .fail-trends tbody").first();
        Element row;
        int ix = 0;
        
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

    // Dashboard View -> Report Summary
    // Columns:
    //     Date | Source | Duration | Test Count | Summary | 
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
                .text(String.valueOf(reportView.select(".node-list > li, .test:not(.hasChildren)").size()));
        
        // progress bar
        reportSummaryView
            .select(".report-progress")
            .first()
                .text(ReportSummaryView.getProgressBar(reportView));
        
        return reportSummaryView;
    }
    
    private String getExtentMergeLogs() {
        String s = "";
        
        for (Log log : Logger.getLogs()) {
            if (log.getLogStatus() != LogStatus.UNKNOWN) {
                s += "<p>";
                s += "[" + DateTimeUtil.getFormattedDateTime(log.getTimestamp().getTime(), LogSettings.getLogDateTimeFormat()) + "] ";
                s += "[" + log.getLogStatus().toString().toUpperCase() + "] ";
                s += log.getDetails();
                s += "</p>";
            }
        }
        
        return s;
    }
}
