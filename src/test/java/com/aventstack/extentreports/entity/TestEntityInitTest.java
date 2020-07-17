package com.aventstack.extentreports.entity;

import java.util.Collections;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Test;

public class TestEntityInitTest {

    @org.testng.annotations.Test
    public void startAndEndTimesNonNullAtInit() {
        Test test = Test.builder().build();
        Assert.assertNotNull(test.getStartTime());
        Assert.assertNotNull(test.getEndTime());
    }

    @org.testng.annotations.Test
    public void startIsPassOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }

    @org.testng.annotations.Test
    public void levelIs0OnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getLevel().intValue(), 0);
    }

    @org.testng.annotations.Test
    public void testIsLeafOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.isLeaf(), true);
    }

    @org.testng.annotations.Test
    public void childrenEmptyOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getChildren(), Collections.EMPTY_LIST);
    }

    @org.testng.annotations.Test
    public void logsEmptyOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getLogs(), Collections.EMPTY_LIST);
    }

    @org.testng.annotations.Test
    public void authorsEmptyOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getAuthorSet(), Collections.EMPTY_SET);
    }

    @org.testng.annotations.Test
    public void devicesEmptyOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getDeviceSet(), Collections.EMPTY_SET);
    }

    @org.testng.annotations.Test
    public void tagEmptyOnInit() {
        Test test = Test.builder().build();
        Assert.assertEquals(test.getCategorySet(), Collections.EMPTY_SET);
    }

    @org.testng.annotations.Test
    public void testId1OrGreaterOnInit() {
        Test test = Test.builder().build();
        Assert.assertNotEquals(test.getId(), 0);
    }

    @org.testng.annotations.Test
    public void descriptionNullOnInit() {
        Test test = Test.builder().build();
        Assert.assertNull(test.getDescription());
    }

    @org.testng.annotations.Test
    public void parentNullOnInit() {
        Test test = Test.builder().build();
        Assert.assertNull(test.getParent());
    }
}
