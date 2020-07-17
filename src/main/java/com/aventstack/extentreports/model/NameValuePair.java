package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NameValuePair extends NamedAttribute implements Serializable, BaseEntity {
    private static final long serialVersionUID = 6613712676810614719L;
    private String value;

    public NameValuePair(String name, String value) {
        super(name);
        this.value = value;
    }
}
