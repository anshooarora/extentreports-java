namespace RelevantCodes.ExtentReports.Source
{
    using System;

    class ScreencastHtml
    {
        public static string GetSource(String ScreencastPath)
        {
            return "<video id='video' src='file:///" + ScreencastPath + "' width='50%' controls />";
        }
    }
}
