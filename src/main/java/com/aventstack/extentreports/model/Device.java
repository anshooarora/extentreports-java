package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
public final class Device extends NamedAttribute implements Serializable {
    private static final long serialVersionUID = -5585746447313067401L;

    public Device(String name) {
        super(name);
    }
}
