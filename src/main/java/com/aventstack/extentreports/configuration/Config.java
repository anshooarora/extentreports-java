package com.aventstack.extentreports.configuration;

public class Config {
    
    private String k;
    private Object v;

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
