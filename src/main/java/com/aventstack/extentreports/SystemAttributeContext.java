package com.aventstack.extentreports;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.model.SystemAttribute;

public class SystemAttributeContext {
    private List<SystemAttribute> sysAttrCollection;

    public SystemAttributeContext() { 
        sysAttrCollection = new ArrayList<>();
    }
    
    public void setSystemAttribute(SystemAttribute sa) {
        sysAttrCollection.add(sa);
    }
    
    public List<SystemAttribute> getSystemAttributeList() { return sysAttrCollection; }
    
    public void clear() { sysAttrCollection.clear(); }
    
}
