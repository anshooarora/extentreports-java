/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.relevantcodes.extentmerge.App.ConsoleArgs;
import com.relevantcodes.extentmerge.model.Report;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.utils.DateTimeUtil;
import com.relevantcodes.extentreports.utils.ExtentUtils;
import com.relevantcodes.extentreports.utils.FileReaderEx;

class FileInfoAggregator implements IAggregator {
    private List<String> files;
    private Document doc;
    private LogStatus reportStatus = LogStatus.UNKNOWN;
    
    public FileInfoAggregator(List<String> files) {
        this.files = files;
    }
    
    public List<Report> getAggregatedData() {
        if (files == null) {
            return null;
        }
        
        Report report;
        List<Test> testList;
        List<Report> reportList = new ArrayList<Report>();
        
        for (String filePath : files) {
            int index = filePath.lastIndexOf(".");
            
            if (index > 0 && filePath.substring(index + 1).toLowerCase().contains("htm")) {
                Logger.info("Scanning HTML file " + filePath);
                
                testList = getTestList(filePath);
                                
                if (testList != null && testList.size() > 0) {
                    report = new Report();
                    report.setSourceType(SourceType.HTML);
                    
                    report.setStartedTime(doc.select(".suite-started-time").first().text());
                    report.setEndedTime(doc.select(".suite-ended-time").first().text());
                    report.setTestList(testList);
                    report.setEndedStatus(reportStatus);
                    reportList.add(report);
                    
                    resetReportStatus();
                }
            }
        }
        
        return reportList;
    }
    
    private void resetReportStatus() {
        reportStatus = LogStatus.UNKNOWN;
    }
    
    private List<Test> getTestList(String filePath) {
        doc = Jsoup.parse(FileReaderEx.readAllText(filePath));
        
        Elements body = doc.select("body.extent");
        
        // continue if the HTML file's body element contains class=extent
        if (body != null & body.size() > 0) {
            Logger.info(filePath + " is a valid Extent HTML file.");
            
            String startedTime = body.select(".suite-started-time").first().text();
            String endedTime = body.select(".suite-ended-time").first().text();
            
            if (!isReportWithinDateThreshold(startedTime, endedTime)) {
                return null;
            }
            
            Elements allTests = doc.select(".test");
            
            List<Test> extentTestList = new ArrayList<Test>();
            Test extentTest;
            
            // build Test model
            for (Element test : allTests) {
                extentTest = new Test();
                
                extentTest.setName(test.select(".test-name").first().text());
                extentTest.setDescription(test.select(".test-desc").first().text());
                
                extentTest.setStatus(
                        ExtentUtils.toLogStatus(
                                test.select(".test-status").first().text()
                            )
                );

                extentTest.setStartedTime(
                        DateTimeUtil.getDate(
                                test.select(".test-started-time").first().text(), 
                                LogSettings.getLogDateTimeFormat()
                        )
                );
                
                extentTest.setEndedTime(
                        DateTimeUtil.getDate(
                                test.select(".test-ended-time").first().text(), 
                                LogSettings.getLogDateTimeFormat()
                        )
                );
                
                Elements categories = test.select(".category");
                for (Element category : categories) {
                    extentTest.setCategory(new Category(category.text()));
                }
                
                extentTest.setLog(getLogList(test, false));
                extentTest.setNodeList(getNodeList(test));
                
                if (extentTest.getNodeList() != null) {
                    extentTest.hasChildNodes = true;
                } 
                else {
                    TestTrends.setTest(extentTest.getName(), extentTest.getStatus());
                }
                
                trackReportStatus(extentTest.getStatus());
                
                extentTestList.add(extentTest);
            }
            
            return extentTestList;
        }
        
        return null;
    }
    
    private void trackReportStatus(LogStatus logStatus) {
        if (reportStatus == LogStatus.FATAL) return;
        
        if (logStatus == LogStatus.FATAL) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.FAIL) return;
        
        if (logStatus == LogStatus.FAIL) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.ERROR) return;
        
        if (logStatus == LogStatus.ERROR) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.WARNING) return;
        
        if (logStatus == LogStatus.WARNING) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.PASS) return;
        
        if (logStatus == LogStatus.PASS) {
            reportStatus = LogStatus.PASS;
            return;
        }
        
        if (reportStatus == LogStatus.SKIP) return;
        
        if (logStatus == LogStatus.SKIP) {
            reportStatus = LogStatus.SKIP;
            return;
        }
        
        if (reportStatus == LogStatus.INFO) return;
        
        if (logStatus == LogStatus.INFO) {
            reportStatus = LogStatus.INFO;
            return;
        }
        
        reportStatus = LogStatus.UNKNOWN;
    }
    
    private List<Test> getNodeList(Element test) {
        Elements allNodes = test.select(".node-list > li");
        Test extentNode;        
        
        if (allNodes != null && allNodes.size() > 0) {
            List<Test> extentNodeList = new ArrayList<Test>();
            
            for (Element node : allNodes) {
                extentNode = new Test();
                
                extentNode.setName(node.select(".test-node-name").first().text());
                
                extentNode.setStatus(
                        ExtentUtils.toLogStatus(
                                node.select(".test-status").first().text()
                            )
                );

                extentNode.setStartedTime(
                        DateTimeUtil.getDate(
                                node.select(".test-started-time").first().text(), 
                                LogSettings.getLogDateTimeFormat()
                        )
                );
                
                extentNode.setEndedTime(
                        DateTimeUtil.getDate(
                                node.select(".test-ended-time").first().text(), 
                                LogSettings.getLogDateTimeFormat()
                        )
                );
                
                extentNode.setLevelClass(node.attr("class"));
                extentNode.setLog(getLogList(node, true));
                
                TestTrends.setTest(extentNode.getName(), extentNode.getStatus());
                
                extentNodeList.add(extentNode);
            }
            
            return extentNodeList;
        }
        
        return null;
    }
    
    private ArrayList<Log> getLogList(Element test, boolean node) {
        Elements logList;
        logList = node ? test.select(".collapsible-body tbody > tr") : test.select(".test-body > .test-steps > table > tbody > tr");
        
        ArrayList<Log> extentLogList = new ArrayList<Log>();
        Log extentLog;
        
        for (Element log : logList) {
            extentLog = new Log();
            
            extentLog.setTimestamp(
                    DateTimeUtil.getDate(
                            log.select(".timestamp").first().text(), 
                            LogSettings.getLogTimeFormat()
                    )
            );
            
            if (log.select(".step-name").size() == 1) {
                extentLog.setStepName(log.select(".step-name").first().text());
            }
            
            extentLog.setLogStatus(ExtentUtils.toLogStatus(log.select(".status").first().attr("title")));
            extentLog.setDetails(log.select(".step-details").first().text());
            
            extentLogList.add(extentLog);
        }
        
        return extentLogList;
    }
    
    private boolean isReportWithinDateThreshold(String reportStartDate, String reportEndDate) {
        ConsoleArgs args = App.getConsoleArgs();
        
        long startMillis = DateTimeUtil.getDate(reportStartDate, LogSettings.getLogDateTimeFormat()).getTime();
        long endMillis = DateTimeUtil.getDate(reportEndDate, LogSettings.getLogDateTimeFormat()).getTime();
        
        if (startMillis >= args.getStartMillis() && endMillis <= args.getEndMillis()) {
            return true;
        }
        
        return false;
    }
}
