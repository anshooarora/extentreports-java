package com.aventstack.extentreports.api;

import java.io.UnsupportedEncodingException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;

public class GherkinKeywordTests extends Base {

    @Test
    public void testEnglishGherkinKeywords() throws ClassNotFoundException, UnsupportedEncodingException {
        extent.setGherkinDialect("en");
        
        ExtentTest feature = extent.createTest(new GherkinKeyword("Feature"), "Refund item VM");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Jeff returns a faulty microwave");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Given"), "Jeff has bought a microwave for $100").skip("skip");
        ExtentTest and = scenario.createNode(new GherkinKeyword("And"), "he has a receipt").pass("pass");
        ExtentTest when = scenario.createNode(new GherkinKeyword("When"), "he returns the microwave").pass("pass");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Then"), "Jeff should be refunded $100").error("error");
        
        Assert.assertEquals(feature.getModel().getBehaviorDrivenType(), Feature.class);
        Assert.assertEquals(scenario.getModel().getBehaviorDrivenType(), Scenario.class);
        Assert.assertEquals(given.getModel().getBehaviorDrivenType(), Given.class);
        Assert.assertEquals(and.getModel().getBehaviorDrivenType(), And.class);
        Assert.assertEquals(when.getModel().getBehaviorDrivenType(), When.class);
        Assert.assertEquals(then.getModel().getBehaviorDrivenType(), Then.class);
    }
    
    @Test
    public void testGermanGherkinKeywords() throws ClassNotFoundException, UnsupportedEncodingException {
        extent.setGherkinDialect("de");
        
        ExtentTest feature = extent.createTest(new GherkinKeyword("Funktionalität"), "Refund item VM");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Szenario"), "Jeff returns a faulty microwave");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Angenommen"), "Jeff has bought a microwave for $100").skip("skip");
        ExtentTest and = scenario.createNode(new GherkinKeyword("Und"), "he has a receipt").pass("pass");
        ExtentTest when = scenario.createNode(new GherkinKeyword("Wenn"), "he returns the microwave").pass("pass");
        ExtentTest then = scenario.createNode(new GherkinKeyword("Dann"), "Jeff should be refunded $100").error("error");
        
        Assert.assertEquals(feature.getModel().getBehaviorDrivenType(), Feature.class);
        Assert.assertEquals(scenario.getModel().getBehaviorDrivenType(), Scenario.class);
        Assert.assertEquals(given.getModel().getBehaviorDrivenType(), Given.class);
        Assert.assertEquals(and.getModel().getBehaviorDrivenType(), And.class);
        Assert.assertEquals(when.getModel().getBehaviorDrivenType(), When.class);
        Assert.assertEquals(then.getModel().getBehaviorDrivenType(), Then.class);
    }
    
    @Test
    public void testMixedGherkinKeywords() throws UnsupportedEncodingException, ClassNotFoundException {
        ExtentTest and = null, when = null, then = null;
        
        // set German dialect
        extent.setGherkinDialect("de");
        
        // the below tests should pass since GherkinKeywords are valid German words
        ExtentTest feature = extent.createTest(new GherkinKeyword("Funktionalität"), "Refund item VM");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Szenario"), "Jeff returns a faulty microwave");
        ExtentTest given = scenario.createNode(new GherkinKeyword("Angenommen"), "Jeff has bought a microwave for $100").skip("skip");
        
        Assert.assertEquals(feature.getModel().getBehaviorDrivenType(), Feature.class);
        Assert.assertEquals(scenario.getModel().getBehaviorDrivenType(), Scenario.class);
        Assert.assertEquals(given.getModel().getBehaviorDrivenType(), Given.class);
        
        // all below tests should fail since all GherkinKeywords are not valid German words
        // each ExtentTest object should equal NULL
        try {
            and = scenario.createNode(new GherkinKeyword("And"), "he has a receipt").pass("pass");
        } catch (ClassNotFoundException e) { }
        
        try {
            when = scenario.createNode(new GherkinKeyword("When"), "he returns the microwave").pass("pass");
        } catch (ClassNotFoundException e) { }
        
        try {
            then = scenario.createNode(new GherkinKeyword("Then"), "Jeff should be refunded $100").error("error");
        } catch (ClassNotFoundException e) { }
        
        Assert.assertEquals(and, null);
        Assert.assertEquals(when, null);
        Assert.assertEquals(then, null);
    }
}
