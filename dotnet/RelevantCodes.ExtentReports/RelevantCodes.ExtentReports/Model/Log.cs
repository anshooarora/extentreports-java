using System;

namespace RelevantCodes.ExtentReports.Model
{
    public class Log
    {
        public DateTime Timestamp { get; set; }
        public LogStatus LogStatus { get; set; }
        public string StepName { get; set; }
        public string Details { get; set; }
    }
}
