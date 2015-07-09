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
    
    public class ReportInstance
    {
        private bool terminated = false;
        private CategoryList categoryList;
        private DisplayOrder displayOrder;
        private int infoWrite = 0;
        private string filePath;
        private MediaList mediaList;
        private string quickSummarySource = "";
        private RunInfo runInfo;
        private string extentSource = null;
        private string testSource = "";
        private object sourcelock = new object();

        internal void AddTest(Test Test)
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
            AddCategories(Test);
        }

        private void AddCategories(Test test) {
            foreach (TestAttribute t in test.CategoryList)
            {
                if (!categoryList.Categories.Contains(t.GetName()))
                {
                    categoryList.Categories.Add(t.GetName());
                }
            }
        }

        internal void Initialize(string FilePath, bool ReplaceExisting, DisplayOrder DisplayOrder)
        {
            this.displayOrder = DisplayOrder;
            this.filePath = FilePath;

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
                lock (sourcelock)
                {
                    extentSource = Standard.GetSource();
                }
            }
            else
            {
                lock (sourcelock)
                {
                    extentSource = File.ReadAllText(FilePath);
                }
            }

            runInfo = new RunInfo();
            runInfo.StartedTime = DateTime.Now;
            
            categoryList = new CategoryList();
            mediaList = new MediaList();
        }

        internal void WriteAllResources(List<ExtentTest> TestList, SystemInfo SystemInfo)
        {
            if (terminated)
            {
                throw new IOException("Stream closed");
            }

            if (SystemInfo != null)
                UpdateSystemInfo(SystemInfo.GetInfo());

            if (testSource == "")
                return;

            runInfo.EndedTime = DateTime.Now;

            UpdateCategoryList();
            UpdateSuiteExecutionTime();
            UpdateMediaList();

            if (this.displayOrder == DisplayOrder.OldestFirst)
            {
                lock (sourcelock)
                {
                    extentSource = extentSource.Replace(ExtentFlag.GetPlaceHolder("test"), testSource + ExtentFlag.GetPlaceHolder("test"))
                                            .Replace(ExtentFlag.GetPlaceHolder("quickTestSummary"), quickSummarySource + ExtentFlag.GetPlaceHolder("quickTestSummary"));
                }
            }
            else
            {
                lock (sourcelock)
                {
                    extentSource = extentSource.Replace(ExtentFlag.GetPlaceHolder("test"), ExtentFlag.GetPlaceHolder("test") + testSource)
                                            .Replace(ExtentFlag.GetPlaceHolder("quickTestSummary"), ExtentFlag.GetPlaceHolder("quickTestSummary") + quickSummarySource);
                }
            }

            using (var file = new StreamWriter(filePath))
            {
                TextWriter.Synchronized(file).WriteLine(extentSource);
            }

            testSource = "";
            quickSummarySource = "";
        }

        internal void Terminate(List<ExtentTest> TestList)
        {
            if (TestList != null)
            {
                foreach (ExtentTest t in TestList)
                {
                    if (!t.GetTest().HasEnded)
                    {
                        t.GetTest().InternalWarning = "Test did not end safely because endTest() was not called. There may be errors.";
                        AddTest(t.GetTest());
                    }
                }
            }

            WriteAllResources(null, null);

            extentSource = "";
            categoryList = null;
            runInfo = null;

            terminated = true;
        }

        private void UpdateCategoryList() {
            String catsAdded = "";
            String c = "";
            
            for (int ix = categoryList.Categories.Count - 1; ix > -1; ix--)
            {
                if (extentSource.IndexOf(ExtentFlag.GetPlaceHolder("categoryAdded" + c)) > 0)
                {
                    categoryList.Categories.RemoveAt(ix);
                }
                else
                {
                    catsAdded += ExtentFlag.GetPlaceHolder("categoryAdded" + c);
                }
            }
        
            string source = CategoryOptionBuilder.build(categoryList.Categories);
        
            if (source != "") {
                lock (sourcelock) {
                    extentSource = extentSource
                            .Replace(ExtentFlag.GetPlaceHolder("categoryListOptions"), source + ExtentFlag.GetPlaceHolder("categoryListOptions"))
                            .Replace(ExtentFlag.GetPlaceHolder("categoryAdded"), catsAdded + ExtentFlag.GetPlaceHolder("categoryAdded"));
                }
            }
        }

        private void UpdateSuiteExecutionTime()
        {
            string[] flags = { ExtentFlag.GetPlaceHolder("suiteStartTime"), ExtentFlag.GetPlaceHolder("suiteEndTime") };
            string[] values = { runInfo.StartedTime.ToString(), runInfo.EndedTime.ToString() };

            lock (sourcelock)
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

                lock (sourcelock)
                {
                    extentSource = SourceBuilder.Build(extentSource, flags, values);
                }
            }
        }

        private void UpdateMediaList()
        {
            string mediaSource = MediaViewBuilder.GetSource(mediaList.ScreenCapture, "img");
            string[] flags = new string[] { ExtentFlag.GetPlaceHolder("imagesView") };
            string[] values = new string[] { mediaSource + ExtentFlag.GetPlaceHolder("imagesView") };

            if (!(infoWrite >= 1 && values[0].IndexOf("No media") >= 0))
            {
                lock (sourcelock)
                {
                    extentSource = SourceBuilder.Build(extentSource, flags, values);

                    if (mediaList.ScreenCapture.Count > 0)
                    {
                        try
                        {
                            string match = RegexMatcher.GetNthMatch(extentSource, ExtentFlag.GetPlaceHolder("objectViewNullImg") + ".*" + ExtentFlag.GetPlaceHolder("objectViewNullImg"), 0);
                            extentSource = extentSource.Replace(match, "");
                        }
                        catch { }
                    }

                    mediaList.ScreenCapture.Clear();
                }
            }

            mediaSource = MediaViewBuilder.GetSource(mediaList.Screencast, "vid");
            flags = new String[] { ExtentFlag.GetPlaceHolder("videosView") };
            values = new String[] { mediaSource + ExtentFlag.GetPlaceHolder("videosView") };

            if (!(infoWrite >= 1 && values[0].IndexOf("No media") >= 0))
            {
                lock (sourcelock)
                {
                    extentSource = SourceBuilder.Build(extentSource, flags, values);

                    if (mediaList.Screencast.Count > 0)
                    {
                        try
                        {
                            string match = RegexMatcher.GetNthMatch(extentSource, ExtentFlag.GetPlaceHolder("objectViewNullVid") + ".*" + ExtentFlag.GetPlaceHolder("objectViewNullVid"), 0);
                            extentSource = extentSource.Replace(match, "");
                        }
                        catch { }
                    }

                    mediaList.Screencast.Clear();                    
                }
            }

            infoWrite++;
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

        internal string Source
        {
            get
            {
                return extentSource;
            }
        }

        internal void UpdateSource(string NewSource)
        {
            lock (sourcelock)
            {
                extentSource = NewSource;
            }
        }

        public ReportInstance() { }
    }
}
