package com.aventstack.extentreports.model;

import com.aventstack.extentreports.Status;

@FunctionalInterface
public interface RunResult {
    Status getStatus();
}
