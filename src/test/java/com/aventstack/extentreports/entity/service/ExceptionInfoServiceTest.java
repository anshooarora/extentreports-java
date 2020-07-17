package com.aventstack.extentreports.entity.service;

import org.testng.Assert;

import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.model.service.ExceptionInfoService;

public class ExceptionInfoServiceTest {
    @org.testng.annotations.Test
    public void exceptionInfo() {
        RuntimeException ex = new RuntimeException("ERROR");
        ExceptionInfo info = ExceptionInfoService.createExceptionInfo(ex);
        Assert.assertEquals(info.getException(), ex);
        Assert.assertEquals(info.getName(), "java.lang.RuntimeException");
    }
}
