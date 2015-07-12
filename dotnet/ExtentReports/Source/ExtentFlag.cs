namespace RelevantCodes.ExtentReports.Source
{
    using System;

    internal class ExtentFlag
    {
        public static string GetPlaceHolder(string flag)
        {
            return "<!--%%" + flag.ToUpper() + "%%-->";
        }
    }
}
