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
        
        return "<div class='test-section'>" +
                    "<div class='col s12'>" +
                    "<div class='test card-panel <!--%%TESTSTATUS%%-->'>" +
                        "<div class='test-head'>" +
                            "<div class='test-name left'>" +
                                "<!--%%TESTNAME%%-->" +
                            "</div>" +
                            "<div class='right'>" +
                                "<span alt='Test started time' title='Test started time' class='test-started-time label'><!--%%TESTSTARTTIME%%--></span>" +
                                "<span alt='Test ended time' title='Test ended time' class='test-ended-time label'><!--%%TESTENDTIME%%--></span>" +
                                "<span class='test-status label <!--%%TESTSTATUS%%-->'><!--%%TESTSTATUS%%--></span>" +
                            "</div>" +
                            "<div class='test-desc' <!--%%DESCVIS%%-->>" +
                                "<span><!--%%TESTDESCRIPTION%%--></span>" +
                            "</div>" +
                        "</div>" +
                        "<div class='test-body'>" +
                            "<table class='bordered table-results'>" +
                                "<thead>" +
                                    "<tr>" +
                                        "<th>Timestamp</th>" +
                                        "<th>Status</th>" +
                                        colStepName +
                                        "<th>Details</th>" +
                                    "</tr>" +
                                "</thead>" +
                            "<tbody>" +
                                "<!--%%STEP%%-->" +
                            "</tbody>" +
                            "</table>" +
                        "</div>" +
                    "</div>" +
                "</div>" +
            "</div>";
    }
    
    public static String getSourceQuickView() {
    	return "<tr>" +
	    			"<td><span class='quick-view-test'><!--%%TESTNAME%%--></span></td>" +
	    			"<td><!--%%CURRENTTESTPASSEDCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTFAILEDCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTFATALCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTERRORCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTWARNINGCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTINFOCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTSKIPPEDCOUNT%%--></td>" +
	    			"<td><!--%%CURRENTTESTUNKNOWNCOUNT%%--></td>" +
	    			"<td><span class='status <!--%%CURRENTTESTRUNSTATUS%%--> label'><!--%%CURRENTTESTRUNSTATUSU%%--></span></td>" +
	    		"</tr>";
    }
}
