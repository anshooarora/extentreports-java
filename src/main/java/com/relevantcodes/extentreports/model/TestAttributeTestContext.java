package com.relevantcodes.extentreports.model;

import java.util.ArrayList;
import java.util.List;

import com.relevantcodes.extentreports.Status;

public class TestAttributeTestContext<T extends TestAttribute> {
    T ta;
    List<Test> testList;
    int passed = 0;
    int failed = 0;
    int others = 0;
    
    public TestAttributeTestContext(T ta) { this.ta = ta; }
    
    public void setTest(Test test) {
        if (testList == null)
            testList = new ArrayList<>();
        
        passed += test.getStatus() == Status.PASS ? 1 : 0;
        failed += test.getStatus() == Status.FAIL ? 1 : 0;
        others += test.getStatus() != Status.PASS && test.getStatus() != Status.FAIL ? 1 : 0;
        
        testList.add(test);
    }
    
    public List<Test> getTestList() { return testList; }
    
    public String getName() { return ta.getName(); }
    
    public int getPassed() { return passed; }
    
    public int getFailed() { return failed; }
    
    public int getOthers() { return others; }
    
    public T getAttribute() { return ta; }
}

