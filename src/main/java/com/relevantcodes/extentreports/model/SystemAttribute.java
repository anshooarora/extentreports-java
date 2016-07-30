package com.relevantcodes.extentreports.model;

public class SystemAttribute extends Attribute {
    
    public String getName() {
        return getKey();
    }
    
    public void setName(String name) {
        setKey(name);
    }

}
