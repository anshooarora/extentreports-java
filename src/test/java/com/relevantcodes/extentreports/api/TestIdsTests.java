package com.relevantcodes.extentreports.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class TestIdsTests extends Base {

    private final String filePath = getOutputFolder() + getClass().getName() + ".html";
    private final int times = 100;
    
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
    public void verifyAllStartedTestsHaveUniqueIds(Method method) {
        List<Integer> idCollection = new ArrayList<>();
        
        // create [times] tests to ensure test-id is not duplicate 
        for (int ix = 0; ix < times; ix++) {
            int testId = extent.createTest(method.getName() + "." + ix).info("test # " + ix).getModel().getID();

            Assert.assertFalse(idCollection.contains(testId));
            
            idCollection.add(testId);
        }
    }
}
