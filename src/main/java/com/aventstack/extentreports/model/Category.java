package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
public final class Category extends NamedAttribute implements Serializable, BaseEntity {
    private static final long serialVersionUID = 5282727628859917993L;

    public Category(String name) {
        super(name);
    }
}
