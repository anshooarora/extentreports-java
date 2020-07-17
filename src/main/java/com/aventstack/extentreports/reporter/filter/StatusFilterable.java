package com.aventstack.extentreports.reporter.filter;

import java.util.Set;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Report;

@FunctionalInterface
public interface StatusFilterable {
    Report filterAndGet(Report report, Set<Status> set);
}
