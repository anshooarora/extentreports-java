package com.aventstack.extentreports.model;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;

public interface IAddsMedia {
    /**
     * Adds a snapshot to the test or log with title
     * 
     * @param imagePath Image path
     * @param title Image title
     * 
     * @return Object this method is called from, generally {@link ExtentTest} or {@link Log}
     * 
     * @throws IOException thrown if the supplied image from <code>imagePath</code> is not found
     */
    ExtentTest addScreenCaptureFromPath(String imagePath, String title) throws IOException;
    
    /**
     * Adds a snapshot to test or log
     * 
     * @param imagePath Image path
     * 
     * @return Object this method is called from, generally {@link ExtentTest} or {@link Log}
     * 
     * @throws IOException thrown if the supplied image from <code>imagePath</code> is not found
     */
    ExtentTest addScreenCaptureFromPath(String imagePath) throws IOException;
}
