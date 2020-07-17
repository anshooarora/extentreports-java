package com.aventstack.extentreports.entity;

import java.util.Arrays;

import org.testng.Assert;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.model.ReportStats;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.service.TestService;

public class ReportStatsTest {
    @org.testng.annotations.Test
    public void analysisStrategyDefault() {
        ReportStats stats = new ReportStats();
        Assert.assertNotNull(stats.getAnalysisStrategy());
        Assert.assertEquals(stats.getAnalysisStrategy(), AnalysisStrategy.TEST);
    }

    @org.testng.annotations.Test
    public void allLevelMapsNonNull() {
        ReportStats stats = new ReportStats();
        Assert.assertNotNull(stats.getChild());
        Assert.assertNotNull(stats.getChildPercentage());
        Assert.assertNotNull(stats.getGrandchild());
        Assert.assertNotNull(stats.getGrandchildPercentage());
        Assert.assertNotNull(stats.getLog());
        Assert.assertNotNull(stats.getLogPercentage());
        Assert.assertNotNull(stats.getParent());
        Assert.assertNotNull(stats.getParentPercentage());
    }

    @org.testng.annotations.Test
    public void statsSize() {
        Test test = TestService.createTest("Test");
        Report report = Report.builder().build();
        report.getTestList().add(test);
        Assert.assertEquals(report.getStats().getParent().size(), 0);
        report.getStats().update(report.getTestList());
        Assert.assertEquals(report.getStats().getParent().size(), Status.values().length);
    }

    @org.testng.annotations.Test
    public void statsAll() {
        Report report = Report.builder().build();
        report.getStats().update(report.getTestList());
        // check if all Status fields are present
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getParent().containsKey(x)));
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getChild().containsKey(x)));
        Arrays.asList(Status.values())
                .forEach(x -> Assert.assertTrue(report.getStats().getGrandchild().containsKey(x)));
        Assert.assertEquals(report.getStats().getParent().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.FAIL).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.SKIP).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.WARNING).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.INFO).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.FAIL).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.SKIP).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.WARNING).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.INFO).longValue(), 0);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.FAIL).longValue(), 0);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.SKIP).longValue(), 0);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.WARNING).longValue(), 0);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.INFO).longValue(), 0);
    }

    @org.testng.annotations.Test
    public void statsTestStatus() {
        Test test = TestService.createTest("Test");
        Report report = Report.builder().build();
        report.getTestList().add(test);
        report.getStats().update(report.getTestList());
        // check if all Status fields are present
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getParent().containsKey(x)));
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getChild().containsKey(x)));
        Arrays.asList(Status.values())
                .forEach(x -> Assert.assertTrue(report.getStats().getGrandchild().containsKey(x)));
        test.setStatus(Status.FAIL);
        report.getStats().update(report.getTestList());
        Assert.assertEquals(report.getStats().getParent().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.FAIL).longValue(), 1);
    }

    @org.testng.annotations.Test
    public void statsChildStatus() {
        Test test = TestService.createTest("Test");
        Test node = TestService.createTest("Node");
        node.setStatus(Status.SKIP);
        test.addChild(node);
        Report report = Report.builder().build();
        report.getTestList().add(test);
        report.getStats().update(report.getTestList());
        // check if all Status fields are present
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getParent().containsKey(x)));
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getChild().containsKey(x)));
        Arrays.asList(Status.values())
                .forEach(x -> Assert.assertTrue(report.getStats().getGrandchild().containsKey(x)));
        Assert.assertEquals(report.getStats().getParent().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.SKIP).longValue(), 1);
        Assert.assertEquals(report.getStats().getChild().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.SKIP).longValue(), 1);
    }

    @org.testng.annotations.Test
    public void statsGrandchildStatus() {
        Test test = TestService.createTest("Test");
        Test node = TestService.createTest("Node");
        Test grandchild = TestService.createTest("Grandchild");
        grandchild.setStatus(Status.FAIL);
        node.addChild(grandchild);
        test.addChild(node);
        Report report = Report.builder().build();
        report.getTestList().add(test);
        report.getStats().update(report.getTestList());
        // check if all Status fields are present
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getParent().containsKey(x)));
        Arrays.asList(Status.values()).forEach(x -> Assert.assertTrue(report.getStats().getChild().containsKey(x)));
        Arrays.asList(Status.values())
                .forEach(x -> Assert.assertTrue(report.getStats().getGrandchild().containsKey(x)));
        Assert.assertEquals(report.getStats().getParent().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getParent().get(Status.FAIL).longValue(), 1);
        Assert.assertEquals(report.getStats().getChild().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getChild().get(Status.FAIL).longValue(), 1);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.PASS).longValue(), 0);
        Assert.assertEquals(report.getStats().getGrandchild().get(Status.FAIL).longValue(), 1);
    }
}
