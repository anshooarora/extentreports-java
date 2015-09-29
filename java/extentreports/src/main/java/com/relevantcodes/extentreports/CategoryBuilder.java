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

class CategoryBuilder {
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
            trTest.select("td").first().text(DateTimeHelper.getFormattedDateTime(test.getStartedTime(), LogSettings.getLogDateTimeFormat()));
            trTest.select(".category-link").first().text(test.getName()).attr("extentId", test.getId().toString());
            trTest.select(".label").first().text(test.getStatus().toString()).addClass(test.getStatus().toString());
            
            divCat.select("table").first().appendChild(trTest);
            
            // counts
            int pass = divCat.select(".status.pass") != null ? divCat.select(".status.pass").size() : 0;
            int fail = divCat.select(".status.fail, .status.fatal") != null ? divCat.select(".status.fail, .status.fatal").size() : 0;
            int others = divCat.select(".status.warning, .status.error, .status.skip, .status.unknown") != null ? divCat.select(".status.warning, .status.error, .status.skip, .status.unknown").size() : 0;
            
            divCat.select(".cat-pass").first().text("Pass: " + pass);
            divCat.select(".cat-fail").first().text("Fail: " + fail);
            divCat.select(".cat-other").first().text("Others: " + others);
        }
    }
}
