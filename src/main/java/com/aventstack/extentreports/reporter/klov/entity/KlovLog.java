package com.aventstack.extentreports.reporter.klov.entity;

import java.util.Date;

import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KlovLog {
    private String project;
    private String report;
    private String test;
    private String testName;
    private Integer sequence;
    private String status;
    private String details;
    private String exception;
    private String stacktrace;
    private Date timestamp;
    private Integer mediaCount;
    private KlovMedia media;

    public KlovLog(KlovEntity entity) {
        project = entity.getProjectId();
        report = entity.getReportId();
    }

    public KlovLog transform(Log log, Test test) {
        sequence = log.getSeq();
        status = log.getStatus().toLower();
        details = log.getDetails();
        if (log.hasException()) {
            exception = log.getException().getName();
            stacktrace = log.getException().getStackTrace();
        }
        timestamp = log.getTimestamp();
        if (log.hasMedia()) {
            mediaCount = 1;
            media = new KlovMedia(log.getMedia());
        }
        return this;
    }
}
