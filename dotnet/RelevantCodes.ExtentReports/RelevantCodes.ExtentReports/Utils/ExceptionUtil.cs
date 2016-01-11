using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace RelevantCodes.ExtentReports.Utils
{
    internal class ExceptionUtil
    {
        public static string GetExceptionHeadline(Exception Ex)
        {
            var regex = new Regex("([\\w\\.]+)");
            var match = regex.Match(Ex.ToString());

            if (match.Success)
            {
                return match.Value;
            }

            return null;
        }
    }
}
