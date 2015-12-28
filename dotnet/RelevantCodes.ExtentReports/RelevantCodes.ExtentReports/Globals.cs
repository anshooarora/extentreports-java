using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports
{
    internal static class Globals
    {
        public static string DateFormat
        {
            get
            {
                return "yyyy-MM-dd";
            }
        }

        public static string TimeFormat
        {
            get
            {
                return "HH:mm:ss";
            }
        }

        public static string Protocol
        {
            get
            {
                return "https";
            }
        }
    }
}
