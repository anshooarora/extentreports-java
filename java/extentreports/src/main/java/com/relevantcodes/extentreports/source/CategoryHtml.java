/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.source;

public class CategoryHtml {
    public static String getOptionSource() {
        return "<option><!--%%TESTCATEGORY%%--></option>";        
    }
    
    public static String getCategoryViewSource() {
        return "<div class='col s12 m12 l12'>" +
                    "<div class='card-panel category-view'>" +
                        "<div class='category-header test-attributes'>"
                            + "<span class='category'><!--%%CATEGORYVIEWNAME%%--></span>"
                            + "<div class='category-status right'>"
                                + "<span class='label cat-pass'>PASS: </span>"
                                + "<span class='label cat-fail'>FAIL: </span>"
                                + "<span class='label cat-other'>OTHER: </span>"
                            + "</div>"
                        + "</div>" +
                        "<table class='bordered'>" +
                            "<tr>" +
                                "<th>Run Date</th>" +
                                "<th>Test Name</th>" +
                                "<th>Status</th>" +
                            "</tr>" +
                            "<!--%%CATEGORYVIEWTESTDETAILS%%-->" +
                        "</table>" +
                    "</div>" +
                "</div>";
    }
    
    public static String getCategoryViewTestSource() {
        return "<table><tr>" +
                "<td><!--%%CATEGORYVIEWTESTRUNTIME%%--></td>" +
                "<td><span class='category-link linked'><!--%%CATEGORYVIEWTESTNAME%%--></span></td>" +
                "<td><div class='label '><!--%%CATEGORYVIEWTESTSTATUS%%--></div></td>" +
            "</tr></table>";
    }
}
