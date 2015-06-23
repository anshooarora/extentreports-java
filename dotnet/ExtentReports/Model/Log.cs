namespace RelevantCodes.ExtentReports.Model
{
    using System;

    internal class Log
    {
        public DateTime Timestamp;
        public LogStatus LogStatus;
        public string StepName;
        public string Details;
    }
}
