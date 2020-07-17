package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode
public abstract class NamedAttribute implements Serializable, BaseEntity {
    private static final long serialVersionUID = -804568330360505098L;
    private String name;
}
