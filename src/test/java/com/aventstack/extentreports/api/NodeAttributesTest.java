package com.aventstack.extentreports.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.TestAttribute;

public class NodeAttributesTest extends Base {

    private final String[] categories = {
            "extent",
            "git",
            "tests",
            "heroku"
    };
    
    @Test
    public void verifyIfNodeHasAddedCategory(Method method) {
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .assignCategory(categories[0])
                .pass("pass");
        
        Assert.assertEquals(node.getModel().getCategoryList().size(), 1);
        Category c = (Category) node.getModel().getCategoryList().get(0);
        Assert.assertEquals(c.getName(), categories[0]);
    }
    
    @Test
    public void verifyIfTestHasAddedCategories(Method method) {
        ExtentTest node = extent.createTest(method.getName()).createNode("Child").pass("pass");        
        Arrays.stream(categories).forEach(c -> node.assignCategory(c));
               
        Assert.assertEquals(node.getModel().getCategoryList().size(), categories.length);
        
        List<TestAttribute> categoryCollection = node.getModel().getCategoryList();
        Arrays.stream(categories).forEach(c -> {
            Boolean result = categoryCollection.stream().anyMatch(x -> x.getName() == c); 
            Assert.assertTrue(result);
        });
    }
}
