namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Model;
    using Source;

    /// <summary>
    /// Defines a test toggle in the report
    /// </summary>
    public class ExtentTest
    {
        private Test test;
        private LogStatus runStatus = LogStatus.Unknown;

        /// <summary>
        /// Builds a test toggle in the report with the TestName
        /// </summary>
        /// <param name="TestName">Test name</param>
        /// <param name="Description">A short description of the test</param>
        internal ExtentTest(string TestName, string Description)
        {
            test = new Test();

            test.Name = TestName;
            test.Description = Description;
            test.StartedTime = DateTime.Now;
        }

        /// <summary>
        /// Logs events for the test
        /// </summary>
        /// <param name="LogStatus">LogStatus</param>
        /// <param name="StepName">Name of the step</param>
        /// <param name="Details">Step details</param>
        public void Log(LogStatus LogStatus, string StepName, string Details)
        {
            Log evt = new Log();

            evt.Timestamp = DateTime.Now;
            evt.LogStatus = LogStatus;
            evt.StepName = StepName;
            evt.Details = Details;

            test.Logs.Add(evt);

            TrackLastRunStatus(LogStatus);
        }

        /// <summary>
        /// Logs events for the test
        /// </summary>
        /// <param name="LogStatus">LogStatus</param>
        /// <param name="Details">Step details</param>
        public void Log(LogStatus LogStatus, string Details)
        {
            Log(LogStatus, "", Details);
        }

        /// <summary>
        /// Allows for adding a snapshot to the log event
        /// </summary>
        /// <param name="ImagePath">Path of the image</param>
        /// <returns>A formed HTML img tag with the supplied path</returns>
        public string AddScreenCapture(string ImagePath)
        {
            string screenCaptureHtml;

            if (IsPathRelative(ImagePath))
            {
                screenCaptureHtml = ImageHtml.GetSource(ImagePath).Replace("file:///", "");
            }
            else
            {
                screenCaptureHtml = ImageHtml.GetSource(ImagePath);
            }

            ScreenCapture img = new ScreenCapture();
            img.Source = screenCaptureHtml;
            img.TestName = test.Name;

            test.ScreenCapture.Add(img);

            return screenCaptureHtml;
        }

        /// <summary>
        /// Allows for adding a screen cast to the log event
        /// </summary>
        /// <param name="screencastPath">Path of the screencast</param>
        /// <returns>A formed HTML video tag with the supplied path</returns>
        public string AddScreencast(string screencastPath)
        {
            string screencastHtml;

            if (IsPathRelative(screencastPath))
            {
                screencastHtml = ScreencastHtml.GetSource(screencastPath).Replace("file:///", "");
            }
            else
            {
                screencastHtml = ScreencastHtml.GetSource(screencastPath);
            }

            Screencast sc = new Screencast();
            sc.Source = screencastHtml;
            sc.TestName = test.Name;

            test.Screencast.Add(sc);

            return screencastPath;
        }

        /// <summary>
        /// Assigns category to test
        /// </summary>
        /// <param name="CategoryName">Category</param>
        /// <returns>ExtentTest object</returns>
        public ExtentTest AssignCategory(params string[] CategoryName)
        {
            foreach (string c in CategoryName)
            {
                test.CategoryList.Add(new Category(c));
            }

            return this;
        }

        internal Test GetTest()
        {
            test.Status = runStatus;

            return test;
        }

        private Boolean IsPathRelative(string FilePath)
        {
            if (FilePath.StartsWith("http") || FilePath.StartsWith(".") || FilePath.StartsWith("/"))
                return true;

            return false;
        }

        private void TrackLastRunStatus(LogStatus LogStatus)
        {
            if (runStatus == LogStatus.Unknown)
            {
                if (LogStatus == LogStatus.Info)
                {
                    runStatus = LogStatus.Pass;
                }
                else
                {
                    runStatus = LogStatus;
                }

                return;
            }

            if (runStatus == LogStatus.Fatal) return;

            if (LogStatus == LogStatus.Fatal)
            {
                runStatus = LogStatus;
                return;
            }

            if (runStatus == LogStatus.Fail) return;

            if (LogStatus == LogStatus.Fail)
            {
                runStatus = LogStatus;
                return;
            }

            if (runStatus == LogStatus.Error) return;

            if (LogStatus == LogStatus.Error)
            {
                runStatus = LogStatus;
                return;
            }

            if (runStatus == LogStatus.Warning) return;

            if (LogStatus == LogStatus.Warning)
            {
                runStatus = LogStatus;
                return;
            }

            if (runStatus == LogStatus.Pass || runStatus == LogStatus.Info)
            {
                runStatus = LogStatus.Pass;
                return;
            }

            runStatus = LogStatus.Skip;
        }
    }
}
