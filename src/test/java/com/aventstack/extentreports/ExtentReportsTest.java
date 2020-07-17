package com.aventstack.extentreports;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.testng.Assert;

import com.aventstack.extentreports.gherkin.GherkinDialectManager;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.model.Test;

public class ExtentReportsTest {
    private static final String TEST_NAME = "TEST";

    private ExtentReports extent() {
        return new ExtentReports();
    }

    @org.testng.annotations.Test
    public void createTestOverloadTypeNameDesc() {
        ExtentTest test = extent().createTest(Feature.class, TEST_NAME, "Description");
        Test model = test.getModel();
        Assert.assertTrue(model.isBDD());
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertEquals(model.getBddType(), Feature.class);
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createTestOverloadTypeName() {
        ExtentTest test = extent().createTest(Feature.class, TEST_NAME);
        Test model = test.getModel();
        Assert.assertTrue(model.isBDD());
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertNull(model.getDescription());
        Assert.assertEquals(model.getBddType(), Feature.class);
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createTestOverloadKeywordNameDesc() throws ClassNotFoundException {
        ExtentTest test = extent().createTest(new GherkinKeyword("Feature"), TEST_NAME, "Description");
        Test model = test.getModel();
        Assert.assertTrue(model.isBDD());
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertEquals(model.getBddType(), Feature.class);
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createTestOverloadKeywordName() throws ClassNotFoundException {
        ExtentTest test = extent().createTest(new GherkinKeyword("Feature"), TEST_NAME);
        Test model = test.getModel();
        Assert.assertTrue(model.isBDD());
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertNull(model.getDescription());
        Assert.assertEquals(model.getBddType(), Feature.class);
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createTestOverloadNameDesc() {
        ExtentTest test = extent().createTest(TEST_NAME, "Description");
        Test model = test.getModel();
        Assert.assertFalse(model.isBDD());
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertNull(model.getBddType());
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createTestOverloadName() {
        ExtentTest test = extent().createTest(TEST_NAME);
        Test model = test.getModel();
        Assert.assertFalse(model.isBDD());
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertNull(model.getDescription());
        Assert.assertNull(model.getBddType());
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void removeTest() {
        ExtentReports extent = extent();
        ExtentTest test = extent.createTest(TEST_NAME);
        Assert.assertEquals(extent.getReport().getTestList().size(), 1);
        extent.removeTest(test);
        Assert.assertEquals(extent.getReport().getTestList().size(), 0);
    }

    @org.testng.annotations.Test
    public void removeNode() {
        ExtentReports extent = extent();
        ExtentTest test = extent.createTest(TEST_NAME);
        ExtentTest node = test.createNode(TEST_NAME);
        Assert.assertEquals(extent.getReport().getTestList().size(), 1);
        Assert.assertEquals(extent.getReport().getTestList().get(0).getChildren().size(), 1);
        extent.removeTest(node);
        Assert.assertEquals(extent.getReport().getTestList().size(), 1);
        Assert.assertEquals(extent.getReport().getTestList().get(0).getChildren().size(), 0);
    }

    @org.testng.annotations.Test
    public void gherkinDialect() throws UnsupportedEncodingException {
        ExtentReports extent = extent();
        extent.setGherkinDialect("de");
        Assert.assertEquals(GherkinDialectManager.getLanguage(), "de");
    }

    @org.testng.annotations.Test
    public void addTestRunnerOutputSingle() {
        String[] logs = new String[]{"Log1", "Log2"};
        ExtentReports extent = extent();
        Arrays.stream(logs).forEach(extent::addTestRunnerOutput);
        Assert.assertEquals(extent.getReport().getLogs().size(), 2);
        Arrays.stream(logs).forEach(x -> Assert.assertTrue(extent.getReport().getLogs().contains(x)));
    }

    @org.testng.annotations.Test
    public void addTestRunnerOutputList() {
        String[] logs = new String[]{"Log1", "Log2"};
        ExtentReports extent = extent();
        extent.addTestRunnerOutput(Arrays.asList(logs));
        Assert.assertEquals(extent.getReport().getLogs().size(), 2);
        Arrays.stream(logs).forEach(x -> Assert.assertTrue(extent.getReport().getLogs().contains(x)));
    }
}
