package com.relevantcodes.extentreports.model;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTestContext {
    
    ExceptionInfo ei;
    List<Test> testList;
    
    public ExceptionTestContext(ExceptionInfo ei) { this.ei = ei; }
    
    public void setTest(Test test) {
        if (testList == null)
            testList = new ArrayList<>();

        testList.add(test);
    }
    
    public List<Test> getTestList() { return testList; }
    
    public ExceptionInfo getExceptionInfo() { return ei; }
    
}
