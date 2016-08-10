package com.relevantcodes.extentreports.configuration;

public class Config {
    
    String k;
    Object v;

    public void setKey(String k) {
        this.k = k;
    }
    
    public String getKey() {
        return k;
    }
    
    public void setValue(Object v) {
        this.v = v;
    }
    
    public Object getValue() {
        return v;
    }
}
