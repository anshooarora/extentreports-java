/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.Icon;
import com.relevantcodes.extentreports.source.StepHtml;
import com.relevantcodes.extentreports.source.TestHtml;

class TestBuilder {
    public static String getSource(Test test) {
        String src = TestHtml.getSource(3);
        
        if (test.log.size() > 0 && test.log.get(0).stepName != "") {
            src = TestHtml.getSource(4);
        }
        
        if (test.description == null || test.description == "") {
            src = src.replace(ExtentFlag.getPlaceHolder("descVis"), "style='display:none;'");
        }
        
        src = src.replace(ExtentFlag.getPlaceHolder("testName"), test.name)
                .replace(ExtentFlag.getPlaceHolder("testStatus"), test.status.toString().toLowerCase())
                .replace(ExtentFlag.getPlaceHolder("testStartTime"), test.startedAt)
                .replace(ExtentFlag.getPlaceHolder("testEndTime"), test.endedAt)
                .replace(ExtentFlag.getPlaceHolder("testStatus"), test.status.toString().toLowerCase())
                .replace(ExtentFlag.getPlaceHolder("testDescription"), test.description)
                .replace(ExtentFlag.getPlaceHolder("descVis"), "");    
        
        String stepSrc = StepHtml.getSrc(2);
        
        if (test.log.size() > 0) {
            if (test.log.get(0).stepName != "") {
                stepSrc = StepHtml.getSrc(0);
            }
            
            for (int ix = 0; ix < test.log.size(); ix++) {
                src = src.replace(ExtentFlag.getPlaceHolder("step"), stepSrc + ExtentFlag.getPlaceHolder("step"))
                        .replace(ExtentFlag.getPlaceHolder("timeStamp"), test.log.get(ix).timestamp)
                        .replace(ExtentFlag.getPlaceHolder("stepStatusU"), test.log.get(ix).logStatus.toString().toUpperCase())
                        .replace(ExtentFlag.getPlaceHolder("stepStatus"), test.log.get(ix).logStatus.toString().toLowerCase())
                        .replace(ExtentFlag.getPlaceHolder("statusIcon"), Icon.getIcon(test.log.get(ix).logStatus))
                        .replace(ExtentFlag.getPlaceHolder("stepName"), test.log.get(ix).stepName)
                        .replace(ExtentFlag.getPlaceHolder("details"), test.log.get(ix).details);
            }
        }
        
        src = src.replace(ExtentFlag.getPlaceHolder("step"), "");
        
        return src;
    }
    
	public static String getQuickTestSummary(Test test) {
    	String src = TestHtml.getSourceQuickView();
    	Integer passed, failed, fatal, error, warning, info, skipped, unknown;
    	
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
}
