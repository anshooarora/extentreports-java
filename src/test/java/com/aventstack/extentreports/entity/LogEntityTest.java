package com.aventstack.extentreports.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ScreenCapture;

public class LogEntityTest {

    @Test
    public void defaultStatusBuilder() {
        Log log = Log.builder().build();
        Assert.assertEquals(log.getStatus(), Status.PASS);
    }

    /*
     * Lombok @Builder.Default bug causing not setting fields on instantiating
     * via new
     */
    // @Test
    // public void defaultStatusInstantiate() {
    // Log log = new Log();
    // Assert.assertEquals(log.getStatus(), Status.PASS);
    // }

    @Test
    public void changedStatus() {
        Log log = new Log();
        log.setStatus(Status.FAIL);
        Assert.assertEquals(log.getStatus(), Status.FAIL);
        log.setStatus(Status.PASS);
        Assert.assertEquals(log.getStatus(), Status.PASS);
    }

    @Test
    public void timestampNonNullOnInit() {
        Log log = Log.builder().build();
        Assert.assertNotNull(log.getTimestamp());
    }

    @Test
    public void detailsNullOnInit() {
        Log log = Log.builder().build();
        Assert.assertNull(log.getDetails());
    }

    @Test
    public void seqNegOnInit() {
        Log log = Log.builder().build();
        Assert.assertEquals(log.getSeq().intValue(), -1);
    }

    @Test
    public void mediaEmptyOnInit() {
        Log log = Log.builder().build();
        Assert.assertEquals(log.getMedia(), null);
    }

    @Test
    public void exceptionsEmptyOnInit() {
        Log log = Log.builder().build();
        Assert.assertEquals(log.getException(), null);
    }

    @Test
    public void addMediaDefault() {
        Log log = Log.builder().build();
        Assert.assertFalse(log.hasMedia());
    }

    @Test
    public void addMediaWithPathToLog() {
        Media m = ScreenCapture.builder().path("./img.png").build();
        Log log = Log.builder().media(m).build();
        Assert.assertTrue(log.hasMedia());
    }

    @Test
    public void addMediaWithResolvedPathToLog() {
        Media m = ScreenCapture.builder().resolvedPath("./img.png").build();
        Log log = Log.builder().media(m).build();
        Assert.assertTrue(log.hasMedia());
    }
}
