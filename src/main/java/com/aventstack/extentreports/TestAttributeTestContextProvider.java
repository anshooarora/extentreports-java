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
    
    @SuppressWarnings("unchecked")
    public void setAttributeContext(T attr, Test test) {
        Optional<TestAttributeTestContext> testOptionalTestContext = testAttrCollection
                .stream()
                .filter(x -> x.getName().equals(attr.getName()))
                .findFirst();
        
        if (testOptionalTestContext.isPresent()) {
            List<Test> testList = testOptionalTestContext.get().getTestList();
            
            boolean b = testList
                    .stream()
                    .filter(t -> t.getID() == test.getID())
                    .findFirst()
                    .isPresent();
            
            if (!b)
                testOptionalTestContext.get().setTest(test);
            
            testOptionalTestContext.get().refreshTestStatusCounts();
        }
        else {
            TestAttributeTestContext testAttrContext = new TestAttributeTestContext<T>(attr);
            testAttrContext.setTest(test);
            
            testAttrCollection.add(testAttrContext);
        }
    }
    
    public List<TestAttributeTestContext> getCategoryTestContextList() {
        return testAttrCollection;
    }
    
}
