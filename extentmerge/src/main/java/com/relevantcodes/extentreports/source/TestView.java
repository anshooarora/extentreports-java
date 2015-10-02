package com.relevantcodes.extentreports.source;

public class TestView {
	public static String getSource() {
		return "<div class='col s5 hide report-view'>" +
					"<span class='hide report-date' id=''></span>" +
		            "<div class='card-panel filters'>" +
		                "<div class='input-field no-margin-v'>" +
		                    "<input id='searchTests' type='text' class='validate'>" +
		                    "<label class='active' for='searchTests'>Search Tests..</label>" +
		                "</div>" +
		                "<div class='row'>" +
		                    "<div class='col s6'>" +
		                        "<div class='input-field tests-toggle'>" +
		                            "<select>" +
		                                "<option value='0' selected>Choose your option</option>" +
		                                "<option value='1'>Pass</option>" +
		                                "<option value='2'>Fatal</option>" +
		                                "<option value='3'>Fail</option>" +
		                                "<option value='4'>Error</option>" +
		                                "<option value='5'>Warning</option>" +
		                                "<option value='6'>Skip</option>" +
		                                "<option value='7'>Unknown</option>" +
		                                "<option value='8'>Clear Filters</option>" +
		                            "</select>" +
		                            "<label>Filter By Status</label>" +
		                        "</div>" +
		                    "</div>" +
		                    "<div class='col s6'>" +
		                        "<div class='input-field category-toggle'>" +
		                            "<select disabled>" +
		                                "<option value='0' selected>Choose your option</option>" +
		                                "<option value='8'>Clear Filters</option>" +
		                            "</select>" +
		                            "<label>Filter By Category</label>" +
		                        "</div>" +
		                    "</div>" +
		                "</div>" +
		            "</div>" +
		            "<div class='card-panel no-padding-h no-padding-v'>" +
		                "<div class='wrapper'>" +
		                    "<ul class='test-collection'>" +
		                    "</ul>" +
		                "</div>" +
		            "</div>" +
		        "</div>";
	}
}
