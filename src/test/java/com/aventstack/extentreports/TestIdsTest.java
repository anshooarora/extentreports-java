package com.aventstack.extentreports;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestIdsTest {
    private final int ATTEMPTS = 100;

    @Test
    public void verifyAllStartedTestsHaveUniqueIds(Method method) {
        ExtentReports extent = new ExtentReports();
        Set<Integer> set = new HashSet<>();
        // create [times] tests to ensure test-id is not duplicate
        IntStream.range(0, ATTEMPTS)
                .forEach(x -> set.add(extent.createTest("" + x).info("" + x).getModel().getId()));
        Assert.assertEquals(set.size(), ATTEMPTS);
    }
}
