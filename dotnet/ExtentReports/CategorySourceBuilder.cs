// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Model;
    using Source;

    internal class CategorySourceBuilder {
        public static string buildOptions(List<string> Categories) {
            string source = "";

            Categories.Sort();
        
            foreach (string c in Categories) {
                source += CategoryHtml.GetOptionSource()
                        .Replace(ExtentFlag.GetPlaceHolder("testCategory"), c)
                        .Replace(ExtentFlag.GetPlaceHolder("testCategoryU"), c.ToLower().Replace(" ", ""));
            }
        
            return source;
        }
    }
}
