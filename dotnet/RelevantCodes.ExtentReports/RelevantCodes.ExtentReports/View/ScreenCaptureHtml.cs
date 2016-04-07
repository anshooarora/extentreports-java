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
        
        /// <summary>
        /// Creates the image tag with base64 string as image source
        /// </summary>
        /// <param name="imgSrc">Base64 String</param>
        /// <returns> Image tag with base64 string as source</returns>
        public static String getBase64Source(String imgSrc)
        {
            return "<img class='report-img' data-featherlight='<img src=\"" + imgSrc + "\" />' " +
                    "src='" + imgSrc + "' />";
        }
    }
}
