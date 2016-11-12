package com.aventstack.extentreports.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.TestAttribute;

public class NodeAttributesTest extends Base {

    private final String[] categories = {
            "extent",
            "git",
            "tests",
            "heroku"
    };
    private final String[] authors = {
            "anshoo",
            "viren",
            "maxi",
            "vimal"
    };
    
    @Test
    public void verifyIfNodeHasAddedCategory(Method method) {
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .assignCategory(categories[0])
                .pass("pass");
        
        Assert.assertEquals(node.getModel().getCategoryContext().size(), 1);
        Category c = (Category) node.getModel().getCategory(0);
        Assert.assertEquals(c.getName(), categories[0]);
    }
    
    @Test
    public void verifyIfTestHasAddedCategories(Method method) {
        ExtentTest node = extent.createTest(method.getName()).createNode("Child").pass("pass");        
        Arrays.stream(categories).forEach(c -> node.assignCategory(c));
               
        Assert.assertEquals(node.getModel().getCategoryContext().size(), categories.length);
        
        List<TestAttribute> categoryCollection = node.getModel().getCategoryContext().getAll();
        Arrays.stream(categories).forEach(c -> {
            Boolean result = categoryCollection.stream().anyMatch(x -> x.getName() == c); 
            Assert.assertTrue(result);
        });
    }
    
    @Test
    public void verifyIfTestHasAddedAuthor(Method method) {
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .assignAuthor(authors[0])
                .pass("pass");
        
        Assert.assertEquals(node.getModel().getAuthorContext().size(), 1);
        Assert.assertEquals(node.getModel().getAuthor(0).getName(), authors[0]);
    }
    
    @Test
    public void verifyIfTestHasAddedAuthors(Method method) {
        ExtentTest node = extent
                .createTest(method.getName())
                .createNode("Child")
                .pass("pass");
        Arrays.stream(authors).forEach(a -> node.assignAuthor(a));
               
        Assert.assertEquals(node.getModel().getAuthorContext().size(), authors.length);
        
        List<TestAttribute> authorCollection = node.getModel().getAuthorContext().getAll();
        Arrays.stream(authors).forEach(a -> {
            Boolean result = authorCollection.stream().anyMatch(x -> x.getName() == a); 
            Assert.assertTrue(result);
        });
    }
}
