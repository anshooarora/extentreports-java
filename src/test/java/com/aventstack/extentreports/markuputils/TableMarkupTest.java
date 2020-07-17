package com.aventstack.extentreports.markuputils;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TableMarkupTest {

    @Test
    public void tableWithNullText() {
        Markup m = MarkupHelper.createTable(null);
        Assert.assertEquals(m.getMarkup(), "");
    }

    @Test
    public void tableWithData() {
        String[][] data = new String[][]{{"h1", "h2"}, {"c1", "c2"}};
        Markup m = MarkupHelper.createTable(data);
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<td>h1</td>"));
        Assert.assertTrue(s.contains("<td>h2</td>"));
        Assert.assertTrue(s.contains("<td>c1</td>"));
        Assert.assertTrue(s.contains("<td>c2</td>"));
        Assert.assertTrue(s.contains("<table"));
        Assert.assertTrue(s.contains("</table>"));
    }

    @Test
    public void tableWithSingleStringField() {
        Markup m = MarkupHelper.toTable("String");
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<td>String</td>"));
        Assert.assertFalse(s.contains("<th>String</th>"));
    }

    @Test
    public void tableWithSingleList() {
        Markup m = MarkupHelper.toTable(Arrays.asList("Anshoo", "Extent", "Klov"));
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<td>Anshoo</td>"));
        Assert.assertTrue(s.contains("<td>Extent</td>"));
        Assert.assertTrue(s.contains("<td>Klov</td>"));
    }

    @Test
    public void tableWithSingleSet() {
        Markup m = MarkupHelper.toTable(Arrays.asList("Anshoo", "Extent", "Klov")
                .stream().collect(Collectors.toSet()));
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<td>Anshoo</td>"));
        Assert.assertTrue(s.contains("<td>Extent</td>"));
        Assert.assertTrue(s.contains("<td>Klov</td>"));
    }

    @Test
    public void tableWithSingleArray() {
        Markup m = MarkupHelper.toTable(new String[]{"Anshoo", "Extent", "Klov"});
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<td>Anshoo</td>"));
        Assert.assertTrue(s.contains("<td>Extent</td>"));
        Assert.assertTrue(s.contains("<td>Klov</td>"));
    }

    @Test
    public void tableWithPojoBeginningEndingTags() {
        Markup m = MarkupHelper.toTable(new Foo());
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<table"));
        Assert.assertTrue(s.contains("</table>"));
    }

    @Test
    public void tableWithPojoHeaders() {
        Markup m = MarkupHelper.toTable(new Foo());
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<th>names</th>"));
        Assert.assertTrue(s.contains("<th>stack1</th>"));
        Assert.assertTrue(s.contains("<th>items</th>"));
        Assert.assertFalse(s.contains("<th>ignored</th>"));
    }

    @Test
    public void tableWithPojoCells() {
        Markup m = MarkupHelper.toTable(new Foo());
        String s = m.getMarkup();
        Assert.assertTrue(s.contains("<td>Anshoo</td>"));
        Assert.assertTrue(s.contains("<td>Extent</td>"));
        Assert.assertTrue(s.contains("<td>Klov</td>"));
        Assert.assertTrue(s.contains("<td>Java</td>"));
        Assert.assertTrue(s.contains("<td>C#</td>"));
        Assert.assertTrue(s.contains("<td>Angular</td>"));
        Assert.assertTrue(s.contains("<td>Item1:Value1</td>"));
        Assert.assertTrue(s.contains("<td>Item2:Value2</td>"));
        Assert.assertTrue(s.contains("<td>Item3:Value3</td>"));
        Assert.assertFalse(s.contains("<td>Anshoo/Ignore</td>"));
        Assert.assertFalse(s.contains("<td>Extent/Ignore</td>"));
        Assert.assertFalse(s.contains("<td>Klov/Ignore</td>"));
    }
}
