// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports.Source
{
    using System;

    internal class TestHtml
    {
        public static string GetSource(int ColumnCount)
        {
            string colStepName = "";

            if (ColumnCount == 4)
            {
                colStepName = "<th>StepName</th>";
            }

            return @"<div class='test-section'>
                        <div class='col s12'>
                            <div class='test card-panel <!--%%TESTSTATUS%%-->'>
                                <div class='test-head'>
                                    <div class='text-info right'>
                                        <span alt='Test started time' title='Test started time' class='test-started-time label'><!--%%TESTSTARTTIME%%--></span>
                                        <span alt='Test ended time' title='Test ended time' class='test-ended-time label'><!--%%TESTENDTIME%%--></span>
                                        <span alt='Time taken to finish' title='Time taken to finish' class='test-time-taken label'><!--%%TESTTIMETAKEN%%--></span>
                                        <span class='test-status label <!--%%TESTSTATUS%%-->'><!--%%TESTSTATUS%%--></span>
                                    </div>
                                    <div class='test-name'><!--%%TESTNAME%%--><!--%%TESTWARNINGS%%--></div>
                                    <div class='test-desc' <!--%%DESCVIS%%-->>
                                        <span><!--%%TESTDESCRIPTION%%--></span>
                                    </div>
                                </div>
                                <div class='test-attributes'>
                                    <div class='categories'>
                                        <!--%%TESTCATEGORY%%-->
                                    </div>
                                </div>
                                <div class='test-body'>
                                    <table class='bordered table-results'>
                                        <thead>
                                            <tr>
                                                <th>Timestamp</th>
                                                <th>Status</th>" + 
                                                colStepName +
                                                @"<th>Details</th>
                                            </tr>
                                        </thead>
                                    <tbody>
                                        <!--%%STEP%%-->
                                    </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>";
        }

        public static string GetQuickSummarySource()
        {
            return @"<tr>
	    			    <td><span class='quick-view-test'><!--%%TESTNAME%%--></span><!--%%TESTWARNINGS%%--></td>
	    			    <td><!--%%CURRENTTESTPASSEDCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTFAILEDCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTFATALCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTERRORCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTWARNINGCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTINFOCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTSKIPPEDCOUNT%%--></td>
	    			    <td><!--%%CURRENTTESTUNKNOWNCOUNT%%--></td>
	    			    <td><span class='status <!--%%CURRENTTESTRUNSTATUS%%--> label'><!--%%CURRENTTESTRUNSTATUSU%%--></span></td>
	    		    </tr>";
        }

        public static string GetCategorySource()
        {
            return "<span class='category'><!--%%CATEGORY%%--></span>";
        }

        public static string GetWarningSource(string Warning)
        {
            if (Warning == "")
            {
                return "";
            }

            return "<span class='test-warning tooltipped' data-tooltip='" + Warning + "' data-position='top'><i class='fa fa-info' alt='" + Warning + "' title='" + Warning + "'></i></span>";
        }
    }
}
