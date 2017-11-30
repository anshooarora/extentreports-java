package com.aventstack.extentreports.common;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    private static ExtentReports extent;

    public static synchronized void setReporter(ExtentReports extent) {
        ExtentTestManager.extent = extent;
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }

    public static synchronized ExtentTest createTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        return test;
    }
    
}
