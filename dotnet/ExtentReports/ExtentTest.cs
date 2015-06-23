namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using RelevantCodes.ExtentReports.Model;
    using RelevantCodes.ExtentReports.Source;

    public class ExtentTest : IDisposable
    {
        private Test test;
        private LogStatus runStatus = LogStatus.Unknown;

        public ExtentTest(String TestName, String Description)
        {
            test = new Test();

            test.Name = TestName;
            test.Description = Description;
            test.StartedAt = DateTime.Now;
        }

        public void Log(LogStatus logStatus, String stepName, String details)
        {
            Log evt = new Log();

            evt.Timestamp = DateTime.Now;
            evt.LogStatus = logStatus;
            evt.StepName = stepName;
            evt.Details = details;

            test.Log.Add(evt);

            TrackLastRunStatus(logStatus);
        }

        public void Log(LogStatus logStatus, String details)
        {
            Log(logStatus, "", details);
        }

        public String AddScreenCapture(String ImagePath)
        {
            String screenCaptureHtml;

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

        public String AddScreencast(String screencastPath)
        {
            String screencastHtml;

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

        public Test GetTest()
        {
            test.Status = runStatus;

            return test;
        }

        private Boolean IsPathRelative(String path)
        {
            if (path.IndexOf("http") == 0 || path.IndexOf(".") == 0 || path.IndexOf("/") == 0)
            {
                return true;
            }

            return false;
        }

        private void TrackLastRunStatus(LogStatus logStatus)
        {
            if (runStatus == LogStatus.Unknown)
            {
                if (logStatus == LogStatus.Info)
                {
                    runStatus = LogStatus.Pass;
                }
                else
                {
                    runStatus = logStatus;
                }

                return;
            }

            if (runStatus == LogStatus.Fatal) return;

            if (logStatus == LogStatus.Fatal)
            {
                runStatus = logStatus;
                return;
            }

            if (runStatus == LogStatus.Fail) return;

            if (logStatus == LogStatus.Fail)
            {
                runStatus = logStatus;
                return;
            }

            if (runStatus == LogStatus.Error) return;

            if (logStatus == LogStatus.Error)
            {
                runStatus = logStatus;
                return;
            }

            if (runStatus == LogStatus.Warning) return;

            if (logStatus == LogStatus.Warning)
            {
                runStatus = logStatus;
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
