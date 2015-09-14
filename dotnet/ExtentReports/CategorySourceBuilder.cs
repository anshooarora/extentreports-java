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

            var catFlags = new string[] { 
                ExtentFlag.GetPlaceHolder("testCategory"), 
                ExtentFlag.GetPlaceHolder("testCategoryU") 
            };

            Categories.ForEach(c =>
            {
                var catValues = new string[] { 
                    c, 
                    c.ToLower().Replace(" ", "") 
                };

                source += SourceBuilder.Build(CategoryHtml.GetOptionSource(), catFlags, catValues);
            });
        
            return source;
        }
    }
}
