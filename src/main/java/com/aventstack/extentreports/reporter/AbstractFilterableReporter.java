package com.aventstack.extentreports.reporter;

import java.util.Set;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.model.service.ReportFilterService;
import com.aventstack.extentreports.reporter.filter.StatusFilterable;

import lombok.Getter;

@Getter
public class AbstractFilterableReporter extends AbstractReporter implements StatusFilterable {
    @Override
    public Report filterAndGet(Report report, Set<Status> set) {
        if (report == null || report.getTestList().isEmpty())
            return report;
        if (set != null)
            return ReportFilterService.filter(report, set);
        return report;
    }
}
