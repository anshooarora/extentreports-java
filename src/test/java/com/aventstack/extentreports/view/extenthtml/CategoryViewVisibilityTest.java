package com.aventstack.extentreports.view.extenthtml;

import java.lang.reflect.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.Reader;

public class CategoryViewVisibilityTest {

    private static String PATH = "test-output/";
    private static String EXT = ".html";
    
    @Test
    public void noTestsHaveCategoriesCategoryViewNotExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).pass("Pass");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#category-view").size(), 0);
        Assert.assertEquals(doc.select("#exception-view").size(), 0);
    }
    
    @Test
    public void parentTestHasCategoriesCategoryViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).fail(new RuntimeException("RuntimeException")).assignCategory("Extent");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#category-view").size(), 1);
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
    @Test
    public void childTestHasCategoriesCategoryViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").fail(new RuntimeException("RuntimeException")).assignCategory("Extent");;
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#category-view").size(), 1);
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
    @Test
    public void grandChildTestHasCategoriesCategoryViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").createNode("GrandChild").fail(new RuntimeException("RuntimeException")).assignCategory("Extent");;
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#category-view").size(), 1);
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
}
