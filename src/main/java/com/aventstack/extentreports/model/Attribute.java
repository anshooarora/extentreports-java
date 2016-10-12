package com.aventstack.extentreports.model;

import java.io.Serializable;

abstract class Attribute implements Serializable {

    private static final long serialVersionUID = 6491172989326625178L;

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
