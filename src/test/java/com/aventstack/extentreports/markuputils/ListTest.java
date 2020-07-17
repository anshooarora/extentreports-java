package com.aventstack.extentreports.markuputils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ListTest {
    @Test
    public void unorderedListFromString() {
        String items = "Item";
        Markup m = MarkupHelper.createUnorderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ul>"));
        Assert.assertTrue(s.contains("</ul>"));
        Assert.assertTrue(s.contains("<li>Item</li>"));
    }

    @Test
    public void unorderedListFromArray() {
        String[] items = new String[]{"Item1", "Item2", "Item3"};
        Markup m = MarkupHelper.createUnorderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ul>"));
        Assert.assertTrue(s.contains("</ul>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
        Assert.assertTrue(s.contains("<li>Item2</li>"));
        Assert.assertTrue(s.contains("<li>Item3</li>"));
    }

    @Test
    public void unorderedListFromSet() {
        Set<Object> items = new HashSet<>(Arrays.asList("Item1", "Item2", "Item3"));
        Markup m = MarkupHelper.createUnorderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ul>"));
        Assert.assertTrue(s.contains("</ul>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
        Assert.assertTrue(s.contains("<li>Item2</li>"));
        Assert.assertTrue(s.contains("<li>Item3</li>"));
    }

    @Test
    public void unorderedListFromList() {
        List<Object> items = Arrays.asList(new Object[]{"Item1", "Item2", "Item3"});
        Markup m = MarkupHelper.createUnorderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ul>"));
        Assert.assertTrue(s.contains("</ul>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
        Assert.assertTrue(s.contains("<li>Item2</li>"));
        Assert.assertTrue(s.contains("<li>Item3</li>"));
    }

    @SuppressWarnings("serial")
    @Test
    public void unorderedListFromMap() {
        Map<Object, Object> items = new HashMap<Object, Object>() {
            {
                put("Item1", "Value1");
                put("Item2", "Value2");
                put("Item3", "Value3");
            }
        };
        Markup m = MarkupHelper.createUnorderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ul>"));
        Assert.assertTrue(s.contains("</ul>"));
        Assert.assertTrue(s.contains("<li>Item1:Value1</li>"));
        Assert.assertTrue(s.contains("<li>Item2:Value2</li>"));
        Assert.assertTrue(s.contains("<li>Item3:Value3</li>"));
    }

    @Test
    public void orderedListFromString() {
        String items = "Item1";
        Markup m = MarkupHelper.createOrderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ol>"));
        Assert.assertTrue(s.contains("</ol>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
    }

    @Test
    public void orderedListFromArray() {
        String[] items = new String[]{"Item1", "Item2", "Item3"};
        Markup m = MarkupHelper.createOrderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ol>"));
        Assert.assertTrue(s.contains("</ol>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
        Assert.assertTrue(s.contains("<li>Item2</li>"));
        Assert.assertTrue(s.contains("<li>Item3</li>"));
    }

    @Test
    public void orderedListFromSet() {
        Set<Object> items = new HashSet<>(Arrays.asList("Item1", "Item2", "Item3"));
        Markup m = MarkupHelper.createOrderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ol>"));
        Assert.assertTrue(s.contains("</ol>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
        Assert.assertTrue(s.contains("<li>Item2</li>"));
        Assert.assertTrue(s.contains("<li>Item3</li>"));
    }

    @Test
    public void orderedListFromList() {
        List<Object> items = Arrays.asList(new Object[]{"Item1", "Item2", "Item3"});
        Markup m = MarkupHelper.createOrderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ol>"));
        Assert.assertTrue(s.contains("</ol>"));
        Assert.assertTrue(s.contains("<li>Item1</li>"));
        Assert.assertTrue(s.contains("<li>Item2</li>"));
        Assert.assertTrue(s.contains("<li>Item3</li>"));
    }

    @SuppressWarnings("serial")
    @Test
    public void orderedListFromMap() {
        Map<Object, Object> items = new HashMap<Object, Object>() {
            {
                put("Item1", "Value1");
                put("Item2", "Value2");
                put("Item3", "Value3");
            }
        };
        Markup m = MarkupHelper.createOrderedList(items);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<ol>"));
        Assert.assertTrue(s.contains("</ol>"));
        Assert.assertTrue(s.contains("<li>Item1:Value1</li>"));
        Assert.assertTrue(s.contains("<li>Item2:Value2</li>"));
        Assert.assertTrue(s.contains("<li>Item3:Value3</li>"));
    }
}
