namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Model;
    using Source;

    public class ExtentTest// : IDisposable
    {
        private Test test;
        private LogStatus runStatus = LogStatus.Unknown;

        public ExtentTest(string TestName, string Description)
        {
            test = new Test();

            test.Name = TestName;
            test.Description = Description;
            test.StartedTime = DateTime.Now;
        }

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

        public void Log(LogStatus LogStatus, string Details)
        {
            Log(LogStatus, "", Details);
        }

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

        public string AddScreencast(String screencastPath)
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

        internal Test GetTest()
        {
            test.Status = runStatus;

            return test;
        }

        private Boolean IsPathRelative(String FilePath)
        {
            if (FilePath.IndexOf("http") == 0 || FilePath.IndexOf(".") == 0 || FilePath.IndexOf("/") == 0)
            {
                return true;
            }

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
