package com.aventstack.extentreports.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.TestAttribute;

public class TestAttributesTests extends Base {
    
    private final String[] categories = {
            "extent",
            "git",
            "tests",
            "heroku"
    };

    @Test
    public void verifyIfTestHasAddedCategory(Method method) {
        ExtentTest test = extent.createTest(method.getName()).assignCategory(categories[0]);
        test.pass("pass");
        
        Assert.assertEquals(test.getModel().getCategoryList().size(), 1);
        Assert.assertEquals(test.getModel().getCategoryList().get(0).getName(), categories[0]);
    }
    
    @Test
    public void verifyIfTestHasAddedCategories(Method method) {
        ExtentTest test = extent.createTest(method.getName());        
        Arrays.stream(categories).forEach(c -> test.assignCategory(c));
        test.pass("pass");
               
        Assert.assertEquals(test.getModel().getCategoryList().size(), categories.length);
        
        List<TestAttribute> categoryCollection = test.getModel().getCategoryList();
        Arrays.stream(categories).forEach(c -> {
            Boolean result = categoryCollection.stream().anyMatch(x -> x.getName() == c); 
            Assert.assertTrue(result);
        });
    }
}
