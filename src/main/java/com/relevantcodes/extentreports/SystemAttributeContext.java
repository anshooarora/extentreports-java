package com.relevantcodes.extentreports;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.relevantcodes.extentreports.model.SystemAttribute;

public class SystemAttributeContext {
    List<SystemAttribute> saList;

    public SystemAttributeContext() { 
        saList = new ArrayList<>();
        
        SystemAttribute sa = new SystemAttribute();
        sa.setName("User Name"); 
        sa.setValue(System.getProperty("user.name")); 
        saList.add(sa);
        
        sa = new SystemAttribute();
        sa.setName("OS"); 
        sa.setValue(System.getProperty("os.name"));  
        saList.add(sa);
        
        sa = new SystemAttribute();
        sa.setName("Java Version"); 
        sa.setValue(System.getProperty("java.version"));  
        saList.add(sa);
        
        sa = new SystemAttribute();
        sa.setName("Host Name");
        try {
            sa.setValue(InetAddress.getLocalHost().getHostName()); 
            saList.add(sa);
        } 
        catch(Exception e) {
            sa.setValue(""); 
            saList.add(sa); 
        }
    }
    
    public void setSystemAttribute(SystemAttribute sa) {
        saList.add(sa);
    }
    
    public List<SystemAttribute> getSystemAttributeList() { return saList; }
}
