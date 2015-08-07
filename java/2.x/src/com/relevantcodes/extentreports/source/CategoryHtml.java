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
		return "<option value='<!--%%TESTCATEGORYU%%-->'><!--%%TESTCATEGORY%%--></option>";		
	}
	
	public static String getCategoryViewSource() {
		return "<div class='col s12 m12 l12'>" +
					"<div class='card-panel category-view'>" +
						"<div class='category-header test-attributes'>"
							+ "<span class='category'><!--%%CATEGORYVIEWNAME%%--></span>"
							+ "<div class='category-status right'>"
								+ "<span class='cat-pass'>PASS: </span>"
								+ "<span class='cat-fail'>FAIL: </span>"
								+ "<span class='cat-other'>OTHER: </span>"
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
		return "<tr>" +
				"<td><!--%%CATEGORYVIEWTESTRUNTIME%%--></td>" +
				"<td><span class='category-link'><!--%%CATEGORYVIEWTESTNAME%%--></span></td>" +
				"<td><span class='label <!--%%CATEGORYVIEWTESTSTATUS%%-->'><!--%%CATEGORYVIEWTESTSTATUS%%--></span></td>" +
			"</tr>";
	}
}
