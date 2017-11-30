package com.aventstack.extentreports.api;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;

public class NodeMediaTests extends Base {

    private final String imgName = "img";
    private URL url = getClass().getClassLoader().getResource("1.png");
    private String imgPath = url.getPath();
    
    @Test
    public void verifyIfScreenCaptureAdded(Method method) throws IOException {
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .addScreenCaptureFromPath(imgPath)
                .fail("fail");
        
        Assert.assertEquals(node.getModel().getScreenCaptureList().size(), 1);
    }
    
    @Test
    public void verifyScreenCaptureTitle(Method method) throws IOException {
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .addScreenCaptureFromPath(imgPath, imgName)
                .fail("fail");
        
        Assert.assertEquals(node.getModel().getScreenCaptureList().size(), 1);
        Assert.assertEquals(node.getModel().getScreenCaptureList().get(0).getName(), imgName);
    }
    
    @Test
    public void verifyMultipleScreenCaptures(Method method) throws IOException {
        int times = 4;
        
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .fail("fail");

        for (int ix = 0; ix < times; ix++) {
            node.addScreenCaptureFromPath(imgPath, imgName);
        }
        
        Assert.assertEquals(node.getModel().getScreenCaptureList().size(), times);
        for (int ix = 0; ix < times; ix++) {
            Assert.assertEquals(node.getModel().getScreenCaptureList().get(ix).getName(), imgName);
        }
    }
    
}
