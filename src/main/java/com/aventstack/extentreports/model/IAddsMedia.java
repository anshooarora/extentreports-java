package com.aventstack.extentreports.model;

import java.io.IOException;

public interface IAddsMedia<T> {
	/**
	 * Adds a snapshot to the test or log with title
	 * 
	 * @param imagePath Image path
	 * @param title Image title
	 * 
	 * @return Object this method is called from, generally {@link com.aventstack.extentreports.ExtentTest} or {@link Log}
	 * 
	 * @throws IOException thrown if the <code>imagePath</code> of image is not found
	 */
    T addScreenCaptureFromPath(String imagePath, String title) throws IOException;
    
    /**
     * Adds a snapshot to test or log
     * 
     * @param imagePath Image path
     * 
     * @return Object this method is called from, generally {@link com.aventstack.extentreports.ExtentTest} or {@link Log}
     * 
     * @throws IOException thrown if the <code>imagePath</code> of image is not found
     */
    T addScreenCaptureFromPath(String imagePath) throws IOException;

    /**
     * Adds a screencast to test or log
     * 
     * @param screencastPath Screencast path
     * 
     * @return Object this method is called from, generally {@link com.aventstack.extentreports.ExtentTest} or {@link Log}
     * 
     * @throws IOException thrown if the <code>screencastPath</code> of image is not found
     */
    T addScreencastFromPath(String screencastPath) throws IOException;
}
