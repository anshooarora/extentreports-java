/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemProperties implements Serializable {

    private static final long serialVersionUID = 8115692530725523101L;

    public void setSystemInfo(Map<String, String> info) {
        //if (this.info.size() > 0)
            //info.putAll(this.info);
        
        //this.info = info;
        this.info.putAll(info);
    }
    
    public void setSystemInfo(String k, String v) {
        info.put(k, v);
    }
    
    public Map<String, String> getSystemInfo() {
        return info;
    }
    
    public SystemProperties() {
        info = new LinkedHashMap<String, String>();        
    }
    
    private Map<String, String> info;
}
