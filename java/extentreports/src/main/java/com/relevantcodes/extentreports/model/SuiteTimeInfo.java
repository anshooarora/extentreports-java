/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.Date;

public class SuiteTimeInfo {
    public void setSuiteStartTimestamp(Date startedAt) {
        this.startedAt = startedAt;
    }
    
    public Date getSuiteStartTimestamp() {
        return startedAt;
    }
    
    public void setSuiteEndTimestamp(Date endedAt) {
        this.endedAt = endedAt;
    }
    
    public Date getSuiteEndTimestamp() {
        return endedAt;
    }
    
    public long getTimeDiff() {
    	return endedAt.getTime() - startedAt.getTime();
    }
    
    private Date startedAt;
    private Date endedAt;
    
    public SuiteTimeInfo() { } 
}
