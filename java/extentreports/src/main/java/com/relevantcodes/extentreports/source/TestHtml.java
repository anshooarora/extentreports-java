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
        
        return "<div class='test card-panel displayed'>" +
            "<div class='test-head'>" +
                "<div class='right test-info'>" +
                    "<span title='Test started time' class='test-started-time label'></span>" +
                    "<span title='Test ended time' class='test-ended-time label'></span>" +
                    "<span title='Time taken to finish' class='test-time-taken label'></span>" +
                    "<span class='test-status label'></span>" +
                "</div>" +
                "<div class='test-name'>" +
                "</div>" +
                "<div class='test-desc'>" +
                    "<span></span>" +
                "</div>" +
            "</div>" +
            "<div class='test-attributes'>" +
                "<div class='categories'>" +
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
                "</tbody>" +
                "</table>" +
                "<ul class='collapsible node-list' data-collapsible='accordion'>" +
                "</ul>" +
            "</div>" +
        "</div>";
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
                            "<span alt='Test started time' title='Test started time' class='test-started-time label'></span>" +
                            "<span alt='Test ended time' title='Test ended time' class='test-ended-time label'></span>" +
                            "<span alt='Time taken to finish' title='Time taken to finish' class='test-time-taken label'></span>" +
                            "<span class='test-status label'></span>" +
                        "</div>" +
                        "<div class='test-node-name'></div>" +
                    "</div>" +
                    "<div class='collapsible-body'>" +
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
                                "</tbody>" +
                            "</table>" +
                        "</div>" +
                    "</div>" +
                "</li>";
    }
    
    public static String getCategorySource() {
        return "<span class='category'></span>";
    }
    
    public static String getWarningSource(String warning) {
        if (warning == "") {
            return "";
        }
            
        return "<span class='test-warning tooltipped' data-tooltip='" + warning + "' data-position='top'><i class='fa fa-info' alt='" + warning + "' title='" + warning + "'></i></span>";
    }
}
