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

    internal class CategoryFilterHtml
    {
        public static string GetOptionSource()
        {
            return "<option value='<!--%%TESTCATEGORYU%%-->'><!--%%TESTCATEGORY%%--></option>";
        }
    }
}
