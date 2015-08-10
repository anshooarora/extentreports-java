/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.Icon;
import com.relevantcodes.extentreports.source.StepHtml;
import com.relevantcodes.extentreports.source.TestHtml;
import com.relevantcodes.extentreports.support.DateTimeHelper;

class TestBuilder {
    public static String getSource(Test test) {
    	if (test.isChildNode) {
    		return "";
    	}
    	
        String testSource = TestHtml.getSource(3);
        
        if (test.log.size() > 0 && test.log.get(0).stepName != "") {
            testSource = TestHtml.getSource(4);
        }
        
        if (test.description == null || test.description == "") {
            testSource = testSource.replace(ExtentFlag.getPlaceHolder("descVis"), "style='display:none;'");
        }
        
        long diff = test.endedTime.getTime() - test.startedTime.getTime();
        long hours = diff / (60 * 60 * 1000) % 24;
        long mins = diff / (60 * 1000) % 60;
        long secs = diff / 1000 % 60;
        
        testSource = testSource
                .replace(ExtentFlag.getPlaceHolder("testName"), test.name)
                .replace(ExtentFlag.getPlaceHolder("testStatus"), test.status.toString().toLowerCase())
                .replace(ExtentFlag.getPlaceHolder("testStartTime"), DateTimeHelper.getFormattedDateTime(test.startedTime, LogSettings.logDateTimeFormat))
                .replace(ExtentFlag.getPlaceHolder("testEndTime"),  DateTimeHelper.getFormattedDateTime(test.endedTime, LogSettings.logDateTimeFormat))
                .replace(ExtentFlag.getPlaceHolder("testTimeTaken"), hours + "h " + mins + "m " + secs + "s")
                .replace(ExtentFlag.getPlaceHolder("testDescription"), test.description)
                .replace(ExtentFlag.getPlaceHolder("descVis"), "")
                .replace(ExtentFlag.getPlaceHolder("category"), "")
                .replace(ExtentFlag.getPlaceHolder("testWarnings"), TestHtml.getWarningSource(test.internalWarning));
        
        for (TestAttribute attr : test.categoryList) {
               testSource = testSource
                    .replace(ExtentFlag.getPlaceHolder("testCategory"), TestHtml.getCategorySource() + ExtentFlag.getPlaceHolder("testCategory"))
                    .replace(ExtentFlag.getPlaceHolder("category"), attr.getName());
        }
        
        String stepSrc = StepHtml.getSrc(2);
        
        if (test.log.size() > 0) {
            if (test.log.get(0).stepName != "") {
                stepSrc = StepHtml.getSrc(0);
            }
            
            for (int ix = 0; ix < test.log.size(); ix++) {
                testSource = testSource.replace(ExtentFlag.getPlaceHolder("step"), stepSrc + ExtentFlag.getPlaceHolder("step"))
                        .replace(ExtentFlag.getPlaceHolder("timeStamp"), DateTimeHelper.getFormattedDateTime(test.log.get(ix).timestamp, LogSettings.logTimeFormat))
                        .replace(ExtentFlag.getPlaceHolder("stepStatusU"), test.log.get(ix).logStatus.toString().toUpperCase())
                        .replace(ExtentFlag.getPlaceHolder("stepStatus"), test.log.get(ix).logStatus.toString().toLowerCase())
                        .replace(ExtentFlag.getPlaceHolder("statusIcon"), Icon.getIcon(test.log.get(ix).logStatus))
                        .replace(ExtentFlag.getPlaceHolder("stepName"), test.log.get(ix).stepName)
                        .replace(ExtentFlag.getPlaceHolder("details"), test.log.get(ix).details);
            }
        }
        
        testSource = testSource.replace(ExtentFlag.getPlaceHolder("step"), "");
        
        testSource = addChildTests(test, testSource, 1);
        
        /*
        String nodeSource;
        
        for (Test node : test.nodeList) {
            nodeSource = TestHtml.getNodeSource(3);
            
            if (node.log.size() > 0 && node.log.get(0).stepName != "") {
                nodeSource = TestHtml.getNodeSource(4);
            }
            
            diff = node.endedTime.getTime() - node.startedTime.getTime();
            hours = diff / (60 * 60 * 1000) % 24;
            mins = diff / (60 * 1000) % 60;
            secs = diff / 1000 % 60;
            
            testSource = testSource
                    .replace(ExtentFlag.getPlaceHolder("nodeList"), nodeSource + ExtentFlag.getPlaceHolder("nodeList"))
                    .replace(ExtentFlag.getPlaceHolder("nodeName"), node.name)
                    .replace(ExtentFlag.getPlaceHolder("nodeStatus"), node.status.toString().toLowerCase())
                    .replace(ExtentFlag.getPlaceHolder("nodeStartTime"), DateTimeHelper.getFormattedDateTime(node.startedTime, LogSettings.logDateTimeFormat))
                    .replace(ExtentFlag.getPlaceHolder("nodeEndTime"),  DateTimeHelper.getFormattedDateTime(node.endedTime, LogSettings.logDateTimeFormat))
                    .replace(ExtentFlag.getPlaceHolder("nodeTimeTaken"), hours + "h " + mins + "m " + secs + "s")
                    .replace(ExtentFlag.getPlaceHolder("nodeLevel"), "node-1x");
            
            stepSrc = StepHtml.getSrc(2);
            
            if (node.log.get(0).stepName != "") {
                stepSrc = StepHtml.getSrc(0);
            }
            
            for (int ix = 0; ix < node.log.size(); ix++) {
                testSource = testSource
                        .replace(ExtentFlag.getPlaceHolder("nodeStep"), stepSrc + ExtentFlag.getPlaceHolder("nodeStep"))
                        .replace(ExtentFlag.getPlaceHolder("timeStamp"), DateTimeHelper.getFormattedDateTime(node.log.get(ix).timestamp, LogSettings.logTimeFormat))
                        .replace(ExtentFlag.getPlaceHolder("stepStatusU"), node.log.get(ix).logStatus.toString().toUpperCase())
                        .replace(ExtentFlag.getPlaceHolder("stepStatus"), node.log.get(ix).logStatus.toString().toLowerCase())
                        .replace(ExtentFlag.getPlaceHolder("statusIcon"), Icon.getIcon(node.log.get(ix).logStatus))
                        .replace(ExtentFlag.getPlaceHolder("stepName"), node.log.get(ix).stepName)
                        .replace(ExtentFlag.getPlaceHolder("details"), node.log.get(ix).details);
            }
            
            testSource = testSource
                    .replace(ExtentFlag.getPlaceHolder("step"), "")
                    .replace(ExtentFlag.getPlaceHolder("nodeStep"), "");
            
            testSource = addChildTests(node, testSource, 2);
        }*/
        
        return testSource;
    }
    
