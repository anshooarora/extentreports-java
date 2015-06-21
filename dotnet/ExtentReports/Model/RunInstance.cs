namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;

    internal class RunInstance
    {
        public RunInfo RunInfo;
        public List<Test> Tests;
        public SystemProperties SystemInfo;
        public List<ScreenCapture> ScreenCapture;

        public void init()
        {
            Tests = new List<Test>();
            ScreenCapture = new List<ScreenCapture>();
        }
    }
}
