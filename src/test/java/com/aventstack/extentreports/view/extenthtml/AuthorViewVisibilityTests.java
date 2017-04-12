package com.aventstack.extentreports.view.extenthtml;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.Reader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;

public class AuthorViewVisibilityTests {

    private static String PATH = "test-output/";
    private static String EXT = ".html";

    @BeforeClass
    public void setup() {
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    @Test
    public void noTestsHaveAuthorsAuthorViewNotExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).pass("Pass");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#author-view").size(), 0);
        Assert.assertEquals(doc.select("#exception-view").size(), 0);
    }
    
    @Test
    public void parentTestHasAuthorsAuthorViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).fail(new RuntimeException("RuntimeException")).assignAuthor("Vimal");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#author-view").size(), 1);
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
    @Test
    public void childTestHasAuthorsAuthorViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").fail(new RuntimeException("RuntimeException")).assignAuthor("Vimal");;
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#author-view").size(), 1);
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
    @Test
    public void grandChildTestHasAuthorsAuthorViewExpected(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").createNode("GrandChild").fail(new RuntimeException("RuntimeException")).assignAuthor("Vimal");;
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#author-view").size(), 1);
        Assert.assertEquals(doc.select("#exception-view").size(), 1);
    }
    
}
