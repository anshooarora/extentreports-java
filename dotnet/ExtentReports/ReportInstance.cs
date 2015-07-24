// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

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
        private AttributeList categoryList;
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
            UpdateCategoryView(Test);
        }

        private void AddCategories(Test test) {
            foreach (TestAttribute t in test.CategoryList)
            {
                if (!categoryList.Contains((Category) t))
                {
                    categoryList.Categories.Add(t);
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
            
            categoryList = new AttributeList();
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
                    extentSource = SourceBuilder.BuildSimple(extentSource,
                        new string[] { ExtentFlag.GetPlaceHolder("test"), ExtentFlag.GetPlaceHolder("quickTestSummary") },
                        new string[] { testSource + ExtentFlag.GetPlaceHolder("test"), quickSummarySource + ExtentFlag.GetPlaceHolder("quickTestSummary") });
                }
            }
            else
            {
                lock (sourcelock)
                {
                    extentSource = SourceBuilder.BuildSimple(extentSource,
                        new string[] { ExtentFlag.GetPlaceHolder("test"), ExtentFlag.GetPlaceHolder("quickTestSummary") },
                        new string[] { ExtentFlag.GetPlaceHolder("test") + testSource, ExtentFlag.GetPlaceHolder("quickTestSummary") + quickSummarySource });
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
            string catsAdded = "";
            string c = "";
            var placeholder = "";

            for (int ix = categoryList.Categories.Count - 1; ix > -1; ix--)
            {
                c = categoryList.GetItem(ix);
                placeholder = ExtentFlag.GetPlaceHolder("categoryAdded" + c);

                if (extentSource.IndexOf(placeholder) == -1 && catsAdded.IndexOf(placeholder) == -1)
                {
                    catsAdded += ExtentFlag.GetPlaceHolder("categoryAdded" + c);
                }
                else
                {
                    categoryList.Categories.RemoveAt(ix);
                }
            }
        
            string source = CategoryOptionBuilder.Build(categoryList.Categories);
        
            if (source != "") 
            {
                lock (sourcelock) 
                {
                    extentSource = SourceBuilder.BuildSimple(extentSource,
                        new string[] { ExtentFlag.GetPlaceHolder("categoryListOptions"), ExtentFlag.GetPlaceHolder("categoryAdded") },
                        new string[] { source + ExtentFlag.GetPlaceHolder("categoryListOptions"), catsAdded + ExtentFlag.GetPlaceHolder("categoryAdded") });
                }
            }
        }

        private void UpdateCategoryView(Test test) {
            string s = "", testSource = "";
            string addedFlag = "";
            string[] sourceKeys = { ExtentFlag.GetPlaceHolder("categoryViewName"), 
                                      ExtentFlag.GetPlaceHolder("categoryViewTestDetails") };
            string[] testKeys = { ExtentFlag.GetPlaceHolder("categoryViewTestRunTime"), 
                                    ExtentFlag.GetPlaceHolder("categoryViewTestName"), 
                                    ExtentFlag.GetPlaceHolder("categoryViewTestStatus") };
            string[] testValues = { test.StartedTime.ToString(),
                                      test.Name, 
                                      test.Status.ToString().ToLower()};
        
            foreach (TestAttribute attr in test.CategoryList) {
                addedFlag = ExtentFlag.GetPlaceHolder("categoryViewTestDetails" + attr.GetName());
            
                if (!extentSource.Contains(addedFlag)) {
                    string[] sourceValues = { attr.GetName(), addedFlag };

                    s += SourceBuilder.BuildSimple(CategoryHtml.GetCategoryViewSource(), sourceKeys, sourceValues);
                    testSource = SourceBuilder.BuildSimple(CategoryHtml.GetCategoryViewTestSource(), testKeys, testValues);    
                    s = SourceBuilder.BuildSimple(s, new string[] { addedFlag }, new string[] { testSource + addedFlag });
                }
                else {
                    testSource = SourceBuilder.BuildSimple(CategoryHtml.GetCategoryViewTestSource(), testKeys, testValues);
                    
                    lock (sourcelock)
                    {
                        extentSource = SourceBuilder.BuildSimple(extentSource, new string[] { addedFlag }, new string[] { testSource + addedFlag });
                    }
                }
            }
        
            lock (sourcelock)
            {
                extentSource = SourceBuilder.BuildSimple(extentSource, 
                    new string[] { ExtentFlag.GetPlaceHolder("extentCategoryDetails") }, 
                    new string[] { s + ExtentFlag.GetPlaceHolder("extentCategoryDetails") });
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
            flags = new string[] { ExtentFlag.GetPlaceHolder("videosView") };
            values = new string[] { mediaSource + ExtentFlag.GetPlaceHolder("videosView") };

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
