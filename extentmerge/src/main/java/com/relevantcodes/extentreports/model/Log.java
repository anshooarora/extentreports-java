/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.Date;

import com.relevantcodes.extentmerge.LogSettings;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.source.Icon;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

public class Log extends LogSettings {
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
    	return DateTimeUtil.getFormattedDateTime(timestamp.getTime(), getLogTimeFormat());
    }
    
    public String getFormattedTime() {
    	return DateTimeUtil.getFormattedDateTime(timestamp.getTime(), getLogDateTimeFormat());
    }
    
    public String getIcon() {
    	return Icon.getIcon(logStatus);
    }
    
    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }
    
    public LogStatus getLogStatus() {
        return logStatus;
    }
    
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
    
    public String getStepName() {
        return stepName;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getDetails() {
        return details;
    }
    
    private Date timestamp;
    private LogStatus logStatus;
    private String stepName;
    private String details;
}
