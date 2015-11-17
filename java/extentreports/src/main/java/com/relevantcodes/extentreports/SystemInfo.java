/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.net.InetAddress;
import java.util.Map;

import com.relevantcodes.extentreports.model.SystemProperties;

class SystemInfo {
    private SystemProperties systemProperties;

    public Map<String, String> getInfo() {
        if (systemProperties == null) {
            return null;
        }
        
        return systemProperties.getSystemInfo();
    }
    
    public void setInfo(Map<String, String> info) {
        for (Map.Entry<String, String> entry : info.entrySet()) {
            systemProperties.setSystemInfo(entry.getKey(), entry.getValue());
        }
    }
    
    public void setInfo(String param, String value) {
        systemProperties.setSystemInfo(param, value);
    }
    
    private void setInfo() {
        if (systemProperties == null) {
            systemProperties = new SystemProperties();
        }
        
        systemProperties.setSystemInfo("User Name", System.getProperty("user.name"));
        systemProperties.setSystemInfo("OS", System.getProperty("os.name"));
        systemProperties.setSystemInfo("Java Version", System.getProperty("java.version"));
        
        try {
            systemProperties.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
        } 
        catch(Exception e) { }
    }
    
    public SystemInfo() { 
        setInfo();
    }
}
