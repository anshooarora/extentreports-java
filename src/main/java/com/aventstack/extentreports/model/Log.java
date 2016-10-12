package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;

public class Log implements Serializable {

    private static final long serialVersionUID = 1594512136869286425L;
    
    private Markup markup;
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
    
    public void setMarkup(Markup markup) {
        this.markup = markup;
    }
    public Markup getMarkup() {
        return markup;
    }
    
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
    public int getSequence() {
        return sequence;
    }
    
}
