package com.relevantcodes.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.GherkinKeyword;
import com.relevantcodes.extentreports.gherkin.model.Given;
import com.relevantcodes.extentreports.gherkin.model.Scenario;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class TestInitializeNullValuesTests extends Base {

    private final String filePath = getOutputFolder() + getClass().getName() + ".html";
    
    private ExtentReports extent;

    @BeforeClass
    public void setup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    
    @AfterClass
    public void tearDown() {
        extent.flush();
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNameNull() {
        extent.createTest(null).pass("pass");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestScenarioNameNull(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(Scenario.class, null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestScenarioNameEmpty(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(Scenario.class, "");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestStepNameNull(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        scenario.createNode(Given.class, null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestStepNameEmpty(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        scenario.createNode(Given.class, "");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestScenarioNameNullGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(new GherkinKeyword("Scenario"), null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestScenarioNameEmptyGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(new GherkinKeyword("Scenario"), "");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestStepNameNullGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        scenario.createNode(new GherkinKeyword("Given"), null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void bddTestStepNameEmptyGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        scenario.createNode(new GherkinKeyword("Given"), "");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNameEmpty() {
        extent.createTest("").pass("pass");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nodeNameNull(Method method) {
        ExtentTest test = extent.createTest(method.getName()).fail("fail");
        ExtentTest node = test.createNode(null);
        
        Assert.assertEquals(test.getModel().getNodeContext().getAll().size(), 0);
        Assert.assertNull(node);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nodeNameEmpty(Method method) {
        ExtentTest test = extent.createTest(method.getName()).fail("fail");
        ExtentTest node = test.createNode("");
        
        Assert.assertEquals(test.getModel().getNodeContext().getAll().size(), 0);
        Assert.assertNull(node);
    }
    
    @Test
    public void testDescriptionNull(Method method) {
        ExtentTest test = extent.createTest(method.getName(), null).pass("pass");
        
        Assert.assertTrue(test.getModel().getDescription().isEmpty());
    }
    
    @Test
    public void nodeDescriptionNull(Method method) {
        ExtentTest node = extent.createTest(method.getName()).createNode("Child", null).pass("pass");
        
        Assert.assertTrue(node.getModel().getDescription().isEmpty());
    }

}
