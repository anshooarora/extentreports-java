package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BddAttributesTest extends Base {
    
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
    
    @Test
    public void initialTestIsOfBddType(Method method) {
        ExtentTest feature = extent.createTest(Feature.class, method.getName());
        
        Assert.assertTrue(feature.getModel().isBehaviorDrivenType());
        Assert.assertEquals(feature.getModel().getBehaviorDrivenType(), Feature.class);
    }
    
    @Test
    public void testIsOfBddTypeWithBddChild(Method method) {
        ExtentTest feature = extent.createTest(Feature.class, method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        
        Assert.assertTrue(feature.getModel().isBehaviorDrivenType());
        Assert.assertEquals(feature.getModel().getBehaviorDrivenType(), Feature.class);
        
        Assert.assertTrue(scenario.getModel().isBehaviorDrivenType());
        Assert.assertEquals(scenario.getModel().getBehaviorDrivenType(), Scenario.class);
    }
    
}
