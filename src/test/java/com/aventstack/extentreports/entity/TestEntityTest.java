package com.aventstack.extentreports.entity;

import java.lang.reflect.Method;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Device;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.service.TestService;

public class TestEntityTest {
    private static final String DESCRIPTION = "test.description";

    private Test getTest() {
        return TestService.createTest("Test");
    }

    @org.testng.annotations.Test
    public void logSeqIncrements(Method method) {
        Test test = TestService.createTest(method.getName());
        Log log = Log.builder().build();
        test.addLog(log);
        Assert.assertEquals(log.getSeq().intValue(), 0);
        test.addLog(log);
        Assert.assertEquals(log.getSeq().intValue(), 1);
        test.addLog(log);
        Assert.assertEquals(log.getSeq().intValue(), 2);
    }

    @org.testng.annotations.Test
    public void testEntities(Method method) {
        Test test = TestService.createTest(method.getName());
        Assert.assertEquals(test.getAuthorSet().size(), 0);
        Assert.assertEquals(test.getDeviceSet().size(), 0);
        Assert.assertEquals(test.getCategorySet().size(), 0);
        Assert.assertEquals(test.getChildren().size(), 0);
        Assert.assertTrue(test.isLeaf());
        Assert.assertEquals(test.getLevel().intValue(), 0);
        Assert.assertEquals(test.getStatus(), Status.PASS);
        Assert.assertNull(test.getDescription());
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void addChildToNullTest() {
        Test test = TestService.createTest("Test", DESCRIPTION);
        test.addChild(null);
    }

    @org.testng.annotations.Test
    public void addChildToTest() {
        Test test = getTest();
        Test node = TestService.createTest("Node", "");
        test.addChild(node);
        Assert.assertEquals(test.getChildren().size(), 1);
        test.addChild(node);
        Assert.assertEquals(test.getChildren().size(), 2);
    }

    @org.testng.annotations.Test
    public void addChildToTestLevel() {
        Test test = getTest();
        Test node = TestService.createTest("Node", "");
        test.addChild(node);
        Assert.assertEquals(test.getLevel().intValue(), 0);
        Assert.assertEquals(node.getLevel().intValue(), 1);
    }

    @org.testng.annotations.Test
    public void addChildToTestLeaf() {
        Test test = getTest();
        Test node = TestService.createTest("Node", "");
        test.addChild(node);
        Assert.assertFalse(test.isLeaf());
        Assert.assertTrue(node.isLeaf());
    }

    @org.testng.annotations.Test(expectedExceptions = IllegalArgumentException.class)
    public void addNullLogToTest() {
        Test test = getTest();
        test.addLog(null);
    }

    @org.testng.annotations.Test
    public void addLogToTest() {
        Test test = getTest();
        Log log = Log.builder().build();
        test.addLog(log);
        Assert.assertEquals(log.getSeq().intValue(), 0);
        Assert.assertEquals(test.getLogs().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.PASS);
        Assert.assertEquals(log.getStatus(), Status.PASS);
    }

    @org.testng.annotations.Test
    public void addSkipLogToTest() {
        Test test = getTest();
        Log log = Log.builder().status(Status.SKIP).build();
        test.addLog(log);
        Assert.assertEquals(test.getStatus(), Status.SKIP);
        Assert.assertEquals(log.getStatus(), Status.SKIP);
    }

    @org.testng.annotations.Test
    public void addFailLogToTest() {
        Test test = getTest();
        Log log = Log.builder().status(Status.FAIL).build();
        test.addLog(log);
        Assert.assertEquals(test.getStatus(), Status.FAIL);
        Assert.assertEquals(log.getStatus(), Status.FAIL);
    }

    @org.testng.annotations.Test
    public void testHasLog() {
        Test test = getTest();
        Assert.assertFalse(test.hasLog());
        Log log = Log.builder().status(Status.FAIL).build();
        test.addLog(log);
        Assert.assertTrue(test.hasLog());
    }

    @org.testng.annotations.Test
    public void isTestBDD() {
        Test test = getTest();
        Assert.assertFalse(test.isBDD());
        test.setBddType(Scenario.class);
        Assert.assertTrue(test.isBDD());
    }

    @org.testng.annotations.Test
    public void testHasChildren() {
        Test test = getTest();
        Assert.assertFalse(test.hasChildren());
        test.addChild(TestService.createTest("Node", ""));
        Assert.assertTrue(test.hasChildren());
    }

    @org.testng.annotations.Test
    public void testStatusWithoutLog() {
        Test test = getTest();
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }

    @org.testng.annotations.Test
    public void testStatusWithLog() {
        Test test = getTest();
        Assert.assertEquals(test.getStatus(), Status.PASS);
        Log log = Log.builder().status(Status.FAIL).build();
        test.addLog(log);
        Assert.assertEquals(test.getStatus(), Status.FAIL);
    }

    @org.testng.annotations.Test
    public void testStatusWithLogStatusChanged() {
        Test test = getTest();
        Assert.assertEquals(test.getStatus(), Status.PASS);
        Log log = Log.builder().status(Status.SKIP).build();
        test.addLog(log);
        Assert.assertEquals(test.getStatus(), Status.SKIP);
        log.setStatus(Status.FAIL);
        test.updateResult();
        Assert.assertEquals(test.getStatus(), Status.FAIL);
    }

    @org.testng.annotations.Test
    public void hasAuthor() {
        Test test = getTest();
        Assert.assertFalse(test.hasAuthor());
        test.getAuthorSet().add(new Author("x"));
        Assert.assertTrue(test.hasAuthor());
    }

    @org.testng.annotations.Test
    public void hasCategory() {
        Test test = getTest();
        Assert.assertFalse(test.hasDevice());
        test.getCategorySet().add(new Category("x"));
        Assert.assertTrue(test.hasCategory());
    }

    @org.testng.annotations.Test
    public void hasDevice() {
        Test test = getTest();
        Assert.assertFalse(test.hasDevice());
        test.getDeviceSet().add(new Device("x"));
        Assert.assertTrue(test.hasDevice());
    }

    @org.testng.annotations.Test
    public void hasAttributes() {
        Test test = getTest();
        Assert.assertFalse(test.hasAttributes());
        test.getAuthorSet().add(new Author("x"));
        Assert.assertTrue(test.hasAttributes());
        test = TestService.createTest("Test", "");
        test.getDeviceSet().add(new Device("x"));
        Assert.assertTrue(test.hasAttributes());
        test = TestService.createTest("Test", "");
        test.getCategorySet().add(new Category("x"));
        Assert.assertTrue(test.hasAttributes());
    }

    @org.testng.annotations.Test
    public void testFullName() {
        String[] name = new String[]{"Test", "Child", "Grandchild"};
        Test test = TestService.createTest(name[0], "");
        Test child = TestService.createTest(name[1], "");
        test.addChild(child);
        Test grandchild = TestService.createTest(name[2], "");
        child.addChild(grandchild);
        Assert.assertEquals(test.getFullName(), name[0]);
        Assert.assertEquals(child.getFullName(), name[0] + "." + name[1]);
        Assert.assertEquals(grandchild.getFullName(),
                name[0] + "." + name[1] + "." + name[2]);
    }

    @org.testng.annotations.Test
    public void hasScreenCapture() {
        Test test = getTest();
        Assert.assertFalse(test.hasScreenCapture());
        test.addMedia(ScreenCapture.builder().build());
        Assert.assertFalse(test.hasScreenCapture());
        test.addMedia(ScreenCapture.builder().path("/img.png").build());
        Assert.assertTrue(test.hasScreenCapture());
    }

    @org.testng.annotations.Test
    public void computeTestStatusNoLog() {
        Test test = getTest();
        test.updateResult();
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }

    @org.testng.annotations.Test
    public void computeTestStatusSkipLog() {
        Test test = getTest();
        test.getLogs().add(Log.builder().status(Status.SKIP).build());
        test.updateResult();
        Assert.assertEquals(test.getStatus(), Status.SKIP);
    }

    @org.testng.annotations.Test
    public void computeTestStatusSkipAndFailLog() {
        Test test = getTest();
        test.getLogs().add(Log.builder().status(Status.SKIP).build());
        test.getLogs().add(Log.builder().status(Status.FAIL).build());
        test.updateResult();
        Assert.assertEquals(test.getStatus(), Status.FAIL);
    }

    @org.testng.annotations.Test
    public void computeTestStatusNode() {
        Test parent = getTest();
        Test node = getTest();
        parent.addChild(node);
        node.getLogs().add(Log.builder().status(Status.SKIP).build());
        parent.updateResult();
        Assert.assertEquals(parent.getStatus(), Status.SKIP);
        Assert.assertEquals(node.getStatus(), Status.SKIP);
        node.getLogs().add(Log.builder().status(Status.FAIL).build());
        parent.updateResult();
        Assert.assertEquals(parent.getStatus(), Status.FAIL);
        Assert.assertEquals(node.getStatus(), Status.FAIL);
    }

    @org.testng.annotations.Test
    public void ancestor() {
        Test parent = TestService.createTest("Ancestor");
        Test node = TestService.createTest("Node");
        Test child = TestService.createTest("Child");
        parent.addChild(node);
        node.addChild(child);
        Assert.assertEquals(parent.getAncestor(), parent);
        Assert.assertEquals(node.getAncestor(), parent);
        Assert.assertEquals(child.getAncestor(), parent);
    }

    @org.testng.annotations.Test
    public void generatedLog() {
        Test test = getTest();
        Log log = Log.builder().status(Status.SKIP).details("details").build();
        test.addGeneratedLog(log);
        Assert.assertEquals(test.getGeneratedLog().size(), 1);
        Assert.assertEquals(test.getLogs().size(), 0);
        Assert.assertEquals(test.getStatus(), Status.SKIP);
    }

    @org.testng.annotations.Test
    public void testHasAngLogWithNoLogs() {
        Test test = getTest();
        Assert.assertFalse(test.hasAnyLog());
    }

    @org.testng.annotations.Test
    public void testHasAngLogWithLog() {
        Test test = getTest();
        Log log = Log.builder().status(Status.SKIP).details("details").build();
        test.addLog(log);
        Assert.assertTrue(test.hasAnyLog());
    }

    @org.testng.annotations.Test
    public void testHasAngLogWithGeneratedLog() {
        Test test = getTest();
        Log log = Log.builder().status(Status.SKIP).details("details").build();
        test.addGeneratedLog(log);
        Assert.assertTrue(test.hasAnyLog());
    }

    @org.testng.annotations.Test
    public void timeTaken() {
        Test test = getTest();
        long duration = test.timeTaken();
        Assert.assertTrue(duration < 5);
    }
}
