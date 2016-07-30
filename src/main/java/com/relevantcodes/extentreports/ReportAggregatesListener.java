package com.relevantcodes.extentreports;

import java.util.List;

import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Test;

public interface ReportAggregatesListener {
    void setTestList(List<Test> testList);
    void setTestRunnerLogs(List<String> logs);
    void setCategoryContextInfo(TestAttributeTestContextProvider<Category> categoryContext);
    void setExceptionContextInfo(ExceptionTestContextImpl exceptionContext);
    void setSystemAttributeContext(SystemAttributeContext systemAttributeContext);
    void setStatusCount(SessionStatusStats sc);
}
