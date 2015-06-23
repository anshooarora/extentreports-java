namespace RelevantCodes.ExtentReports.Source
{
    using System;

    internal class ExtentFlag
    {
        public static string GetPlaceHolder(String flag)
        {
            return "<!--%%" + flag.ToUpper() + "%%-->";
        }
    }
}
