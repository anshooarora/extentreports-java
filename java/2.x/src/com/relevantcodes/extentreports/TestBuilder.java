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
        
        String[] testFlags = { ExtentFlag.getPlaceHolder("testName"),
        		ExtentFlag.getPlaceHolder("testStatus"),
        		ExtentFlag.getPlaceHolder("testStartTime"),
        		ExtentFlag.getPlaceHolder("testEndTime"),
        		ExtentFlag.getPlaceHolder("testTimeTaken"),
        		ExtentFlag.getPlaceHolder("testDescription"),
        		ExtentFlag.getPlaceHolder("descVis"),
        		ExtentFlag.getPlaceHolder("category"),
        		ExtentFlag.getPlaceHolder("testWarnings")
        };
        String[] testValues = { test.name,
        		test.status.toString().toLowerCase(),
        		DateTimeHelper.getFormattedDateTime(test.startedTime, LogSettings.logDateTimeFormat),
        		DateTimeHelper.getFormattedDateTime(test.endedTime, LogSettings.logDateTimeFormat),
        		hours + "h " + mins + "m " + secs + "s",
        		test.description,
        		"",
        		"",
        		TestHtml.getWarningSource(test.internalWarning)
        };
        
        testSource = SourceBuilder.build(testSource, testFlags, testValues);
        
        for (TestAttribute attr : test.categoryList) {
               testSource = testSource
                    .replace(ExtentFlag.getPlaceHolder("testCategory"), TestHtml.getCategorySource() + ExtentFlag.getPlaceHolder("testCategory"))
                    .replace(ExtentFlag.getPlaceHolder("category"), attr.getName());
        }
        
        String stepSrc = StepHtml.getSrc(2);
        
        String[] stepFlags = { ExtentFlag.getPlaceHolder("step"),
        		ExtentFlag.getPlaceHolder("timeStamp"),
        		ExtentFlag.getPlaceHolder("stepStatusU"),
        		ExtentFlag.getPlaceHolder("stepStatus"),
        		ExtentFlag.getPlaceHolder("statusIcon"),
        		ExtentFlag.getPlaceHolder("stepName"),
        		ExtentFlag.getPlaceHolder("details")
        };
        String[] stepValues;
        
        if (test.log.size() > 0) {
            if (test.log.get(0).stepName != "") {
                stepSrc = StepHtml.getSrc(0);
            }
            
            for (int ix = 0; ix < test.log.size(); ix++) {
            	stepValues = new String[] { stepSrc + ExtentFlag.getPlaceHolder("step"),
            			DateTimeHelper.getFormattedDateTime(test.log.get(ix).timestamp, LogSettings.logTimeFormat),
            			test.log.get(ix).logStatus.toString().toUpperCase(),
            			test.log.get(ix).logStatus.toString().toLowerCase(),
            			Icon.getIcon(test.log.get(ix).logStatus),
            			test.log.get(ix).stepName,
            			test.log.get(ix).details
            	};
            	
            	testSource = SourceBuilder.build(testSource, stepFlags, stepValues);
            }
        }
        
        testSource = testSource.replace(ExtentFlag.getPlaceHolder("step"), "");
        
        testSource = addChildTests(test, testSource, 1);

        return testSource;
    }
    
    private static String addChildTests(Test test, String testSource, int nodeLevel) {
    	String nodeSource;
        long diff, hours, mins, secs;
        String stepSrc = "";
        String[] testValues, stepValues;
        
        String[] testFlags = { ExtentFlag.getPlaceHolder("nodeList"),
        		ExtentFlag.getPlaceHolder("nodeName"),
        		ExtentFlag.getPlaceHolder("nodeStartTime"),
        		ExtentFlag.getPlaceHolder("nodeEndTime"),
        		ExtentFlag.getPlaceHolder("nodeTimeTaken"),
        		ExtentFlag.getPlaceHolder("nodeLevel")
        };
        String[] stepFlags = { ExtentFlag.getPlaceHolder("nodeStep"),
        		ExtentFlag.getPlaceHolder("timeStamp"),
        		ExtentFlag.getPlaceHolder("stepStatusU"),
        		ExtentFlag.getPlaceHolder("stepStatus"),
        		ExtentFlag.getPlaceHolder("statusIcon"),
        		ExtentFlag.getPlaceHolder("stepName"),
        		ExtentFlag.getPlaceHolder("details")
        };

        for (Test node : test.nodeList) {
            nodeSource = TestHtml.getNodeSource(3);
            
            if (node.log.size() > 0 && node.log.get(0).stepName != "") {
                nodeSource = TestHtml.getNodeSource(4);
            }
            
            diff = node.endedTime.getTime() - node.startedTime.getTime();
            hours = diff / (60 * 60 * 1000) % 24;
            mins = diff / (60 * 1000) % 60;
            secs = diff / 1000 % 60;
            
            testValues = new String[] { nodeSource + ExtentFlag.getPlaceHolder("nodeList"),
	            	node.name,
	            	DateTimeHelper.getFormattedDateTime(node.startedTime, LogSettings.logDateTimeFormat),
	            	DateTimeHelper.getFormattedDateTime(node.endedTime, LogSettings.logDateTimeFormat),
	            	hours + "h " + mins + "m " + secs + "s",
	            	"node-" + nodeLevel + "x"
            };
            
            testSource = SourceBuilder.build(testSource, testFlags, testValues);

            if (node.log.size() > 0) {
            	testSource = testSource.replace(ExtentFlag.getPlaceHolder("nodeStatus"), node.status.toString().toLowerCase());
            			
            	stepSrc = StepHtml.getSrc(2);
            	
	            if (node.log.get(0).stepName != "") {
	                stepSrc = StepHtml.getSrc(0);
	            }
	            
	            for (int ix = 0; ix < node.log.size(); ix++) {
	            	stepValues = new String[] { stepSrc + ExtentFlag.getPlaceHolder("nodeStep"),
	            			DateTimeHelper.getFormattedDateTime(node.log.get(ix).timestamp, LogSettings.logTimeFormat),
	            			node.log.get(ix).logStatus.toString().toUpperCase(),
	            			node.log.get(ix).logStatus.toString().toLowerCase(),
	            			Icon.getIcon(node.log.get(ix).logStatus),
	            			node.log.get(ix).stepName,
	            			node.log.get(ix).details	            		
		            };
	            	
	            	testSource = SourceBuilder.build(testSource, stepFlags, stepValues);
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
    	LogCounts lc = new TestBuilder().new LogCounts().getLogCounts(test);
    	
    	String[] flags = { ExtentFlag.getPlaceHolder("testName"), 
    			ExtentFlag.getPlaceHolder("testWarnings"),
    			ExtentFlag.getPlaceHolder("currentTestPassedCount"),
    			ExtentFlag.getPlaceHolder("currentTestFailedCount"),
    			ExtentFlag.getPlaceHolder("currentTestFatalCount"),
    			ExtentFlag.getPlaceHolder("currentTestErrorCount"),
    			ExtentFlag.getPlaceHolder("currentTestWarningCount"),
    			ExtentFlag.getPlaceHolder("currentTestInfoCount"),
    			ExtentFlag.getPlaceHolder("currentTestSkippedCount"),
    			ExtentFlag.getPlaceHolder("currentTestUnknownCount"),
    			ExtentFlag.getPlaceHolder("currentTestRunStatus"),
    			ExtentFlag.getPlaceHolder("currentTestRunStatusU")
    	};
    	
    	String[] values = { test.name,
    			TestHtml.getWarningSource(test.internalWarning),
    			"" + lc.pass,
    			"" + lc.fail,
    			"" + lc.fatal,
    			"" + lc.error,
    			"" + lc.warning,
    			"" + lc.info,
    			"" + lc.skip,
    			"" + lc.unknown,
    			test.status.toString().toLowerCase(),
    			test.status.toString()
    	};
    	
    	src = SourceBuilder.build(src, flags, values);
        
        return src;
    }
    
    private class LogCounts {
    	public int pass = 0;
    	public int fail = 0;
    	public int fatal = 0;
    	public int error = 0;
    	public int warning = 0;
    	public int info = 0;
    	public int skip = 0;
    	public int unknown = 0;
    	
    	public LogCounts getLogCounts(Test test) {
    		for (int ix = 0; ix < test.log.size(); ix++) {
                if (test.log.get(ix).logStatus == LogStatus.PASS)
                    pass++; 
                else if (test.log.get(ix).logStatus == LogStatus.FAIL)
                    fail++;
                else if (test.log.get(ix).logStatus == LogStatus.FATAL)
                    fatal++;
                else if (test.log.get(ix).logStatus == LogStatus.ERROR)
                    error++;
                else if (test.log.get(ix).logStatus == LogStatus.WARNING)
                    warning++;
                else if (test.log.get(ix).logStatus == LogStatus.INFO)
                    info++;
                else if (test.log.get(ix).logStatus == LogStatus.SKIP)
                    skip++;
                else if (test.log.get(ix).logStatus == LogStatus.UNKNOWN)
                    unknown++;
            }
    		
    		for (Test node : test.nodeList) {
    			getLogCounts(node);
    		}
    		
    		return this;
    	}
    	
    	public LogCounts() { }
    }
}
