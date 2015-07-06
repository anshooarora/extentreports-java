namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;

    internal class Test
    {
        public bool HasEnded = false;
        public DateTime StartedTime;
        public DateTime EndedTime; 
        public List<TestAttribute> CategoryList;
        public List<TestAttribute> AuthorList;
        public List<Log> Logs;
        public List<ScreenCapture> ScreenCapture;
        public List<Screencast> Screencast;
        public LogStatus Status;
        public string Description;
        public string InternalWarning;
        public string StatusMessage;
        public string Name;

        public Test()
        {
            InternalWarning = "";

            CategoryList = new List<TestAttribute>();
            AuthorList = new List<TestAttribute>();
            Logs = new List<Log>();
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }
    }
}
