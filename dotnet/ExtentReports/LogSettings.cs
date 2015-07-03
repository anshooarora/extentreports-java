namespace RelevantCodes.ExtentReports
{
    internal abstract class LogSettings
    {
        protected static string LogTimeFormat = "HH:mm:ss";
        protected static string LogDateFormat = "yyyy-MM-dd";
        protected static string LogDateTimeFormat = LogDateFormat + " " + LogTimeFormat;
    }
}
