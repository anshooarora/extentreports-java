package com.aventstack.extentreports.entity.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.GherkinDialectManager;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.service.TestService;

public class TestServiceTest {
    private static final String DESCRIPTION = "test.description";

    private Test getTest() {
        return TestService.createTest("Test");
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void testWithNullName() {
        TestService.createTest(null);
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void testWithEmptyName() {
        TestService.createTest("");
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void testWithNullName2() {
        TestService.createTest(null, DESCRIPTION);
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void testWithEmptyName2() {
        TestService.createTest("", DESCRIPTION);
    }

    @org.testng.annotations.Test
    public void testWithNullDescription() {
        Test test = TestService.createTest("Test", null);
        Assert.assertNull(test.getDescription());
    }

    @org.testng.annotations.Test
    public void testWithEmptyDescription() {
        Test test = TestService.createTest("Test", "");
        Assert.assertEquals(test.getDescription(), "");
    }

    @org.testng.annotations.Test
    public void testWithDescription() {
        Test test = TestService.createTest("Test", DESCRIPTION);
        Assert.assertEquals(test.getDescription(), DESCRIPTION);
    }

    @org.testng.annotations.Test
    public void testWithNullBddType() {
        Test test = TestService.createTest(null, "Test", DESCRIPTION);
        Assert.assertEquals(test.getBddType(), null);
    }

    @org.testng.annotations.Test
    public void testWithBddType() {
        Test test = TestService.createTest(Scenario.class, "Test", DESCRIPTION);
        Assert.assertEquals(test.getBddType(), Scenario.class);
    }

    @org.testng.annotations.Test
    public void testWithBddTypeGherkinKeyword() throws ClassNotFoundException, UnsupportedEncodingException {
        GherkinDialectManager.setLanguage("en");
        GherkinKeyword keyword = new GherkinKeyword("Scenario");
        Test test = TestService.createTest(keyword.getClazz(), "Test", DESCRIPTION);
        Assert.assertEquals(test.getBddType(), keyword.getClazz());
    }

    @org.testng.annotations.Test
    public void deleteTest() {
        Test test1 = TestService.createTest("Test1", "");
        Test test2 = TestService.createTest("Test2", "");
        List<Test> list = new ArrayList<>();
        list.add(test1);
        list.add(test2);
        Assert.assertEquals(list.size(), 2);
        TestService.deleteTest(list, test1);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getName(), "Test2");
    }

    @org.testng.annotations.Test
    public void deleteNode() {
        Test test1 = TestService.createTest("Test1", "");
        Test test2 = TestService.createTest("Test2", "");
        Test child = TestService.createTest("Child", "");
        test1.addChild(child);
        List<Test> list = new ArrayList<>();
        list.add(test1);
        list.add(test2);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).getChildren().size(), 1);
        TestService.deleteTest(list, child);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).getChildren().size(), 0);
    }

    @org.testng.annotations.Test
    public void testHasScreenCaptureDeepLog() {
        Test test = getTest();
        Log log = Log.builder().status(Status.PASS).details("").build();
        log.addMedia(ScreenCapture.builder().path("/img.png").build());
        test.addLog(log);
        Assert.assertFalse(test.hasScreenCapture());
        Assert.assertTrue(TestService.testHasScreenCapture(test, true));
    }

    @org.testng.annotations.Test
    public void testHasScreenCaptureDeepNode() {
        Test test = getTest();
        Test node = getTest();
        test.getChildren().add(node);
        node.addMedia(ScreenCapture.builder().path("/img.png").build());
        Assert.assertFalse(test.hasScreenCapture());
        Assert.assertTrue(TestService.testHasScreenCapture(test, true));
    }

    @org.testng.annotations.Test
    public void testHasScreenCaptureDeepNodeLog() {
        Test test = getTest();
        Test node = getTest();
        test.getChildren().add(node);
        Log log = Log.builder().status(Status.PASS).details("").build();
        log.addMedia(ScreenCapture.builder().path("/img.png").build());
        node.addLog(log);
        Assert.assertFalse(test.hasScreenCapture());
        Assert.assertTrue(TestService.testHasScreenCapture(test, true));
    }
}
