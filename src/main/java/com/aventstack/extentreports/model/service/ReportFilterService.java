package com.aventstack.extentreports.model.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Device;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContext;
import com.aventstack.extentreports.model.context.filter.NamedAttributeTestContextFilter;

public class ReportFilterService {
    public static Report filter(Report report, Set<Status> statusList) {
        if (report.getTestList().isEmpty())
            return report;
        Report filtered = Report.builder().build();
        filtered.getLogs().addAll(report.getLogs());
        filtered.setStartTime(report.getStartTime());
        filtered.setEndTime(report.getEndTime());
        List<Test> list = report.getTestList().stream()
                .filter(x -> statusList.contains(x.getStatus()))
                .collect(Collectors.toList());
        list.forEach(filtered.getTestList()::add);
        filtered.getStats().update(list);
        Set<NamedAttributeContext<Author>> authorCtx = new NamedAttributeTestContextFilter<Author>()
                .filter(report.getAuthorCtx(), statusList);
        authorCtx.stream().forEach(x -> filtered.getAuthorCtx().addContext(x.getAttr(), x.getTestList()));
        Set<NamedAttributeContext<Category>> categoryCtx = new NamedAttributeTestContextFilter<Category>()
                .filter(report.getCategoryCtx(), statusList);
        categoryCtx.stream().forEach(x -> filtered.getCategoryCtx().addContext(x.getAttr(), x.getTestList()));
        Set<NamedAttributeContext<Device>> deviceCtx = new NamedAttributeTestContextFilter<Device>()
                .filter(report.getDeviceCtx(), statusList);
        deviceCtx.stream().forEach(x -> filtered.getDeviceCtx().addContext(x.getAttr(), x.getTestList()));
        return filtered;
    }
}
