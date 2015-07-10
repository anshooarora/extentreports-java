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

            foreach (Category c in Categories)
                list.Add(c.GetName());

            list.Sort();

            foreach (string c in list)
            {
                source += CategoryFilterHtml.GetOptionSource()
                    .Replace(ExtentFlag.GetPlaceHolder("testCategory"), c)
					.Replace(ExtentFlag.GetPlaceHolder("testCategoryU"), c.ToLower().Replace(" ", ""));
            }

            return source;
        }
    }
}
