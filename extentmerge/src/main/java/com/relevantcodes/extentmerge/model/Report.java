/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.relevantcodes.extentmerge.LogSettings;
import com.relevantcodes.extentmerge.SourceType;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

/**
 * ReportSingle represents a past run report
 * 
 * @author Anshoo
 *
 */
public class Report {
    private List<Test> testList;
    private LogStatus reportStatus = LogStatus.UNKNOWN;
    private SourceType sourceType;
    private Date startedTime;
    private Date endedTime;
    private UUID id;
    private MergedDataMaster mergedData;
    private TestIterator iter;
    
    private class TestIterator implements Iterator<Test> {
        private int testIterIndex = 0;
        
        public TestIterator() {
            testIterIndex = 0;
        }
        
        public boolean hasNext() {
            if (testList != null && testList.size() >= testIterIndex + 1) {
                return true;
            }
            
            return false;
        }

        public Test next() {
            if (hasNext()) {
                return testList.get(testIterIndex++);
            }
            
            return null; 
        }
        
    }
    
    public TestIterator testIterator() {
        iter = new TestIterator();
        
        return iter;
    }
    
    public void setTest(Test test) {
        testList.add(test);
    }
    
    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }

    public List<Test> getTestList() {
        return testList;
    }
    
    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }
    
    public SourceType getSourceType() {
        return sourceType;
    }
    
    public String getFormattedDate() {
        Date date = new Date(getStartedTime().getTime());
        
        SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateFormat());
        String dtFormatted = sdf.format(date);
        
        return dtFormatted;
    }
    
    public String getFormattedTime() {
        Date date = new Date(getStartedTime().getTime());
        
        SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogTimeFormat());
        String timeFormatted = sdf.format(date);

        return timeFormatted;
    }
    
    public String getFormattedRunDuration() {
        return DateTimeUtil.getDiff(endedTime, startedTime);
    }
    
    public Date getStartedTime() {
        return startedTime;
    }
    
    public void setStartedTime(String startedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateTimeFormat());
        
        try {
            this.startedTime = sdf.parse(startedTime);
        } 
        catch (ParseException e) {            
            e.printStackTrace();
        }
    }
    
    public Date getEndedTime() {
        return endedTime;
    }
    
    public void setEndedTime(String startedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateTimeFormat());
        
        try {
            this.endedTime = sdf.parse(startedTime);
        } 
        catch (ParseException e) {            
            e.printStackTrace();
        }
    }
    
    public UUID getId() {
        return id;
    }
    
    public void setEndedStatus(LogStatus reportStatus) {
        this.reportStatus = reportStatus;
    }
    
    public LogStatus getEndedStatus() {
        return reportStatus;
    }
    
    public int getTestsCount(Integer... statusArrayInt) {
        return mergedData.getTestsCountByStatusInt(statusArrayInt);
    }

    public Report() {
        id = UUID.randomUUID();
        testList = new ArrayList<Test>();
        mergedData = new MergedDataMaster(this);
    }
}
