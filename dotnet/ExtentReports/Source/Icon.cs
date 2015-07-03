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
                case "FAIL":
                    return "times-circle-o";
                case "ERROR": 
                    return "exclamation-circle";
                case "FATAL": 
                    return "exclamation-circle";
                case "PASS": 
                    return "check-circle-o";
                case "INFO": 
                    return "info-circle";
                case "WARNING": 
                    return "warning";
                case "SKIP": 
                    return "chevron-circle-right";
                default:
                    return "question";
            }            
        }
    }
}
