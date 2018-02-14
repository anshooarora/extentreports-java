package com.aventstack.extentreports.view.extenthtml;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import org.testng.annotations.Test;

import java.io.File;

/*
 * https://github.com/anshooarora/extentreports-java/issues/1016
 */
public class BDDAppendExistingTest {

    @Test(priority=1)
    public void test1() throws ClassNotFoundException {
        ExtentReports extentReports = new ExtentReports();
        File reportFileLocation = new File("test-output/BDDAppendExistingTest.html");
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(reportFileLocation);
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
        extentReports.attachReporter(extentHtmlReporter);
        ExtentTest feature = extentReports.createTest("Test 1");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Test Scenario 1");
        scenario.createNode(new GherkinKeyword("Given"), "some step");
        extentReports.flush();
    }

    @Test(priority=2)
    public void test2() throws ClassNotFoundException {
        ExtentReports extentReports = new ExtentReports();
        File reportFileLocation = new File("test-output/BDDAppendExistingTest.html");
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(reportFileLocation);
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
        extentHtmlReporter.setAppendExisting(true);
        extentReports.attachReporter(extentHtmlReporter);
        ExtentTest feature = extentReports.createTest("Test 2");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Test Scenario 2");
        scenario.createNode(new GherkinKeyword("Given"), "some step");
        extentReports.flush();
    }
    
}
