package com.relevantcodes.extentreports.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class NodeAttributesTest extends Base {
    
    private final String filePath = getOutputFolder() + getClass().getName() + ".html";
    private final String[] categories = {
            "extent",
            "git",
            "tests",
            "heroku"
    };
    
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
