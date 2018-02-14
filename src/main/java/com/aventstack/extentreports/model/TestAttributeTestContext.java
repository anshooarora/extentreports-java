package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.Status;

public class TestAttributeTestContext<T extends TestAttribute> implements Serializable {

    private static final long serialVersionUID = 2595632998970711190L;
    
    private T ta;
    private List<Test> testList;
    private int passed = 0;
    private int failed = 0;
    private int others = 0;
    
    public TestAttributeTestContext(T ta) { this.ta = ta; }
    
    public void setTest(Test test) {
        if (testList == null)
            testList = new ArrayList<>();
        
        updateTestStatusCounts(test);        
        testList.add(test);
    }
    
    private void updateTestStatusCounts(Test test) {
        passed += test.getStatus() == Status.PASS ? 1 : 0;
        failed += test.getStatus() == Status.FAIL || test.getStatus() == Status.FATAL ? 1 : 0;
        others += test.getStatus() != Status.PASS && test.getStatus() != Status.FAIL ? 1 : 0;
    }
    
    public void refreshTestStatusCounts() {
        passed = 0;
        failed = 0;
        others = 0;
        
        testList.forEach(x -> {
            updateTestStatusCounts(x);
        });
    }
    
    public T getContextKey() {
        return ta;
    }
    
    public List<Test> getTestList() { return testList; }
    
    public String getName() { return ta.getName(); }
    
    public int getPassed() { return passed; }
    
    public int getFailed() { return failed; }
    
    public int getOthers() { return others; }
    
    public int size() { return testList == null ? 0 : testList.size(); }
    
    public T getAttribute() { return ta; }
}

