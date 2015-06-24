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
    
    public void clear() {
        systemProperties.info.clear();
    }
    
    public Map<String, String> getInfo() {
        if (systemProperties == null) {
            return null;            
        }
        
        return systemProperties.info;
    }
    
    public void setInfo(Map<String, String> info) {
        for (Map.Entry<String, String> entry : info.entrySet()) {
            systemProperties.info.put(entry.getKey(), entry.getValue());
        }
    }
    
    public void setInfo(String param, String value) {
        systemProperties.info.put(param, value);
    }
    
    private void setInfo() {
        if (systemProperties == null) {
            systemProperties = new SystemProperties();
        }
        
        systemProperties.info.put("User Name", System.getProperty("user.name"));
        systemProperties.info.put("OS", System.getProperty("os.name"));
        systemProperties.info.put("Java Version", System.getProperty("java.version"));
        
        try {
            systemProperties.info.put("Host Name", InetAddress.getLocalHost().getHostName());
        } 
        catch(Exception e) { }
    }
    
    public SystemInfo() { 
        setInfo();
    }
}
