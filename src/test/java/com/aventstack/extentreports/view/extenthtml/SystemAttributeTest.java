package com.aventstack.extentreports.view.extenthtml;

import java.lang.reflect.Method;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.utils.Reader;

public class SystemAttributeTest extends Base {

    @BeforeClass
    public void beforeClass() {
        extent.createTest("Test").pass("Pass");
    }
    
    @Test (expectedExceptions = AssertionError.class)
    public void keyNullSystemAttribute(Method method) {
        String key = null;
        String value = "value";
        extent.setSystemInfo(key, value);
        performAssertForKVPairs(key, value);
    }
    
    @Test (expectedExceptions = AssertionError.class)
    public void valueNullSystemAttribute(Method method) {
        String key = "key";
        String value = null;
        extent.setSystemInfo(key, value);
        performAssertForKVPairs(key, value);
    }
    
    @Test (expectedExceptions = AssertionError.class)
    public void keyValueNullSystemAttribute(Method method) {
        String key = null;
        String value = null;
        extent.setSystemInfo(key, value);
        performAssertForKVPairs(key, value);
    }
    
    @Test
    public void simpleSystemAttribute(Method method) {
        String key = "attributeName";
        String value = "attributeValue";
        extent.setSystemInfo(key, value);
        performAssertForKVPairs(key, value);
    }
    
    private void performAssertForKVPairs(String key, String value) {
        Boolean keyFound = false;
        Boolean valueFound = false;
        
        extent.flush();
        String html = Reader.readAllText(htmlFilePath);
        Document doc = Jsoup.parse(html);
        
        Elements tdColl = doc.select(".dashboard-environment td");
        
        for (Element td : tdColl) {
            if (td.text().equals(key))
                keyFound = true;
            if (td.text().equals(value))
                valueFound = true;
        }
        
        Assert.assertTrue(keyFound);
        Assert.assertTrue(valueFound);
    }
    
}
