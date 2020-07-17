package com.aventstack.extentreports.model.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;

public class TestService {
    public static boolean deleteTest(List<Test> list, Test test) {
        boolean removed = list.removeIf(x -> x.getId() == test.getId());
        if (!removed)
            list.forEach(x -> deleteTest(x.getChildren(), test));
        return removed;
    }

    public static Optional<Test> findTest(List<Test> list, String name) {
        Optional<Test> test = list.stream().filter(x -> x.getName().equals(name)).findFirst();
        if (!test.isPresent())
            for (Test t : list)
                return findTest(t.getChildren(), name);
        return test;
    }

    public static Boolean testHasScreenCapture(Test test, Boolean deep) {
        if (deep) {
            Boolean hasScreenCapture = !test.getMedia().isEmpty()
                    || test.getLogs().stream().anyMatch(Log::hasMedia);
            if (!hasScreenCapture)
                hasScreenCapture = test.getChildren().stream().anyMatch(x -> testHasScreenCapture(x, deep));
            return hasScreenCapture;
        }
        return test.hasScreenCapture();
    }

    public static List<ExceptionInfo> aggregateExceptions(List<Test> testList) {
        List<ExceptionInfo> list = new ArrayList<>();
        for (Test test : testList) {
            list.addAll(aggregateExceptions(test));
            if (!test.getChildren().isEmpty())
                aggregateExceptions(test.getChildren());
        }
        return list;
    }

    public static List<ExceptionInfo> aggregateExceptions(Test test) {
        return test.getLogs().stream()
                .filter(x -> x.getException() != null)
                .map(x -> x.getException())
                .collect(Collectors.toList());
    }

    public static Test createTest(Class<? extends IGherkinFormatterModel> type, String name, String description) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Test name cannot be null or empty");
        return Test.builder()
                .bddType(type)
                .name(name)
                .description(description)
                .endTime(Calendar.getInstance().getTime()).build();
    }

    public static Test createTest(String name, String description) {
        return createTest(null, name, description);
    }

    public static Test createTest(String name) {
        return createTest(name, null);
    }
}
