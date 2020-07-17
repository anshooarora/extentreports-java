package com.aventstack.extentreports.markuputils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.annotations.MarkupIgnore;

public class Foo {
    public List<Object> names = Arrays.asList("Anshoo", "Extent", "Klov");
    @SuppressWarnings("unused")
    private Object[] stack1 = new Object[]{"Java", "C#", "Angular"};
    @MarkupIgnore
    private List<Object> ignored = Arrays.asList("Anshoo/Ignore", "Extent/Ignore", "Klov/Ignore");
    @SuppressWarnings({"serial", "unused"})
    private Map<Object, Object> items = new HashMap<Object, Object>() {
        {
            put("Item1", "Value1");
            put("Item2", "Value2");
            put("Item3", "Value3");
        }
    };
}