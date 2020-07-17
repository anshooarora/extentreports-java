package com.aventstack.extentreports.reporter.klov.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.aventstack.extentreports.model.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KlovTest {
    private final KlovEntity entity;

    private long extentId;
    private String project;
    private String report;
    private String reportName;
    private long reportSeq;
    private String name;
    private Integer level;
    private String status;
    private boolean bdd;
    private String bddType;
    private int childCount = 0;
    private int logCount = 0;
    private int mediaCount = 0;
    private long duration = 0L;
    private boolean leaf;
    private Date endTime;
    private Date startTime;
    private String description;
    private boolean categorized;
    private String exception;
    private List<KlovTest> children;
    private List<KlovLog> logs;
    private List<KlovMedia> media;
    private Set<String> category;
    private Set<String> device;
    private Set<String> author;

    public KlovTest(KlovEntity entity) {
        this.entity = entity;
        project = entity.getProjectId();
        report = entity.getReportId();
        reportName = entity.getReportName();
        reportSeq = entity.getReportSeq();
    }

    public KlovTest transform(Test test, KlovReport report) {
        extentId = test.getId();
        name = test.getName();
        level = test.getLevel();
        status = test.getStatus().toLower();
        bdd = test.isBDD();
        if (bdd)
            bddType = test.getBddType().getSimpleName();
        childCount = test.getChildren().size();
        logCount = test.getLogs().size();
        mediaCount = test.getMedia().size();
        startTime = test.getStartTime();
        endTime = test.getEndTime();
        duration = test.timeTaken();
        leaf = test.isLeaf();
        description = test.getDescription();
        categorized = test.hasAttributes();
        if (test.hasException())
            exception = test.getExceptions().get(0).getName();
        logs = test.getLogs().stream().map(x -> new KlovLog(entity).transform(x, test)).collect(Collectors.toList());
        children = test.getChildren().stream().map(x -> new KlovTest(entity).transform(x, report))
                .collect(Collectors.toList());
        media = test.getMedia().stream().map(KlovMedia::new).collect(Collectors.toList());
        author = test.getAuthorSet().stream().map(x -> x.getName()).collect(Collectors.toSet());
        category = test.getCategorySet().stream().map(x -> x.getName()).collect(Collectors.toSet());
        device = test.getDeviceSet().stream().map(x -> x.getName()).collect(Collectors.toSet());
        if (report != null) {
            project = report.getProject();
            this.report = report.getId();
            reportName = report.getName();
            reportSeq = report.getSeq();
        }
        return this;
    }
}
