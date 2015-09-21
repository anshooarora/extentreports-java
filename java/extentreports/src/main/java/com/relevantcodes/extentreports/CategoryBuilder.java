/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.CategoryHtml;
import com.relevantcodes.extentreports.support.DateTimeHelper;

public class CategoryBuilder {
    public static void buildCategoryViewLink(Document extentDoc, Test test) {
        String catName;
        Element divCat = null;
        
        for (TestAttribute attr : test.getCategoryList()) {
            catName = attr.getName().trim().toLowerCase().replace(" ", "");
            
            if (extentDoc.select(".category-item." + catName).size() == 0) {
                divCat = Jsoup.parseBodyFragment(CategoryHtml.getCategoryViewSource()).select("li").first();
                
                divCat.select(".category-item").first().addClass(catName);
                divCat.select(".category-name").first().text(attr.getName());
                
                extentDoc.select(".cat-collection").first().appendChild(divCat);
            }
            else {
                divCat = extentDoc.select(".category-item." + catName).first();
            }
            
            Element trTest = Jsoup.parseBodyFragment(CategoryHtml.getCategoryViewTestSource()).select("tr").first();
            trTest.select("td").first().text(DateTimeHelper.getFormattedDateTime(test.getStartedTime(), LogSettings.logDateTimeFormat));
            trTest.select(".category-link").first().text(test.getName()).attr("extentId", test.getId().toString());
            trTest.select(".label").first().text(test.getStatus().toString()).addClass(test.getStatus().toString());
            
            divCat.select("table").first().appendChild(trTest);
        }
    }
}
