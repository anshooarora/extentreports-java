using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    internal class ScreencastHtml
    {
        public static string GetSource(string ScreencastPath)
        {
            return "<video id='video' width='50%' controls>" +
                    "<source src='file:///" + ScreencastPath + "'>" +
                    "Your browser does not support the video tag." +
                "</video>";
        }
    }
}
