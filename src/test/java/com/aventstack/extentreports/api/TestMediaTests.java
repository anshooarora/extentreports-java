package com.aventstack.extentreports.api;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;

public class TestMediaTests extends Base {

    private final String imgName = "img";
    private URL url = getClass().getClassLoader().getResource("1.png");
    private String imgPath = url.getPath();
    
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
