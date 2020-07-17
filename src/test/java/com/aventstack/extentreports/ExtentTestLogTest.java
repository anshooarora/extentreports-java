package com.aventstack.extentreports;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtentTestLogTest {
    private static final String DETAILS = "details";
    private static final String ATTACHMENT = "img.png";

    private ExtentTest test() {
        return new ExtentReports().createTest("Test");
    }

    private Exception ex() {
        return new RuntimeException();
    }

    @Test
    public void logDetails() {
        ExtentTest test = test().log(Status.SKIP, DETAILS + "1");
        Assert.assertEquals(test.getModel().getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS + "1");
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
        test.log(Status.FAIL, DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(1).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(1).getStatus(), Status.FAIL);
    }

    @Test
    public void logMedia() throws IOException {
        ExtentTest test = test().log(Status.SKIP, DETAILS,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
        test.log(Status.FAIL, DETAILS, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(1).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(1).getStatus(), Status.FAIL);
        Assert.assertEquals(test.getModel().getLogs().get(1).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void logThrowable() {
        Exception ex = ex();
        ExtentTest test = test().log(Status.SKIP, ex);
        Assert.assertEquals(test.getModel().getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
        test.log(Status.FAIL, ex);
        Assert.assertEquals(test.getModel().getLogs().get(1).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(1).getStatus(), Status.FAIL);
    }

    @Test
    public void logThrowableMedia() throws IOException {
        Exception ex = ex();
        ExtentTest test = test().log(Status.SKIP, ex,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
        test.log(Status.FAIL, ex, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(1).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(1).getStatus(), Status.FAIL);
        Assert.assertEquals(test.getModel().getLogs().get(1).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void failDetails() {
        ExtentTest test = test().log(Status.FAIL, DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.FAIL);
    }

    @Test
    public void failMedia() throws IOException {
        ExtentTest test = test().log(Status.FAIL, DETAILS,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        test.log(Status.FAIL, DETAILS, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.FAIL);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void failThrowable() {
        Exception ex = ex();
        ExtentTest test = test().log(Status.FAIL, ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.FAIL);
    }

    @Test
    public void failThrowableMedia() throws IOException {
        Exception ex = ex();
        ExtentTest test = test().log(Status.FAIL, ex,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.FAIL);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void skipDetails() {
        ExtentTest test = test().log(Status.SKIP, DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
    }

    @Test
    public void skipMedia() throws IOException {
        ExtentTest test = test().log(Status.SKIP, DETAILS,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        test.log(Status.FAIL, DETAILS, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void skipThrowable() {
        Exception ex = ex();
        ExtentTest test = test().log(Status.SKIP, ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
    }

    @Test
    public void skipThrowableMedia() throws IOException {
        Exception ex = ex();
        ExtentTest test = test().log(Status.SKIP, ex,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.SKIP);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void warnDetails() {
        ExtentTest test = test().log(Status.WARNING, DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.WARNING);
    }

    @Test
    public void warnMedia() throws IOException {
        ExtentTest test = test().log(Status.WARNING, DETAILS,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        test.log(Status.WARNING, DETAILS, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.WARNING);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void warnThrowable() {
        Exception ex = ex();
        ExtentTest test = test().log(Status.WARNING, ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.WARNING);
    }

    @Test
    public void warnThrowableMedia() throws IOException {
        Exception ex = ex();
        ExtentTest test = test().log(Status.WARNING, ex,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.WARNING);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void passDetails() {
        ExtentTest test = test().log(Status.PASS, DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.PASS);
    }

    @Test
    public void passMedia() throws IOException {
        ExtentTest test = test().log(Status.PASS, DETAILS,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        test.log(Status.PASS, DETAILS, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.PASS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void passThrowable() {
        Exception ex = ex();
        ExtentTest test = test().log(Status.PASS, ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.PASS);
    }

    @Test
    public void passThrowableMedia() throws IOException {
        Exception ex = ex();
        ExtentTest test = test().log(Status.PASS, ex,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.PASS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void infoDetails() {
        ExtentTest test = test().log(Status.INFO, DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getDetails(), DETAILS);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.INFO);
    }

    @Test
    public void infoMedia() throws IOException {
        ExtentTest test = test().log(Status.INFO, DETAILS,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        test.log(Status.INFO, DETAILS, MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.INFO);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }

    @Test
    public void infoThrowable() {
        Exception ex = ex();
        ExtentTest test = test().log(Status.INFO, ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.INFO);
    }

    @Test
    public void infoThrowableMedia() throws IOException {
        Exception ex = ex();
        ExtentTest test = test().log(Status.INFO, ex,
                MediaEntityBuilder.createScreenCaptureFromPath(ATTACHMENT).build());
        Assert.assertEquals(test.getModel().getLogs().get(0).getException().getException(), ex);
        Assert.assertEquals(test.getModel().getLogs().get(0).getStatus(), Status.INFO);
        Assert.assertEquals(test.getModel().getLogs().get(0).getMedia().getPath(), ATTACHMENT);
    }
}
