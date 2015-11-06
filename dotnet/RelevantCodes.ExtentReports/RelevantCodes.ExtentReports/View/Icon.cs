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
                case "fail": return "times-circle-o";
                case "fatal": return "exclamation-circle";
                case "error": return "exclamation-circle";
                case "warning": return "warning";
                case "pass": return "check-circle-o";
                case "info": return "info-circle";
                case "skip": return "chevron-circle-right";
                default: return "question";
            }
        }
    }
}
