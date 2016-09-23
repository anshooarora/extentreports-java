package com.relevantcodes.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExceptionTestContext implements Serializable {

    private static final long serialVersionUID = -2516200535748363722L;

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
