using System;
using System.Text.RegularExpressions;

using RelevantCodes.ExtentReports.Model;

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

        public static string GetExceptionHeadline(ExceptionInfo ExInfo)
        {
            return GetExceptionHeadline(ExInfo.Exception);
        }
    }
}
