/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.Collections;
import java.util.List;

import com.relevantcodes.extentreports.source.CategoryHtml;
import com.relevantcodes.extentreports.source.ExtentFlag;

public class CategorySourceBuilder {
    public static String buildOptions(List<String> categories) {
        String source = "";
        
        categories = categories.subList(0, categories.size());
        Collections.sort(categories);
        
        for (String c : categories) {
            source += CategoryHtml.getOptionSource()
                    .replace(ExtentFlag.getPlaceHolder("testCategory"), c)
                    .replace(ExtentFlag.getPlaceHolder("testCategoryU"), c.toLowerCase().replace(" ", ""));
        }
        
        return source;
    }
}
