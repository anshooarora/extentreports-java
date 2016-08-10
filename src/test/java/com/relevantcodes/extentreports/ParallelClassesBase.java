package com.relevantcodes.extentreports;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.common.ExtentManager;
import com.relevantcodes.extentreports.common.ExtentTestManager;

public abstract class ParallelClassesBase extends Base {
    
    final String fileName = getOutputFolder() + getClass().getPackage().getName() + ".ParallelClasses.html";
    
    @BeforeSuite
    public void setup() {
        ExtentManager.createInstance(fileName);
        ExtentTestManager.setReporter(ExtentManager.getInstance());
    }
    
    @AfterSuite
    public void tearDown() {
        ExtentManager.getInstance().flush();
    }
    
}
