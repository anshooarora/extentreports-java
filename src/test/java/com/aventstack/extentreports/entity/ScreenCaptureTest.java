package com.aventstack.extentreports.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.ScreenCapture;

public class ScreenCaptureTest {

    @Test
    public void initWithAllEntitiesNull() {
        ScreenCapture capture = ScreenCapture.builder().build();
        Assert.assertNull(capture.getBase64());
        Assert.assertNull(capture.getPath());
        Assert.assertNull(capture.getResolvedPath());
        Assert.assertNull(capture.getTitle());
    }
}
