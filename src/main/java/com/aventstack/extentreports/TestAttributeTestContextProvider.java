package com.aventstack.extentreports;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.model.TestAttributeTestContext;

@SuppressWarnings("rawtypes")
public class TestAttributeTestContextProvider<T extends TestAttribute> {
    
    List<TestAttributeTestContext> taContextList;
    
    public TestAttributeTestContextProvider() { 
        taContextList = new ArrayList<>();
    }
    
    public void setAttributeContext(T attr, Test test) {
        try {
            TestAttributeTestContext taContext = taContextList.stream().filter(x -> x.getName().equals(attr.getName())).findFirst().get();
            taContext.setTest(test);
        }
        catch (NoSuchElementException e) {
            TestAttributeTestContext taContext = new TestAttributeTestContext<T>(attr);
            taContext.setTest(test);
            taContextList.add(taContext);
            
            return;
        }
    }
    
    public List<TestAttributeTestContext> getCategoryTestContextList() {
        return taContextList;
    }
    
}
