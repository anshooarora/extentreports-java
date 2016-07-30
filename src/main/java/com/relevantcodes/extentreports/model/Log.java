package com.relevantcodes.extentreports.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.relevantcodes.extentreports.Status;

public class Log implements Serializable {

    private static final long serialVersionUID = 1594512136869286425L;
    
    private Date timestamp;
    private Status logStatus;
    private String stepName;
    private String details;
    
    private int sequence;
    
    public Log() { timestamp = Calendar.getInstance().getTime(); }
    
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(Status logStatus) {
        this.logStatus = logStatus;
    }
    public Status getStatus() {
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
    
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
    public int getSequence() {
        return sequence;
    }
    
}
