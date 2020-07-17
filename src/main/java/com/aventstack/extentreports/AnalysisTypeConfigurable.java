package com.aventstack.extentreports;

@FunctionalInterface
public interface AnalysisTypeConfigurable {
    void setAnalysisStrategy(AnalysisStrategy strategy);
}
