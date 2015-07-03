namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;

    using Model;
    using Source;
    using Support;
    
    internal class ReportInstance
    {
        private DisplayOrder displayOrder;
        private string filePath;
        private MediaList mediaList;
        private string quickSummarySource = "";
        private RunInfo runInfo;
        private string extentSource = null;
        private string testSource = "";
        private object locker = new object();

        public void AddTest(Test Test)
        {
            Test.EndedTime = DateTime.Now;

            if (Test.ScreenCapture.Count > 0)
            {
                mediaList.ScreenCapture.AddRange(Test.ScreenCapture);
            }

            if (Test.Screencast.Count > 0)
            {
                mediaList.Screencast.AddRange(Test.Screencast);
            }

            AddTest(TestBuilder.GetTestSource(Test));
            AddQuickTestSummary(TestBuilder.GetQuickSummary(Test));
        }

        public void Initialize(string FilePath, bool ReplaceExisting, DisplayOrder DisplayOrder)
        {
            this.displayOrder = DisplayOrder;
            this.filePath = FilePath;

            lock (locker)
            {
                if (!File.Exists(FilePath))
                {
                    ReplaceExisting = true;
                }

                if (extentSource != null)
                {
                    return;
                }

                if (ReplaceExisting)
                {
                    extentSource = Standard.GetSource();
                }
                else
                {
                    extentSource = File.ReadAllText(FilePath);
                }

                runInfo = new RunInfo();
                runInfo.StartedTime = DateTime.Now;

                mediaList = new MediaList();
            }
        }

        public void Terminate(SystemInfo SystemInfo)
        {
            if (testSource == "")
            {
                return;
            }

            runInfo.EndedTime = DateTime.Now;

            Dictionary<string, string> n = SystemInfo.GetInfo();
            UpdateSystemInfo(SystemInfo.GetInfo());
            UpdateSuiteExecutionTime();
            UpdateMediaList();

            SystemInfo.Clear();

            if (this.displayOrder == DisplayOrder.OldestFirst)
            {
                lock (locker)
                {
                    extentSource = extentSource.Replace(ExtentFlag.GetPlaceHolder("test"), testSource + ExtentFlag.GetPlaceHolder("test"))
                                            .Replace(ExtentFlag.GetPlaceHolder("quickTestSummary"), quickSummarySource + ExtentFlag.GetPlaceHolder("quickTestSummary"));
                }
            }
            else
            {
                lock (locker)
                {
                    extentSource = extentSource.Replace(ExtentFlag.GetPlaceHolder("test"), ExtentFlag.GetPlaceHolder("test") + testSource)
                                            .Replace(ExtentFlag.GetPlaceHolder("quickTestSummary"), ExtentFlag.GetPlaceHolder("quickTestSummary") + quickSummarySource);
                }
            }

            using (var file = new StreamWriter(filePath))
            {
                TextWriter.Synchronized(file).WriteLine(extentSource);
            }
            //TextWriter.Synchronized(new StreamWriter(filePath)).Write(extentSource);

            testSource = "";
            quickSummarySource = "";
        }

        private void UpdateSuiteExecutionTime()
        {
            string[] flags = { ExtentFlag.GetPlaceHolder("suiteStartTime"), ExtentFlag.GetPlaceHolder("suiteEndTime") };
            string[] values = { runInfo.StartedTime.ToString(), runInfo.EndedTime.ToString() };

            lock (locker)
            {
                extentSource = SourceBuilder.Build(extentSource, flags, values);
            }
        }

        private void UpdateSystemInfo(Dictionary<string, string> SystemInfo)
        {
            if (extentSource.IndexOf(ExtentFlag.GetPlaceHolder("systemInfoApplied")) > 0)
            {
                return;
            }

            if (SystemInfo.Count > 0)
            {
                string systemSource = SourceBuilder.GetSource(SystemInfo) + ExtentFlag.GetPlaceHolder("systemInfoApplied");
                string[] flags = new string[] { ExtentFlag.GetPlaceHolder("systemInfoView") };
                string[] values = new string[] { systemSource + ExtentFlag.GetPlaceHolder("systemInfoView") };

                lock (locker)
                {
                    extentSource = SourceBuilder.Build(extentSource, flags, values);
                }
            }
        }

        private void UpdateMediaList()
        {
            string mediaSource = MediaViewBuilder.GetSource(mediaList.ScreenCapture);
            string[] flags = new string[] { ExtentFlag.GetPlaceHolder("imagesView") };
            string[] values = new string[] { mediaSource + ExtentFlag.GetPlaceHolder("imagesView") };
            
            if (values[0].IndexOf("No media") >= 0)
            {
                lock (locker)
                {
                    extentSource = SourceBuilder.Build(extentSource, flags, values);
                    mediaList.ScreenCapture.Clear();
                }
            }

            mediaSource = MediaViewBuilder.GetSource(mediaList.Screencast);
            flags = new String[] { ExtentFlag.GetPlaceHolder("videosView") };
            values = new String[] { mediaSource + ExtentFlag.GetPlaceHolder("videosView") };
            
            if (values[0].IndexOf("No media") >= 0)
            {
                lock (locker)
                {
                    extentSource = SourceBuilder.Build(extentSource, flags, values);
                    mediaList.Screencast.Clear();                    
                }
            }
        }

        private void AddTest(string TestSource)
        {
            if (displayOrder == DisplayOrder.OldestFirst)
            {
                testSource += TestSource;
            }
            else
            {
                testSource = TestSource + testSource;
            }
        }

        private void AddQuickTestSummary(string Summary)
        {
            if (displayOrder == DisplayOrder.OldestFirst)
            {
                quickSummarySource += Summary;
            }
            else
            {
                quickSummarySource = Summary + quickSummarySource;
            }
        }

        public ReportInstance() { }
    }
}
