/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import com.relevantcodes.extentreports.utils.DateTimeUtil;

public class SuiteTimeInfo {
    public void setSuiteStartTimestamp(long startedAt) {
        this.startedAt = startedAt;
    }
    
    public long getSuiteStartTimestamp() {
        return startedAt;
    }
    
    public void setSuiteEndTimestamp(long endedAt) {
        this.endedAt = endedAt;
    }
    
    public long getSuiteEndTimestamp() {
        return endedAt;
    }
    
    public String getTimeDiff() {
    	return DateTimeUtil.getDiff(endedAt, startedAt);
    }
    
    private long startedAt;
    private long endedAt;
    
    public SuiteTimeInfo() { } 
}
