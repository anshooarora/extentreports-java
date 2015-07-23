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
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;


    internal class CategoryHtml
    {
        public static string GetOptionSource()
        {
            return "<option value='<!--%%TESTCATEGORYU%%-->'><!--%%TESTCATEGORY%%--></option>";
        }

        public static string GetCategoryViewSource()
        {
            return "<div class='col s12 m12 l12'>" +
                        "<div class='card-panel category-view'>" +
                            "<div class='category-header test-attributes'><span class='category'><!--%%CATEGORYVIEWNAME%%--></span></div>" +
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

        public static string GetCategoryViewTestSource()
        {
            return "<tr>" +
                    "<td><!--%%CATEGORYVIEWTESTRUNTIME%%--></td>" +
                    "<td><span class='category-link'><!--%%CATEGORYVIEWTESTNAME%%--></span></td>" +
                    "<td><span class='label <!--%%CATEGORYVIEWTESTSTATUS%%-->'><!--%%CATEGORYVIEWTESTSTATUS%%--></span></td>" +
                "</tr>";
        }
    }
}
