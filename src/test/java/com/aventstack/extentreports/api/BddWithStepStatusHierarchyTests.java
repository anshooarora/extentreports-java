package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import com.aventstack.extentreports.gherkin.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.Status;

public class BddWithStepStatusHierarchyTests extends Base {
    
    @Test(expectedExceptions = ClassNotFoundException.class)
    public void throwClassNotFoundExceptionWithInvalidKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(new GherkinKeyword("Invalid"), "Child");
        
        Assert.assertEquals(feature.getStatus(), Status.PASS);
    }
    
    @Test(expectedExceptions = ClassNotFoundException.class)
    public void throwClassNotFoundExceptionWithEmptyKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(new GherkinKeyword(""), "Child");
        
        Assert.assertEquals(feature.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyValidKeywordFoundWithInvalidCaseFirstCharacter(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(new GherkinKeyword("given"), "Child").pass("pass");
        
        Assert.assertEquals(feature.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyValidKeywordFounWithInvalidCase(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        feature.createNode(new GherkinKeyword("giVen"), "Child").pass("pass");
        
        Assert.assertEquals(feature.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyPassHasHigherPriorityThanInfoUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").info("info");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").info("info");
        ExtentTest but = scenario.createNode(new GherkinKeyword("But"), "But").info("info");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").info("info");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").pass("pass");
        
        Assert.assertEquals(feature.getModel().getLevel(), 0);
        Assert.assertEquals(scenario.getModel().getLevel(), 1);
        Assert.assertEquals(given.getModel().getLevel(), 2);
        Assert.assertEquals(and.getModel().getLevel(), 2);
        Assert.assertEquals(but.getModel().getLevel(), 2);
        Assert.assertEquals(when.getModel().getLevel(), 2);
        Assert.assertEquals(then.getModel().getLevel(), 2);
        Assert.assertEquals(given.getStatus(), Status.PASS);
        Assert.assertEquals(and.getStatus(), Status.PASS);
        Assert.assertEquals(but.getStatus(), Status.PASS);
        Assert.assertEquals(when.getStatus(), Status.PASS);
        Assert.assertEquals(then.getStatus(), Status.PASS);
        Assert.assertEquals(scenario.getStatus(), Status.PASS);
        Assert.assertEquals(feature.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyPassHasHigherPriorityThanInfoUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given").info("info");
        ExtentTest and = scenario.createNode(And.class, "And").info("info");
        ExtentTest when = scenario.createNode(When.class, "When").info("info");
        ExtentTest then = scenario.createNode(Then.class, "Then").pass("pass");
        ExtentTest but = scenario.createNode(But.class, "But").pass("pass");

        Assert.assertEquals(given.getStatus(), Status.PASS);
        Assert.assertEquals(and.getStatus(), Status.PASS);
        Assert.assertEquals(when.getStatus(), Status.PASS);
        Assert.assertEquals(then.getStatus(), Status.PASS);
        Assert.assertEquals(but.getStatus(), Status.PASS);
        Assert.assertEquals(scenario.getStatus(), Status.PASS);
        Assert.assertEquals(feature.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifySkipHasHigherPriorityThanPassUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").pass("pass");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").pass("pass");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").pass("pass");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").skip("skip");
        ExtentTest but = scenario.createNode(new GherkinKeyword("But"), "But").skip("skip");

        Assert.assertEquals(given.getStatus(), Status.PASS);
        Assert.assertEquals(and.getStatus(), Status.PASS);
        Assert.assertEquals(when.getStatus(), Status.PASS);
        Assert.assertEquals(then.getStatus(), Status.SKIP);
        Assert.assertEquals(but.getStatus(), Status.SKIP);
        Assert.assertEquals(scenario.getStatus(), Status.SKIP);
        Assert.assertEquals(feature.getStatus(), Status.SKIP);
    }
    
    @Test
    public void verifySkipHasHigherPriorityThanPassUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given").pass("pass");
        ExtentTest and = scenario.createNode(And.class, "And").pass("pass");
        ExtentTest when = scenario.createNode(When.class, "When").pass("pass");
        ExtentTest then = scenario.createNode(Then.class, "Then").skip("skip");
        ExtentTest but = scenario.createNode(But.class, "But").skip("skip");

        Assert.assertEquals(given.getStatus(), Status.PASS);
        Assert.assertEquals(and.getStatus(), Status.PASS);
        Assert.assertEquals(when.getStatus(), Status.PASS);
        Assert.assertEquals(then.getStatus(), Status.SKIP);
        Assert.assertEquals(but.getStatus(), Status.SKIP);
        Assert.assertEquals(scenario.getStatus(), Status.SKIP);
        Assert.assertEquals(feature.getStatus(), Status.SKIP);
    }
    
    @Test
    public void verifyWarningHasHigherPriorityThanSkipUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").skip("skip");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").skip("skip");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").skip("skip");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").warning("warning");
        ExtentTest but = scenario.createNode(new GherkinKeyword("But"), "But").warning("warning");

        Assert.assertEquals(given.getStatus(), Status.SKIP);
        Assert.assertEquals(and.getStatus(), Status.SKIP);
        Assert.assertEquals(when.getStatus(), Status.SKIP);
        Assert.assertEquals(then.getStatus(), Status.WARNING);
        Assert.assertEquals(but.getStatus(), Status.WARNING);
        Assert.assertEquals(scenario.getStatus(), Status.WARNING);
        Assert.assertEquals(feature.getStatus(), Status.WARNING);
    }
    
    @Test
    public void verifyWarningHasHigherPriorityThanSkipUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given").skip("skip");
        ExtentTest and = scenario.createNode(And.class, "And").skip("skip");
        ExtentTest when = scenario.createNode(When.class, "When").skip("skip");
        ExtentTest then = scenario.createNode(Then.class, "Then").warning("warning");
        ExtentTest but = scenario.createNode(But.class, "But").warning("warning");

        Assert.assertEquals(given.getStatus(), Status.SKIP);
        Assert.assertEquals(and.getStatus(), Status.SKIP);
        Assert.assertEquals(when.getStatus(), Status.SKIP);
        Assert.assertEquals(then.getStatus(), Status.WARNING);
        Assert.assertEquals(but.getStatus(), Status.WARNING);
        Assert.assertEquals(scenario.getStatus(), Status.WARNING);
        Assert.assertEquals(feature.getStatus(), Status.WARNING);
    }
    
    @Test
    public void verifyErrorHasHigherPriorityThanWarningUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").warning("warning");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").warning("warning");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").warning("warning");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").error("error");
        ExtentTest but = scenario.createNode(new GherkinKeyword("But"), "But").error("error");

        Assert.assertEquals(given.getStatus(), Status.WARNING);
        Assert.assertEquals(and.getStatus(), Status.WARNING);
        Assert.assertEquals(when.getStatus(), Status.WARNING);
        Assert.assertEquals(then.getStatus(), Status.ERROR);
        Assert.assertEquals(but.getStatus(), Status.ERROR);
        Assert.assertEquals(scenario.getStatus(), Status.ERROR);
        Assert.assertEquals(feature.getStatus(), Status.ERROR);
    }
    
    @Test
    public void verifyErrorHasHigherPriorityThanWarningUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given").warning("warning");
        ExtentTest and = scenario.createNode(And.class, "And").warning("warning");
        ExtentTest when = scenario.createNode(When.class, "When").warning("warning");
        ExtentTest then = scenario.createNode(Then.class, "Then").error("error");
        ExtentTest but = scenario.createNode(But.class, "But").error("error");

        Assert.assertEquals(given.getStatus(), Status.WARNING);
        Assert.assertEquals(and.getStatus(), Status.WARNING);
        Assert.assertEquals(when.getStatus(), Status.WARNING);
        Assert.assertEquals(then.getStatus(), Status.ERROR);
        Assert.assertEquals(but.getStatus(), Status.ERROR);
        Assert.assertEquals(scenario.getStatus(), Status.ERROR);
        Assert.assertEquals(feature.getStatus(), Status.ERROR);
    }
    
    @Test
    public void verifyFailHasHigherPriorityThanErrorUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").error("error");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").error("error");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").error("error");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").fail("fail");
        ExtentTest but = scenario.createNode(new GherkinKeyword("But"), "But").fail("fail");

        Assert.assertEquals(given.getStatus(), Status.ERROR);
        Assert.assertEquals(and.getStatus(), Status.ERROR);
        Assert.assertEquals(when.getStatus(), Status.ERROR);
        Assert.assertEquals(then.getStatus(), Status.FAIL);
        Assert.assertEquals(but.getStatus(), Status.FAIL);
        Assert.assertEquals(scenario.getStatus(), Status.FAIL);
        Assert.assertEquals(feature.getStatus(), Status.FAIL);
    }
    
    @Test
    public void verifyFailHasHigherPriorityThanErrorUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given").error("error");
        ExtentTest and = scenario.createNode(And.class, "And").error("error");
        ExtentTest when = scenario.createNode(When.class, "When").error("error");
        ExtentTest then = scenario.createNode(Then.class, "Then").fail("fail");
        ExtentTest but = scenario.createNode(But.class, "But").fail("fail");

        Assert.assertEquals(given.getStatus(), Status.ERROR);
        Assert.assertEquals(and.getStatus(), Status.ERROR);
        Assert.assertEquals(when.getStatus(), Status.ERROR);
        Assert.assertEquals(then.getStatus(), Status.FAIL);
        Assert.assertEquals(but.getStatus(), Status.FAIL);
        Assert.assertEquals(scenario.getStatus(), Status.FAIL);
        Assert.assertEquals(feature.getStatus(), Status.FAIL);
    }
    
    @Test
    public void verifyFatalHasHigherPriorityThanFailUsingGherkinKeyword(Method method) throws ClassNotFoundException {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Child");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Given").fail("fail");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "And").fail("fail");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "When").fail("fail");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Then").fatal("fatal");
        ExtentTest but = scenario.createNode(new GherkinKeyword("But"), "But").fatal("fatal");

        Assert.assertEquals(given.getStatus(), Status.FAIL);
        Assert.assertEquals(and.getStatus(), Status.FAIL);
        Assert.assertEquals(when.getStatus(), Status.FAIL);
        Assert.assertEquals(then.getStatus(), Status.FATAL);
        Assert.assertEquals(but.getStatus(), Status.FATAL);
        Assert.assertEquals(scenario.getStatus(), Status.FATAL);
        Assert.assertEquals(feature.getStatus(), Status.FATAL);
    }
    
    @Test
    public void verifyFatalHasHigherPriorityThanFailUsingClass(Method method) {
        ExtentTest feature = extent.createTest(method.getName());
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        ExtentTest given = scenario.createNode(Given.class, "Given").fail("fail");
        ExtentTest and = scenario.createNode(And.class, "And").fail("fail");
        ExtentTest when = scenario.createNode(When.class, "When").fail("fail");
        ExtentTest then = scenario.createNode(Then.class, "Then").fatal("fatal");
        ExtentTest but = scenario.createNode(But.class, "But").fatal("fatal");

        Assert.assertEquals(given.getStatus(), Status.FAIL);
        Assert.assertEquals(and.getStatus(), Status.FAIL);
        Assert.assertEquals(when.getStatus(), Status.FAIL);
        Assert.assertEquals(then.getStatus(), Status.FATAL);
        Assert.assertEquals(but.getStatus(), Status.FATAL);
        Assert.assertEquals(scenario.getStatus(), Status.FATAL);
        Assert.assertEquals(feature.getStatus(), Status.FATAL);
    }
}
