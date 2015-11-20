using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    public class Icon
    {
        public static string GetIcon(LogStatus Status)
        {
            switch (Enum.GetName(typeof(LogStatus), Status).ToLower())
            {
                case "fail": return "mdi-navigation-cancel";
                case "fatal": return "mdi-navigation-cancel";
                case "error": return "mdi-alert-error";
                case "warning": return "mdi-alert-warning";
                case "pass": return "mdi-action-check-circle";
                case "info": return "mdi-action-info-outline";
                case "skip": return "mdi-av-skip-next";
                default: return "mdi-action-help";
            }
        }
    }
}
