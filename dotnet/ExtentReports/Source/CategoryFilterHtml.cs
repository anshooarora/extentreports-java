using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports.Source
{
    internal class CategoryFilterHtml
    {
        public static string GetOptionSource()
        {
            return "<option value='<!--%%TESTCATEGORYU%%-->'><!--%%TESTCATEGORY%%--></option>";
        }
    }
}
