package com.aventstack.extentreports;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.model.TestAttributeTestContext;

@SuppressWarnings("rawtypes")
public class TestAttributeTestContextProvider<T extends TestAttribute> {
    
    private List<TestAttributeTestContext> testAttrCollection;
    
    public TestAttributeTestContextProvider() { 
        testAttrCollection = new ArrayList<>();
    }
    
    public void setAttributeContext(T attr, Test test) {
        reset();
        
        Optional<TestAttributeTestContext> testOptionalTestContext = testAttrCollection
                .stream()
                .filter(x -> x.getName().equals(attr.getName()))
                .findFirst();
        
        if (testOptionalTestContext.isPresent()) {
            testOptionalTestContext.get().setTest(test);
        }
        else {
            TestAttributeTestContext testAttrContext = new TestAttributeTestContext<T>(attr);
            testAttrContext.setTest(test);
            
            testAttrCollection.add(testAttrContext);
        }
    }
    
    private void reset() {
        testAttrCollection.clear();
    }
    
    public List<TestAttributeTestContext> getCategoryTestContextList() {
        return testAttrCollection;
    }
    
}
