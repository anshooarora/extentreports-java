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
import com.relevantcodes.extentreports.gherkin.model.And;
import com.relevantcodes.extentreports.gherkin.model.Given;
import com.relevantcodes.extentreports.gherkin.model.Scenario;
import com.relevantcodes.extentreports.gherkin.model.Then;
import com.relevantcodes.extentreports.gherkin.model.When;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class BddLevelsTests extends Base {
    
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
    public void verifyLevelsUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").info("info");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").info("info");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").info("info");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").pass("pass");
        
        Assert.assertEquals(feature.getModel().getLevel(), 0);
        Assert.assertEquals(scenario.getModel().getLevel(), 1);
        Assert.assertEquals(given.getModel().getLevel(), 2);
        Assert.assertEquals(and.getModel().getLevel(), 2);
        Assert.assertEquals(when.getModel().getLevel(), 2);
        Assert.assertEquals(then.getModel().getLevel(), 2);
    }

    @Test
    public void verifyLevelsUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Child");
        ExtentTest given = scenario.createNode(Given.class, "Given").info("info");
        ExtentTest and = scenario.createNode(And.class, "And").info("info");
        ExtentTest when = scenario.createNode(When.class, "When").info("info");
        ExtentTest then = scenario.createNode(Then.class, "Then").pass("pass");
        
        Assert.assertEquals(feature.getModel().getLevel(), 0);
        Assert.assertEquals(scenario.getModel().getLevel(), 1);
        Assert.assertEquals(given.getModel().getLevel(), 2);
        Assert.assertEquals(and.getModel().getLevel(), 2);
        Assert.assertEquals(when.getModel().getLevel(), 2);
        Assert.assertEquals(then.getModel().getLevel(), 2);
    }
}
