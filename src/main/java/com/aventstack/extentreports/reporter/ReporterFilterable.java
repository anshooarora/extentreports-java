package com.aventstack.extentreports.reporter;

import com.aventstack.extentreports.reporter.configuration.EntityFilters;

@FunctionalInterface
public interface ReporterFilterable<T extends AbstractReporter> {
    EntityFilters<T> filter();
}
