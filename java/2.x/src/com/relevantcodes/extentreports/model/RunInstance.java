/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

public class RunInstance {
    public RunInfo runInfo;
    public ArrayList<Test> tests;
    public SystemProperties systemInfo;
    public ArrayList<ScreenCapture> screenCapture;
    
    public void init() {
        tests = new ArrayList<Test>();
        screenCapture = new ArrayList<ScreenCapture>();
    }
    
    private RunInstance() { }
    
    private static class Instance {
        static final RunInstance INSTANCE = new RunInstance();
    }
    
    public static RunInstance getInstance() {
        return Instance.INSTANCE;
    }
}
