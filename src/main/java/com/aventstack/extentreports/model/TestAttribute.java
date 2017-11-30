package com.aventstack.extentreports.model;

import java.io.Serializable;

public abstract class TestAttribute extends Attribute implements Serializable {

    static final long serialVersionUID = 1010210091204302766L;

    public TestAttribute(String k) {
        super(k);
    }
    
    public String getName() {
        return getKey();
    }
    
    public void setName(String name) {
        setKey(name);
    }
    
    public String getDescription() {
        return getValue();
    }
    
    public void setDescription(String description) {
        setValue(description);
    }
    
}