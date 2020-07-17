package com.aventstack.extentreports.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class StatusEntityTest {

    private List<Status> randomHierarchy() {
        List<Status> list = Arrays.asList(Status.values());
        Collections.shuffle(list);
        return list;
    }

    @Test
    public void statusMax() {
        Assert.assertEquals(Status.max(randomHierarchy()), Status.FAIL);
    }

    @Test
    public void statusMin() {
        Assert.assertEquals(Status.min(randomHierarchy()), Status.INFO);
    }

    @Test
    public void statusHierarchy() {
        List<Status> list = Status.getResolvedHierarchy(randomHierarchy());
        Assert.assertTrue(list.get(0).equals(Status.INFO));
        Assert.assertTrue(list.get(Status.values().length - 1).equals(Status.FAIL));
    }

}
