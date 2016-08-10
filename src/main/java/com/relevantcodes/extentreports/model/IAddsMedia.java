package com.relevantcodes.extentreports.model;

import java.io.IOException;

import com.relevantcodes.extentreports.ExtentTest;

public interface IAddsMedia {
    ExtentTest addScreenCaptureFromPath(String imagePath, String title) throws IOException;
    ExtentTest addScreenCaptureFromPath(String imagePath) throws IOException;
}
