package com.aventstack.extentreports.reporter;

import java.io.File;
import java.util.Calendar;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;

public class SparkReporterTest {
    private static final String FILE_PATH = "target/spark/";
    private static final String FILE_NAME = "spark.html";
    private static final String PARENT = "Parent";
    private static final String CHILD = "Child";
    private static final String GRANDCHILD = "Grandchild";
    private static final String SCRIPTS = "spark-script.js";
    private static final String STYLESHEET = "spark-style.css";

    private final String path() {
        return FILE_PATH + Calendar.getInstance().getTimeInMillis() + FILE_NAME;
    }

    private void assertFileExists(String path) {
        File f = new File(path);
        Assert.assertTrue(f.exists());
        f.delete();
    }

    @Test
    public void createsReportWithNoTestsInitPath() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        extent.attachReporter(spark);
        extent.flush();
        assertFileExists(path);
    }

    @Test
    public void createsReportWithNoTestsInitFile() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(new File(path));
        extent.attachReporter(spark);
        extent.flush();
        assertFileExists(path);
    }

    @Test
    public void reportContainsTestsAndNodes() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        extent.attachReporter(spark);
        extent.createTest(PARENT)
                .createNode(CHILD)
                .createNode(GRANDCHILD)
                .pass("Pass");
        extent.flush();
        assertFileExists(path);
        Assert.assertEquals(spark.getReport().getTestList().size(), 1);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getName(), PARENT);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getChildren().size(), 1);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getChildren().get(0).getName(), CHILD);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getChildren().get(0).getChildren().size(), 1);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getChildren().get(0).getChildren().get(0).getName(),
                GRANDCHILD);
    }

    @Test
    public void reportContainsTestsAndNodesTags() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        extent.attachReporter(spark);
        extent.createTest(PARENT).assignCategory("Tag1")
                .createNode(CHILD).assignCategory("Tag2")
                .createNode(GRANDCHILD).assignCategory("Tag3")
                .pass("Pass");
        extent.flush();
        assertFileExists(path);
        com.aventstack.extentreports.model.Test t = spark.getReport().getTestList().get(0);
        Assert.assertTrue(t.getCategorySet().stream().anyMatch(x -> x.getName().equals("Tag1")));
        Assert.assertTrue(t.getChildren().get(0).getCategorySet().stream().anyMatch(x -> x.getName().equals("Tag2")));
        Assert.assertTrue(t.getChildren().get(0).getChildren().get(0).getCategorySet().stream()
                .anyMatch(x -> x.getName().equals("Tag3")));
    }

    @Test
    public void reportContainsTestsAndNodesUsers() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        extent.attachReporter(spark);
        extent.createTest(PARENT).assignAuthor("Tag1")
                .createNode(CHILD).assignAuthor("Tag2")
                .createNode(GRANDCHILD).assignAuthor("Tag3")
                .pass("Pass");
        extent.flush();
        assertFileExists(path);
        com.aventstack.extentreports.model.Test t = spark.getReport().getTestList().get(0);
        Assert.assertTrue(t.getAuthorSet().stream().anyMatch(x -> x.getName().equals("Tag1")));
        Assert.assertTrue(t.getChildren().get(0).getAuthorSet().stream().anyMatch(x -> x.getName().equals("Tag2")));
        Assert.assertTrue(t.getChildren().get(0).getChildren().get(0).getAuthorSet().stream()
                .anyMatch(x -> x.getName().equals("Tag3")));
    }

    @Test
    public void reportContainsTestsAndNodesDevices() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        extent.attachReporter(spark);
        extent.createTest(PARENT).assignDevice("Tag1")
                .createNode(CHILD).assignDevice("Tag2")
                .createNode(GRANDCHILD).assignDevice("Tag3")
                .pass("Pass");
        extent.flush();
        assertFileExists(path);
        com.aventstack.extentreports.model.Test t = spark.getReport().getTestList().get(0);
        Assert.assertTrue(t.getDeviceSet().stream().anyMatch(x -> x.getName().equals("Tag1")));
        Assert.assertTrue(t.getChildren().get(0).getDeviceSet().stream().anyMatch(x -> x.getName().equals("Tag2")));
        Assert.assertTrue(t.getChildren().get(0).getChildren().get(0).getDeviceSet().stream()
                .anyMatch(x -> x.getName().equals("Tag3")));
    }

    @Test
    public void statusFilterable() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path)
                .filter()
                .statusFilter()
                .as(new Status[]{Status.FAIL})
                .apply();
        extent.attachReporter(spark);
        extent.createTest(PARENT).pass("Pass");
        extent.createTest(CHILD).fail("Fail");
        extent.flush();
        assertFileExists(path);
        Assert.assertEquals(spark.getReport().getTestList().size(), 1);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getName(), CHILD);
    }

    @Test
    public void statusFilterableNode() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path)
                .filter()
                .statusFilter()
                .as(new Status[]{Status.FAIL})
                .apply();
        extent.attachReporter(spark);
        extent.createTest(PARENT).pass("Pass");
        extent.createTest(CHILD).pass("Pass")
                .createNode(GRANDCHILD).fail("Fail");
        extent.flush();
        assertFileExists(path);
        Assert.assertEquals(spark.getReport().getTestList().size(), 1);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getName(), CHILD);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getChildren().size(), 1);
        Assert.assertEquals(spark.getReport().getTestList().get(0).getChildren().get(0).getName(), GRANDCHILD);
    }

    @Test
    public void sparkOffline() {
        ExtentReports extent = new ExtentReports();
        String path = path();
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        spark.config().enableOfflineMode(true);
        extent.attachReporter(spark);
        extent.createTest(PARENT).pass("Pass");
        extent.flush();
        assertFileExists(path);
        Assert.assertTrue(new File(FILE_PATH + "spark/" + SCRIPTS).exists());
        Assert.assertTrue(new File(FILE_PATH + "spark/" + STYLESHEET).exists());
    }
}
