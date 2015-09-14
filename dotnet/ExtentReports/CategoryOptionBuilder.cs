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

    internal class CategoryOptionBuilder
    {
        internal static string Build(List<TestAttribute> Categories)
        {
            string source = "";

            var list = new List<string>();
            
            Categories
                .ToList()
                .ForEach(c => list.Add(c.GetName()));
            
            list.Sort();

            var catParams = new string[] { 
                ExtentFlag.GetPlaceHolder("testCategory"), 
                ExtentFlag.GetPlaceHolder("testCategoryU") 
            };

            list.ForEach(c =>
            {
                var catValues = new string[] { 
                    c,
                    c.ToLower().Replace(" ", "")
                };

                source += SourceBuilder.Build(CategoryFilterHtml.GetOptionSource(), catParams, catValues);
            });

            return source;
        }
    }
}
