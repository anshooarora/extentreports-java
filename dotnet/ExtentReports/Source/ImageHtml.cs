namespace RelevantCodes.ExtentReports.Source
{
    using System;

    internal class ImageHtml
    {
        public static string GetSource(String ImgPath)
        {
            return "<img class='report-img' data-featherlight='file:///" + ImgPath + "' src='file:///" + ImgPath + "' />";
        }
    }
}
