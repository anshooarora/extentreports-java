package com.relevantcodes.extentreports;

import java.util.Collections;
import java.util.List;

import com.relevantcodes.extentreports.source.CategoryFilter;
import com.relevantcodes.extentreports.source.ExtentFlag;

public class CategoryOptionBuilder {
    public static String build(List<String> categories) {
        String source = "";
        
        categories = categories.subList(0, categories.size());
        Collections.sort(categories);
        
        for (String c : categories) {
            source += CategoryFilter.getOptionSource()
                    .replace(ExtentFlag.getPlaceHolder("testCategory"), c)
                    .replace(ExtentFlag.getPlaceHolder("testCategoryU"), c.toLowerCase().replace(" ", ""));
        }
        
        return source;
    }
}
