package com.aventstack.extentreports.api;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.Status;

public class StatusHierarchyConfiguratorTests extends Base {

    @Test
    public void skipHasHigherPriorityThanPass() {
        int skipIndex = Status.getStatusHierarchy().indexOf(Status.SKIP);
        int passIndex = Status.getStatusHierarchy().indexOf(Status.PASS);
        
        Assert.assertTrue(skipIndex < passIndex);
    }
    
    @Test
    public void withConfigurationSkipHasLowerPriorityThanPass() {
        List<Status> statusHierarchy = Arrays.asList(
                Status.FATAL,
                Status.FAIL,
                Status.ERROR,
                Status.WARNING,
                Status.PASS,
                Status.SKIP,
                Status.INFO
        );
        
        extent.config().statusConfigurator().setStatusHierarchy(statusHierarchy);
        
        int skipIndex = Status.getStatusHierarchy().indexOf(Status.SKIP);
        int passIndex = Status.getStatusHierarchy().indexOf(Status.PASS);
        
        Assert.assertTrue(skipIndex > passIndex);
    }
    
    @Test
    public void fatalHasHigherPriorityThanFAIL() {
        int fatalIndex = Status.getStatusHierarchy().indexOf(Status.FATAL);
        int failIndex = Status.getStatusHierarchy().indexOf(Status.FAIL);
        
        Assert.assertTrue(fatalIndex < failIndex);
    }
    
    @Test
    public void withConfigurationFatalHasLowerPriorityThanFail() {
        List<Status> statusHierarchy = Arrays.asList(
                Status.FAIL,
                Status.FATAL,
                Status.ERROR,
                Status.WARNING,
                Status.PASS,
                Status.SKIP,
                Status.INFO
        );
        
        extent.config().statusConfigurator().setStatusHierarchy(statusHierarchy);
        
        int fatalIndex = Status.getStatusHierarchy().indexOf(Status.FATAL);
        int failIndex = Status.getStatusHierarchy().indexOf(Status.FAIL);
        
        Assert.assertTrue(fatalIndex > failIndex);
    }
    
    @AfterClass
    public void afterThisClass() {
        extent.config().statusConfigurator().resetStatusHierarchy();
    }
    
}
