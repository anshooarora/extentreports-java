package com.aventstack.extentreports.model;

import java.io.Serializable;

abstract class Attribute implements Serializable {

    private static final long serialVersionUID = 6491172989326625178L;

    private String k;
    private String v;
    
    public Attribute(String k, String v) {
        this.k = k;
        this.v = v;
    }
    
    public Attribute(String k) {
        this(k, null);
    }

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
