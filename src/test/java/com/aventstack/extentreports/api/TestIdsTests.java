package com.aventstack.extentreports.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;

public class TestIdsTests extends Base {

    private final int times = 100;
    
    @Test
    public void verifyAllStartedTestsHaveUniqueIds(Method method) {
        List<Integer> idCollection = new ArrayList<>();
        
        // create [times] tests to ensure test-id is not duplicate 
        for (int ix = 0; ix < times; ix++) {
            int testId = extent.createTest(method.getName() + "." + ix).info("test # " + ix).getModel().getID();

            Assert.assertFalse(idCollection.contains(testId));
            
            idCollection.add(testId);
        }
    }
}
