package com.aventstack.extentreports.reporter.klov.entity;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.NamedAttribute;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.model.ReportStats;
import com.aventstack.extentreports.model.context.NamedAttributeContext;

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
public class KlovReport {
    private String id;
    private int seq;
    private int count;
    private String project;
    private String projectName;
    private String name;
    private Date startTime;
    private Date endTime;
    private int duration;
    private String status = Status.PASS.toString();
    private AnalysisStrategy analysisStrategy;
    private boolean bdd;
    private Set<String> author;
    private Set<String> category;
    private Set<String> device;
    private long parent = 0;
    private long parentPass = 0;
    private long parentFail = 0;
    private long parentSkip = 0;
    private long parentWarning = 0;
    private long child = 0;
    private long childPass = 0;
    private long childFail = 0;
    private long childSkip = 0;
    private long childWarning = 0;
    private long grandchild = 0;
    private long grandchildPass = 0;
    private long grandchildFail = 0;
    private long grandchildSkip = 0;
    private long grandchildWarning = 0;

    public KlovReport(KlovEntity entity) {
        id = entity.getReportId();
        seq = entity.getReportSeq();
        name = entity.getReportName();
        project = entity.getProjectId();
        projectName = entity.getProjectName();
    }

    public void map(Report report) {
        status = report.getStatus().toLower();
        bdd = report.isBDD();
        author = fromNamedAttributeContext(report.getAuthorCtx().getSet());
        category = fromNamedAttributeContext(report.getCategoryCtx().getSet());
        device = fromNamedAttributeContext(report.getDeviceCtx().getSet());
        convertStats(report.getStats());
    }

    private <T extends NamedAttribute> Set<String> fromNamedAttributeContext(Set<NamedAttributeContext<T>> set) {
        return set.stream()
                .map(NamedAttributeContext::getAttr)
                .map(NamedAttribute::getName)
                .collect(Collectors.toSet());
    }

    private void convertStats(ReportStats stats) {
        analysisStrategy = stats.getAnalysisStrategy();
        parent = stats.sumStat(stats.getParent());
        parentPass = stats.getParent().get(Status.PASS);
        parentFail = stats.getParent().get(Status.FAIL);
        parentSkip = stats.getParent().get(Status.SKIP);
        parentWarning = stats.getParent().get(Status.WARNING);
        child = stats.sumStat(stats.getChild());
        childPass = stats.getChild().get(Status.PASS);
        childFail = stats.getChild().get(Status.FAIL);
        childSkip = stats.getChild().get(Status.SKIP);
        childWarning = stats.getChild().get(Status.WARNING);
        grandchild = stats.sumStat(stats.getGrandchild());
        grandchildPass = stats.getGrandchild().get(Status.PASS);
        grandchildFail = stats.getGrandchild().get(Status.FAIL);
        grandchildSkip = stats.getGrandchild().get(Status.SKIP);
        grandchildWarning = stats.getGrandchild().get(Status.WARNING);
    }
}
