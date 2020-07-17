package com.aventstack.extentreports.observer.entity;

import com.aventstack.extentreports.model.NamedAttribute;
import com.aventstack.extentreports.model.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NamedAttributeTestEntity implements ObservedEntity {
    private NamedAttribute attribute;
    private Test test;
}
