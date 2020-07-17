package com.aventstack.extentreports.reporter.klov.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class KlovAttribute {
    protected String project;
    protected String report;
    protected String name;
    protected Long timeTaken;
    protected List<String> testId;
    protected List<String> testName;
    protected Integer testCount;
}
