package com.aventstack.extentreports.reporter.configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.AbstractReporter;

import lombok.Getter;

@Getter
public class StatusFilter<T extends AbstractReporter> {
    private Set<Status> status;
    private EntityFilters<?> configurer;

    public StatusFilter(EntityFilters<T> configurer) {
        this.configurer = configurer;
    }

    @SuppressWarnings("unchecked")
    public EntityFilters<T> as(Set<Status> status) {
        this.status = status;
        return (EntityFilters<T>) configurer;
    }

    public EntityFilters<T> as(List<Status> status) {
        return as(new HashSet<>(status));
    }

    public EntityFilters<T> as(Status[] status) {
        return as(Arrays.asList(status));
    }
}
