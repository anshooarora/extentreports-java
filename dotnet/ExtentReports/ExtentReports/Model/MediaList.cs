namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;

    internal class MediaList
    {
        public List<ScreenCapture> ScreenCapture;
        public List<Screencast> Screencast;

        public MediaList()
        {
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }
    }
}
