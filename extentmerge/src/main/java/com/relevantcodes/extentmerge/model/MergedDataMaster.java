package com.relevantcodes.extentmerge.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;

public class MergedDataMaster {
    private List<Report> reportList;
    
    public int getTestsCountByStatusInt(Integer... statusArrayInt) {
        LogStatus[] statusArray = getLogStatusFromInt(statusArrayInt);
        
        int testCount = 0;
        
        for (Report report : reportList) {
            testCount += getTestCounts(report.getTestList(), statusArray);
        }
        
        return testCount;
    }
    
    private int getTestCounts(List<Test> testList, LogStatus... statusArray) {
        int testCount = 0;
        
        for (Test test : testList) {
            if (test.hasChildNodes && !test.isChildNode) {
                testCount += getTestCounts(test.getNodeList(), statusArray);
            }
            else {
                if (statusArray == null || statusArray.length == 0) {
                    testCount++;
                }
                else {
                    for (LogStatus status : statusArray) {
                        testCount += status == test.getStatus() ? 1 : 0;
                    }
                }
            }
        }
        
        return testCount;
    }
    
    public int getStepsCountByStatusInt(Integer... statusArrayInt) {
        LogStatus[] statusArray = getLogStatusFromInt(statusArrayInt);
        
        int stepCount = 0;
        
        for (Report report : reportList) {
            stepCount += getStepCounts(report.getTestList(), statusArray);
        }

        return stepCount;
    }
    
    private int getStepCounts(List<Test> testList, LogStatus[] statusArray) {
        int stepCount = 0;
        
        for (Test test : testList) {
            if (test.hasChildNodes && !test.isChildNode) {
                stepCount += getStepCounts(test.getNodeList(), statusArray);
            }
            else {
                Iterator<Log> iter = test.logIterator();
                
                if (statusArray == null || statusArray.length == 0) {
                    while (iter.hasNext()) {
                        iter.next();
                        
                        stepCount++;
                    }
                }
                else {
                    Log log;
                    
                    while (iter.hasNext()) {
                        log = iter.next();
                        
                        for (LogStatus status : statusArray) {
                            stepCount += status == log.getLogStatus() ? 1 : 0;
                        }
                    }
                }
            }
        }
        
        return stepCount;
    }
    
    private LogStatus[] getLogStatusFromInt(Integer[] statusArrayInt) {
        LogStatus[] logStatusArray = new LogStatus[statusArrayInt.length];
        
        for (int ix = 0; ix < statusArrayInt.length; ix++) {
            logStatusArray[ix] = LogStatus.values()[statusArrayInt[ix]];
        }
        
        return logStatusArray;
    }
    
    public MergedDataMaster(List<Report> reportList) {
        this.reportList = reportList;
    }
    
    public MergedDataMaster(Report report) {
        reportList = new ArrayList<Report>();
        reportList.add(report);
    }
}
