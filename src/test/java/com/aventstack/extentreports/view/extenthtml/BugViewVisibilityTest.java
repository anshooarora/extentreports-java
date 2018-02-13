package com.aventstack.extentreports.view.extenthtml;

import java.lang.reflect.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.Reader;

public class BugViewVisibilityTest {

    private static String PATH = "test-output/";
    private static String EXT = ".html";
    
    @Test
    public void noTestsHaveExceptionsBugViewNotExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).pass("Pass");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#exception-view").size(), 0);
    }
    
    @Test
    public void parentTestHasExceptionsBugViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).fail(new RuntimeException("RuntimeException"));
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
    @Test
    public void childTestHasExceptionsBugViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").fail(new RuntimeException("RuntimeException"));
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
    @Test
    public void grandChildTestHasExceptionsBugViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").createNode("GrandChild").fail(new RuntimeException("RuntimeException"));
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
}
