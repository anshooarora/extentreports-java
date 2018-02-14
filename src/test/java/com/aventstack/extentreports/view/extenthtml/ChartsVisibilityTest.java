package com.aventstack.extentreports.view.extenthtml;

import java.lang.reflect.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.Reader;

public class ChartsVisibilityTest {
    
    private static String PATH = "test-output/";
    private static String EXT = ".html";
    
    @Test
    public void testAndLogsExpectsParentAndGrandChildCharts(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).pass("Pass");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#parent-analysis").size(), 1);
        Assert.assertEquals(doc.select("#child-analysis").size(), 1);
    }
    
    @Test
    public void classAndTestAndLogsExpectsAllCharts(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.createTest(method.getName()).createNode("Child").pass("Pass");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#parent-analysis").size(), 1);
        Assert.assertEquals(doc.select("#child-analysis").size(), 1);
    }
    
    @Test
    public void bddExpectsAllCharts(Method method) {
        String fileName = PATH + method.getName() + EXT;
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        ExtentTest feature = extent.createTest(Feature.class, "Feature");
        ExtentTest scenario = feature.createNode(Scenario.class, "Scenario");
        scenario.createNode(Given.class, "Given");
        extent.flush();
        
        String html = Reader.readAllText(fileName);
        Document doc = Jsoup.parse(html);
        
        Assert.assertEquals(doc.select("#parent-analysis").size(), 1);
        Assert.assertEquals(doc.select("#child-analysis").size(), 1);
        Assert.assertEquals(doc.select("#grandchild-analysis").size(), 1);
    }
    
}
