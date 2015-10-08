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
        return "<option class='cat-option'></option>";        
    }
    
    public static String getCategoryViewSource() {
		return "<li class='category-item displayed'>" +
				"<div class='cat-head'>" +
					"<span class='category-name'></span>" +
				"</div>" +
				"<div class='category-status-counts'>" +
					"<span class='cat-pass label'></span>" +
					"<span class='cat-fail label'></span>" +
					"<span class='cat-other label'></span>" +
				"</div>" +
				"<div class='cat-body'>" +
					"<div class='category-status-counts'>" +
						"<span class='cat-pass label'></span>" +
						"<span class='cat-fail label'></span>" +
						"<span class='cat-other label'></span>" +
					"</div>" +
					"<div class='cat-tests'>" +
						"<table class='bordered'>" +
							"<thead>" +
								"<tr>" +
									"<th>Run Date</th>" +
									"<th>Test Name</th>" +
									"<th>Status</th>" +
								"</tr>" +
							"</thead>" +
						"</table>" +
					"</div>" +
				"</div>"+ 
			"</li>";
    }
    
    public static String getCategoryViewTestSource() {
        return "<table><tr>" +
                "<td><!--%%CATEGORYVIEWTESTRUNTIME%%--></td>" +
                "<td><span class='category-link linked'><!--%%CATEGORYVIEWTESTNAME%%--></span></td>" +
                "<td><div class='status label capitalize '><!--%%CATEGORYVIEWTESTSTATUS%%--></div></td>" +
            "</tr></table>";
    }
}
