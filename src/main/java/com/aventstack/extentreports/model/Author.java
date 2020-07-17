package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
public final class Author extends NamedAttribute implements Serializable, BaseEntity {
    private static final long serialVersionUID = -1589597649718748057L;

    public Author(String name) {
        super(name);
    }
}
