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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.CategoryHtml;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.support.DateTimeHelper;

public class CategoryBuilder {
    public static String buildOptions(List<String> categories) {
        String source = "";
        
        categories = categories.subList(0, categories.size());
        Collections.sort(categories);
        
        String[] flags = { 
                ExtentFlag.getPlaceHolder("testCategory"), 
                ExtentFlag.getPlaceHolder("testCategoryU") 
        };
        
        for (String c : categories) {
            source += SourceBuilder.build(
                    CategoryHtml.getOptionSource(), 
                    flags, 
                    new String[] { c, c.toLowerCase().replace(" ", "") }
            );
        }
        
        return source;
    }
    
    public static void buildCategoryViewLink(Document extentDoc, Test test) {
        String catName;
        Element divCat = null;
        
        for (TestAttribute attr : test.getCategoryList()) {
            catName = attr.getName().trim().toLowerCase().replace(" ", "");
            
            if (extentDoc.select(".category-view." + catName).size() == 0) {
                divCat = Jsoup.parseBodyFragment(CategoryHtml.getCategoryViewSource()).select("div").first();
                
                divCat.select(".category-view").first().addClass(catName);
                divCat.select(".category").first().text(attr.getName());
                
                extentDoc.select("#category-quick-view").first().appendChild(divCat);
            }
            else {
                divCat = extentDoc.select(".category-view." + catName).first();
            }
            
            Element trTest = Jsoup.parseBodyFragment(CategoryHtml.getCategoryViewTestSource()).select("tr").first();
            trTest.select("td").first().text(DateTimeHelper.getFormattedDateTime(test.getStartedTime(), LogSettings.logDateTimeFormat));
            trTest.select(".category-link").first().text(test.getName()).attr("extentId", test.getId().toString());
            trTest.select(".label").first().text(test.getStatus().toString()).addClass(test.getStatus().toString());
            
            divCat.select("table").first().appendChild(trTest);
        }
    }
}