    private static String addChildTests(Test test, String testSource, int nodeLevel) {
    	String nodeSource;
        long diff, hours, mins, secs;
        String stepSrc = "";

        for (Test node : test.nodeList) {
            nodeSource = TestHtml.getNodeSource(3);
            
            if (node.log.size() > 0 && node.log.get(0).stepName != "") {
                nodeSource = TestHtml.getNodeSource(4);
            }
            
            diff = node.endedTime.getTime() - node.startedTime.getTime();
            hours = diff / (60 * 60 * 1000) % 24;
            mins = diff / (60 * 1000) % 60;
            secs = diff / 1000 % 60;
            
            testSource = testSource
                    .replace(ExtentFlag.getPlaceHolder("nodeList"), nodeSource + ExtentFlag.getPlaceHolder("nodeList"))
                    .replace(ExtentFlag.getPlaceHolder("nodeName"), node.name)
                    .replace(ExtentFlag.getPlaceHolder("nodeStartTime"), DateTimeHelper.getFormattedDateTime(node.startedTime, LogSettings.logDateTimeFormat))
                    .replace(ExtentFlag.getPlaceHolder("nodeEndTime"),  DateTimeHelper.getFormattedDateTime(node.endedTime, LogSettings.logDateTimeFormat))
                    .replace(ExtentFlag.getPlaceHolder("nodeTimeTaken"), hours + "h " + mins + "m " + secs + "s")
                    .replace(ExtentFlag.getPlaceHolder("nodeLevel"), "node-" + nodeLevel + "x");
            
            if (node.log.size() > 0) {
            	testSource = testSource.replace(ExtentFlag.getPlaceHolder("nodeStatus"), node.status.toString().toLowerCase());
            			
            	stepSrc = StepHtml.getSrc(2);
            	
	            if (node.log.get(0).stepName != "") {
	                stepSrc = StepHtml.getSrc(0);
	            }
	            
	            for (int ix = 0; ix < node.log.size(); ix++) {
	                testSource = testSource
	                        .replace(ExtentFlag.getPlaceHolder("nodeStep"), stepSrc + ExtentFlag.getPlaceHolder("nodeStep"))
	                        .replace(ExtentFlag.getPlaceHolder("timeStamp"), DateTimeHelper.getFormattedDateTime(node.log.get(ix).timestamp, LogSettings.logTimeFormat))
	                        .replace(ExtentFlag.getPlaceHolder("stepStatusU"), node.log.get(ix).logStatus.toString().toUpperCase())
	                        .replace(ExtentFlag.getPlaceHolder("stepStatus"), node.log.get(ix).logStatus.toString().toLowerCase())
	                        .replace(ExtentFlag.getPlaceHolder("statusIcon"), Icon.getIcon(node.log.get(ix).logStatus))
	                        .replace(ExtentFlag.getPlaceHolder("stepName"), node.log.get(ix).stepName)
	                        .replace(ExtentFlag.getPlaceHolder("details"), node.log.get(ix).details);
	            }
            }
            
            testSource = testSource
                    .replace(ExtentFlag.getPlaceHolder("step"), "")
                    .replace(ExtentFlag.getPlaceHolder("nodeStep"), "");
            
            if (node.hasChildNodes) {
            	testSource = addChildTests(node, testSource, ++nodeLevel);
            	--nodeLevel;
            }
        }
        
    	return testSource;
    }
    
