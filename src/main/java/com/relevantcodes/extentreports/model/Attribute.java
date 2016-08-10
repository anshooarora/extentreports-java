package com.relevantcodes.extentreports.model;

abstract class Attribute {
    
    protected String k;
    protected String v;
    
    protected String getKey() {
        return k;
    }
    
    protected void setKey(String k) {
        this.k = k;
    }
    
    public String getValue() {
        return v;
    }
    
    public void setValue(String v) {
        this.v = v;
    }
}
