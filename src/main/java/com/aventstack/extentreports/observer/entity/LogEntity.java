package com.aventstack.extentreports.observer.entity;

import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LogEntity implements ObservedEntity {
    private Log log;
    private Test test;
}
