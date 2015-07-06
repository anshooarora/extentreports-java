namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Source;

    internal class CategoryOptionBuilder
    {
        internal static string build(List<string> Categories)
        {
            string source = "";

            Categories.Sort();

            foreach (string c in Categories)
            {
                source += CategoryFilterHtml.GetOptionSource()
                    .Replace(ExtentFlag.GetPlaceHolder("testCategory"), c)
					.Replace(ExtentFlag.GetPlaceHolder("testCategoryU"), c.ToLower().Replace(" ", ""));
            }

            return source;
        }
    }
}
