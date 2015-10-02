/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.source;

public class TestHtml {
    public static String getSource(int cols) {
        String colStepName = "";
        
        if (cols == 4) {
            colStepName = "<th>StepName</th>";
        }
        
        return "<li class='collection-item test displayed active'>" +
					"<div class='test-head'>" +
						"<span class='test-name'></span>" +
						"<span class='test-status right label capitalize'></span>" +
						"<span class='category-assigned hide'></span>" +
					"</div>" +
					"<div class='test-body'>" +
					"<div class='test-info'>" +
						"<span title='Test started time' class='test-started-time label green lighten-2 text-white'></span>" +
	                    "<span title='Test ended time' class='test-ended-time label red lighten-2 text-white'></span>" +
	                    "<span title='Time taken to finish' class='test-time-taken label blue-grey lighten-3 text-white'></span>" +
	                "</div>" +
						"<div class='test-desc'></div>" +
						"<div class='test-attributes'>" +
							"<div class='categories'></div>" +
						"</div>" +
						"<div class='test-steps'>" +
							"<table class='bordered table-results'>" +
								"<thead>" +
									"<tr>" +
										"<th>Status</th>" +
										"<th>Timestamp</th>" +
										colStepName +
										"<th>Details</th>" +
									"</tr>" +
								"</thead>" +
								"<tbody>" +
								"</tbody>" +
							"</table>" +
							"<ul class='collapsible node-list' data-collapsible='accordion'>" +
							"</ul>" +
						"</div>" +
					"</div>" +
				"</li>";
    }
    
    public static String getSourceQuickView() {
        return "<table><tr>" +
                    "<td><span class='quick-view-test linked'><!--%%TESTNAME%%--></span><!--%%TESTWARNINGS%%--></td>" +
                    "<td><!--%%CURRENTTESTPASSEDCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTFAILEDCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTFATALCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTERRORCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTWARNINGCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTINFOCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTSKIPPEDCOUNT%%--></td>" +
                    "<td><!--%%CURRENTTESTUNKNOWNCOUNT%%--></td>" +
                    "<td><div class='status label'><!--%%CURRENTTESTRUNSTATUSU%%--></div></td>" +
                "</tr></table>";
    }
    
    public static String getNodeSource(int cols) {
        String colStepName = "";
        
        if (cols == 4) {
            colStepName = "<th>StepName</th>";
        }
        
        return "<li>" +
                    "<div class='collapsible-header test-node'>" +
                        "<div class='right test-info'>" +
                            "<span title='Test started time' class='test-started-time label green lighten-2 text-white'></span>" +
                            "<span title='Test ended time' class='test-ended-time label red lighten-2 text-white'></span>" +
                            "<span title='Time taken to finish' class='test-time-taken label blue-grey lighten-2 text-white'></span>" +
                            "<span class='test-status label capitalize'></span>" +
                        "</div>" +
                        "<div class='border-bullet'></div>" +
                        "<div class='test-node-name'></div>" +
                    "</div>" +
                    "<div class='collapsible-body'>" +
                        "<div class='test-steps'>" +
                            "<table class='bordered table-results'>" +
                                "<thead>" +
                                    "<tr>" +
                                        "<th>Status</th>" +
                                        "<th>Timestamp</th>" +
                                        colStepName +
                                        "<th>Details</th>" +
                                    "</tr>" +
                                "</thead>" +
                                "<tbody>" +
                                "</tbody>" +
                            "</table>" +
                        "</div>" +
                    "</div>" +
                "</li>";
    }
    
    public static String getCategorySource() {
        return "<span class='category text-white'></span>";
    }
    
    public static String getWarningSource(String warning) {
        if (warning == "") {
            return "";
        }
            
        return "<span class='test-warning tooltipped' data-tooltip='" + warning + "' data-position='top'><i class='fa fa-info' alt='" + warning + "' title='" + warning + "'></i></span>";
    }
}
