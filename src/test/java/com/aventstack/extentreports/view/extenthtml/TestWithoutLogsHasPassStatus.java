package com.aventstack.extentreports.view.extenthtml;

import java.lang.reflect.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.Reader;

public class TestWithoutLogsHasPassStatus extends Base {
	
    @Test
    public void testShowsUnknownStatusIfNoLogsAreAddedView(Method method) {
    	extent.createTest(method.getName());
        extent.flush();
        
        String html = Reader.readAllText(htmlFilePath);
        Document doc = Jsoup.parse(html);
        
        Element htmlTest = doc.select("#test-collection .test").first();
        String status = htmlTest.attr("status");
        
        Assert.assertEquals(status, Status.UNKNOWN.toString().toLowerCase());
    }
}
