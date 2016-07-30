package com.relevantcodes.extentreports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.utils.Reader;

public class TestAttributesTestWithCategory extends Base {

    final String filePath = getOutputFolder() + getClass().getName() + ".html";
    final String testName = getClass().getName();
    final String categoryName = "Extent";
    
    ExtentReports extent;
    ExtentTest test;  
    Document doc;
    Elements htmlTest;
    
    @BeforeSuite
    public void beforeSuite() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    
    @BeforeClass
    public void beforeClass() {
        test = extent.createTest(testName).assignCategory(categoryName);
        test.pass(testName);

        extent.collectRunInfo();
        
        String html = Reader.readAllText(filePath);
        doc = Jsoup.parse(html);
        
        htmlTest = doc.select("#test-collection .test");
    }
    
    @Test(dependsOnGroups={"test-attributes-base"})
    public void validateCategoryName() {
        String categoryNameView = htmlTest.select(".category").first().html().trim();
        
        Assert.assertEquals(categoryNameView, categoryName);
    }
    
    @Test
    public void validateCategoryViewEnabled() {
        Element categoryView = doc.select("#slide-out").first().select("a[view=category-view']").first();
        Assert.assertNotNull(categoryView);
    }
        
    @Test
    public void validateIfCategoryViewHasCategory() {
        int categoryCountCategoryView = doc.select("#category-collection").first().select(".category-name").size();
        Assert.assertEquals(categoryCountCategoryView, 1);
    }
    
    @Test
    public void validateIfCategoryViewHasCategoryWithCorrectName() {
        String categoryNameCategoryView = doc.select("#category-collection").first().select(".category-name").first().html();
        Assert.assertEquals(categoryNameCategoryView, categoryName);
    }
    
    @Test
    public void validateCategoryViewTestName() {
        String testNameCategoryView = doc.select("#category-collection").first().select(".category").first().select(".linked").first().html();
        Assert.assertEquals(testNameCategoryView, testName);
    }
    
    @Test
    public void validateCategoryViewTestID() {
        int testIDCategoryView = Integer.valueOf(doc.select("#category-collection").first().select(".category").first().select(".linked").first().attr("test-id"));
        Assert.assertEquals(testIDCategoryView, test.getInternalTest().getID());
    }
    
    @Test
    public void validateCategoryNameExistsInDashboardView() {
        Elements htmlCategory = doc.select("#dashboard-view").first().select("td:contains(" + categoryName + ")");
        Assert.assertNotEquals(htmlCategory.size(), 0);
    }

}
