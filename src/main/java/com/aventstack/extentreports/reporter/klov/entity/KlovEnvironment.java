package com.aventstack.extentreports.reporter.klov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KlovEnvironment {
    private String id;
    private String project;
    private String report;
    private String name;
    private String value;
}
