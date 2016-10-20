package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;

public class BddAttributesTest extends Base {
    
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
