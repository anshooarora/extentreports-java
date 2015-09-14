/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.HashMap;
import java.util.Map;

public class SystemProperties {
    public void setSystemInfo(Map<String, String> info) {
        this.info = info;
    }
    
    public void setSystemInfo(String k, String v) {
        info.put(k, v);
    }
    
    public Map<String, String> getSystemInfo() {
        return info;
    }
    
    public SystemProperties() {
        info = new HashMap<String, String>();        
    }
    
    private Map<String, String> info;
}
