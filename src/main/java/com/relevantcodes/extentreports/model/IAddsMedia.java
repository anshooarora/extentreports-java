package com.relevantcodes.extentreports.model;

import com.relevantcodes.extentreports.ExtentTest;

public interface IAddsMedia {
    ExtentTest addScreenCaptureFromPath(String imagePath, String title);
    ExtentTest addScreenCaptureFromPath(String imagePath);
}
