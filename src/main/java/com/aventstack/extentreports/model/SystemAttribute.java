package com.aventstack.extentreports.model;

public class SystemAttribute extends Attribute {

    private static final long serialVersionUID = 7531709191041382750L;

    public String getName() {
        return getKey();
    }
    
    public void setName(String name) {
        setKey(name);
    }

}
