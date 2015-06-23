namespace RelevantCodes.ExtentReports.Source
{
    using System;
    using System.Collections.Generic;

    internal class Icon
    {
        private static Dictionary<LogStatus, string> icon = new Dictionary<LogStatus, string>();

        public static void Override(LogStatus LogStatus, string Icon)
        {
            icon.Add(LogStatus, Icon);
        }

        public static string GetIcon(LogStatus LogStatus)
        {
            if (icon.ContainsKey(LogStatus))
                return icon[LogStatus];

            switch (LogStatus.ToString().ToUpper())
            {
                case "fail":
                    return "times-circle-o";
                case "error": 
                    return "exclamation-circle";
                case "fatal": 
                    return "exclamation-circle";
                case "pass": 
                    return "check-circle-o";
                case "info": 
                    return "info-circle";
                case "warning": 
                    return "warning";
                case "skip": 
                    return "chevron-circle-right";
                default:
                    return "question";
            }            
        }
    }
}
