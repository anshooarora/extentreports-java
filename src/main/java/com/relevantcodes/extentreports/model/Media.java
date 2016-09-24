package com.relevantcodes.extentreports.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Media implements Serializable {
    
    private static final long serialVersionUID = 7703803003727779654L;

    public void setSource(String source) {
        this.source = source;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setTestName(String testName) {
        this.testName = testName;
    }
    
    public String getTestName() {
        return testName;
    }
    
    public void setTestId(UUID id) {
        this.id = id;
    }
    
    public UUID getTestId() {
        return id;
    }
    
    private UUID id;
    private String source;
    private String testName;
}
