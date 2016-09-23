package com.relevantcodes.extentreports.api;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class TestMediaTests extends Base {

    private final String filePath = getOutputFolder() + getClass().getName() + ".html";
    private final String imgName = "img";
    
    private ExtentReports extent;
    private String imgPath;
    
    @BeforeClass
    public void setup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        URL url = getClass().getClassLoader().getResource("1.png");
        imgPath = url.getPath();
    }
    
    @AfterClass
    public void tearDown() {
        extent.flush();
    }
    
    @Test
    public void verifyIfScreenCaptureAdded(Method method) throws IOException {
        ExtentTest test = extent
                .createTest(method.getName())
                .addScreenCaptureFromPath(imgPath)
                .fail("fail");
        
        Assert.assertEquals(test.getModel().getScreenCaptureList().size(), 1);
    }
    
    @Test
    public void verifyScreenCaptureTitle(Method method) throws IOException {
        ExtentTest test = extent
                .createTest(method.getName())
                .addScreenCaptureFromPath(imgPath, imgName)
                .fail("fail");
        
        Assert.assertEquals(test.getModel().getScreenCaptureList().size(), 1);
        Assert.assertEquals(test.getModel().getScreenCaptureList().get(0).getName(), imgName);
    }
    
    @Test
    public void verifyMultipleScreenCaptures(Method method) throws IOException {
        int times = 4;
        
        ExtentTest test = extent
                .createTest(method.getName())
                .fail("fail");

        for (int ix = 0; ix < times; ix++) {
            test.addScreenCaptureFromPath(imgPath, imgName);
        }
        
        Assert.assertEquals(test.getModel().getScreenCaptureList().size(), times);
        for (int ix = 0; ix < times; ix++) {
            Assert.assertEquals(test.getModel().getScreenCaptureList().get(ix).getName(), imgName);
        }
    }
    
}
