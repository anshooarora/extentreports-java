using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Model;
using RelevantCodes.ExtentReports.View;

namespace RelevantCodes.ExtentReports
{
    public class ExtentTest
    {
        private LogStatus _testStatus = LogStatus.Unknown;
        private Test _test;

        public ExtentTest(string Name, string Description)
        {
            _test = new Test();

            _test.Name = Name;
            _test.Description = Description;
        }

        public void Log(LogStatus Status, string StepName, string Details)
        {
            var log = new Log();

            log.Timestamp = DateTime.Now; 
            log.LogStatus = Status;
            log.StepName = StepName;
            log.Details = Details;

            _test.LogList.Add(log);

            _test.TrackLastRunStatus();
            _testStatus = _test.Status;
        }

        public void Log(LogStatus Status, string Details)
        {
            Log(Status, null, Details);
        }

        public string AddScreenCapture(string ImagePath)
        {
            string screenCaptureHtml;

            if (IsPathRelative(ImagePath))
            {
                screenCaptureHtml = ScreenCaptureHtml.GetSource(ImagePath).Replace("file:///", "");
            }
            else
            {
                screenCaptureHtml = ScreenCaptureHtml.GetSource(ImagePath);
            }

            var img = new ScreenCapture();
            img.Source = screenCaptureHtml;
            img.TestName = _test.Name;
            img.TestID = _test.ID;

            _test.ScreenCapture.Add(img);

            return screenCaptureHtml;
        }

        public string AddScreencast(string ScreencastPath)
        {
            if (IsPathRelative(ScreencastPath))
            {
                ScreencastPath = ScreencastHtml.GetSource(ScreencastPath).Replace("file:///", "");
            }
            else
            {
                ScreencastPath = ScreencastHtml.GetSource(ScreencastPath);
            }

            var sc = new Screencast();
            sc.Source = ScreencastPath;
            sc.TestName = _test.Name;
            sc.TestID = _test.ID;

            _test.Screencast.Add(sc);

            return ScreencastPath;
        }

        private Boolean IsPathRelative(string FilePath)
        {
            if (FilePath.StartsWith("http") || !Path.IsPathRooted(FilePath))
            {
                return true;
            }

            return false;
        }

        public ExtentTest AssignCategory(params string[] Category)
        {
            Category.ToList().ForEach(c =>
            {
                _test.AddCategory(c);
            });

            return this;
        }

        public ExtentTest AssignAuthor(params string[] Author)
        {
            Author.ToList().ForEach(a =>
            {
                _test.AddAuthor(a);
            });

            return this;
        }

        public ExtentTest AppendChild(ExtentTest Node)
        {
            var node = Node.GetTest();

            node.ChildNode = true;
            node.Ended = true;

            node.TrackLastRunStatus();

            node.CategoryList.ForEach(c => AssignCategory(c.Name));
            node.AuthorList.ForEach(a => AssignAuthor(a.Name));

            _test.NodeList.Add(node);
            _test.ContainsChildNodes = true;

            return this;
        }

        public LogStatus GetCurrentStatus()
        {
            return _testStatus;
        }

        public Test GetTest()
        {
            return _test;
        }
    }
}