    public static String getQuickTestSummary(Test test) {
    	if (test.isChildNode) {
    		return "";
    	}
    	
        String src = TestHtml.getSourceQuickView();
        int passed, failed, fatal, error, warning, info, skipped, unknown;
        
        passed = failed = fatal = error = warning = info = skipped = unknown = 0;
        
        for (int ix = 0; ix < test.log.size(); ix++) {
            if (test.log.get(ix).logStatus == LogStatus.PASS)
                passed++; 
            else if (test.log.get(ix).logStatus == LogStatus.FAIL)
                failed++;
            else if (test.log.get(ix).logStatus == LogStatus.FATAL)
                fatal++;
            else if (test.log.get(ix).logStatus == LogStatus.ERROR)
                error++;
            else if (test.log.get(ix).logStatus == LogStatus.WARNING)
                warning++;
            else if (test.log.get(ix).logStatus == LogStatus.INFO)
                info++;
            else if (test.log.get(ix).logStatus == LogStatus.SKIP)
                skipped++;
            else if (test.log.get(ix).logStatus == LogStatus.UNKNOWN)
                unknown++;
        }
        
        src = src.replace(ExtentFlag.getPlaceHolder("testName"), test.name)
                .replace(ExtentFlag.getPlaceHolder("testWarnings"), TestHtml.getWarningSource(test.internalWarning))
                .replace(ExtentFlag.getPlaceHolder("currentTestPassedCount"), "" + passed)
                .replace(ExtentFlag.getPlaceHolder("currentTestFailedCount"), "" + failed)
                .replace(ExtentFlag.getPlaceHolder("currentTestFatalCount"), "" + fatal)
                .replace(ExtentFlag.getPlaceHolder("currentTestErrorCount"), "" + error)
                .replace(ExtentFlag.getPlaceHolder("currentTestWarningCount"), "" + warning)
                .replace(ExtentFlag.getPlaceHolder("currentTestInfoCount"), "" + info)
                .replace(ExtentFlag.getPlaceHolder("currentTestSkippedCount"), "" + skipped)
                .replace(ExtentFlag.getPlaceHolder("currentTestUnknownCount"), "" + unknown)
                .replace(ExtentFlag.getPlaceHolder("currentTestRunStatus"), "" + test.status.toString().toLowerCase())
                .replace(ExtentFlag.getPlaceHolder("currentTestRunStatusU"), "" + test.status.toString());
        
        return src;
    }
    
    public class LogCounts {
    	public int pass = 0;
    	public int fail = 0;
    	public int fatal = 0;
    	public int error = 0;
    	public int warning = 0;
    	public int info = 0;
    	public int skip = 0;
    	public int unknown = 0;
    	
    	public LogCounts() { }
    }
}
