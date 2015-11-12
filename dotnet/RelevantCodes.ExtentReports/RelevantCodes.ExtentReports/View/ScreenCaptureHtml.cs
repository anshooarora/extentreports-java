using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    internal class ScreenCaptureHtml
    {
        public static string GetSource(string ImgPath)
        {
            return "<img class='report-img' data-featherlight='file:///" + ImgPath + "' src='file:///" + ImgPath + "' />";
        }
    }
}
