package com.aventstack.extentreports.view.extenthtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.utils.Reader;

public class TestAttributesTest extends Base {

    final String testName = getClass().getName();
    final String categoryName = "Extent";

    private ExtentTest test;
    private Document doc;
    private Elements htmlTest;
    
    @BeforeClass
    public void localSetup() {
        test = extent.createTest(testName).assignCategory(categoryName);
        test.pass(testName);

        extent.flush();
        
        String html = Reader.readAllText(htmlFilePath);
        doc = Jsoup.parse(html);
        
        htmlTest = doc.select("#test-collection .test");
    }
    
    @Test
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
        Assert.assertEquals(testIDCategoryView, test.getModel().getID());
    }
    
    @Test
    public void validateCategoryNameExistsInDashboardView() {
        Elements htmlCategory = doc.select("#dashboard-view").first().select("td:contains(" + categoryName + ")");
        Assert.assertNotEquals(htmlCategory.size(), 0);
    }

}
