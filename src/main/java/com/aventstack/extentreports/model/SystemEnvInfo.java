package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
public class SystemEnvInfo extends NameValuePair implements Serializable, BaseEntity {
    private static final long serialVersionUID = 8643409194315531097L;

    public SystemEnvInfo(String name, String value) {
        super(name, value);
    }
}
