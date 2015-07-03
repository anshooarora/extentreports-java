namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;

    internal class Test
    {
        public List<Log> Logs;
        public List<ScreenCapture> ScreenCapture;
        public List<Screencast> Screencast;
        public string Name;
        public DateTime StartedTime;
        public DateTime EndedTime;
        public LogStatus Status;
        public string StatusMessage;
        public string Description;

        public Test()
        {
            Logs = new List<Log>();
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }
    }
}
