package com.aventstack.extentreports.model.service;

import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.model.service.util.ExceptionUtil;

public class ExceptionInfoService {
    public static ExceptionInfo createExceptionInfo(Throwable t) {
        if (t == null)
            return null;
        ExceptionInfo exceptionInfo = ExceptionInfo.builder()
                .exception(t)
                .name(ExceptionUtil.getExceptionHeadline(t))
                .stackTrace(ExceptionUtil.getStackTrace(t))
                .build();
        return exceptionInfo;
    }
}
