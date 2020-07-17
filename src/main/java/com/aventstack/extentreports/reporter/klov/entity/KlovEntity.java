package com.aventstack.extentreports.reporter.klov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KlovEntity {
    private String projectName;
    private String projectId;
    private String reportName;
    private String reportId;
    private int reportSeq;
}
