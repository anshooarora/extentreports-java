/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

public class RunInfo {
    public void setSuiteStartTimestamp(String startedAt) {
        this.startedAt = startedAt;
    }
    
    public String getSuiteStartTimestamp() {
        return startedAt;
    }
    
    public void setSuiteEndTimestamp(String endedAt) {
        this.endedAt = endedAt;
    }
    
    public String getSuiteEndTimestamp() {
        return endedAt;
    }
    
    private String startedAt;
    private String endedAt;
    
    public RunInfo() { } 
}
