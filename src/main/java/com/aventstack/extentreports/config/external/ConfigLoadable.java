package com.aventstack.extentreports.config.external;

@FunctionalInterface
public interface ConfigLoadable<T> {
    void apply();
}
