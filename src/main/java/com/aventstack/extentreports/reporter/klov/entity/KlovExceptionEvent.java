package com.aventstack.extentreports.reporter.klov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KlovExceptionEvent {
    private String id;
    private String project;
    private String report;
    private String name;
    private String stacktrace;
    private Integer testCount;
}
