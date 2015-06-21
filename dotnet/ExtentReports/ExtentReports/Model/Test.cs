namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;

    internal class Test
    {
        public List<Log> Log;
        public List<ScreenCapture> ScreenCapture;
        public List<Screencast> Screencast;
        public string Name;
        public DateTime StartedAt;
        public DateTime EndedAt;
        public LogStatus Status;
        public string StatusMessage;
        public string Description;

        public Test()
        {
            Log = new List<Log>();
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }
    }
}
