using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Model;

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

        public string AddScreenCapture(string ImgPath)
        {
            return null;
        }

        public ExtentTest AssignCategory(params string[] Category)
        {
            bool toAdd = false;

            Category.ToList().ForEach(c =>
            {
                _test.CategoryList.ForEach(x =>
                {
                    toAdd = true;

                    if (x.Equals(c))
                    {
                        toAdd = false;
                    }
                });

                if (toAdd)
                {
                    _test.AuthorList.Add(new Category(c));
                }
            });

            return this;
        }

        public ExtentTest AssignAuthor(params string[] Author)
        {
            bool toAdd = false;

            Author.ToList().ForEach(a =>
            {
                _test.AuthorList.ForEach(x =>
                {
                    toAdd = true;

                    if (x.Equals(a))
                    {
                        toAdd = false;
                    }
                });

                if (toAdd)
                {
                    _test.AuthorList.Add(new Author(a));
                }
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

        internal Test GetTest()
        {
            return _test;
        }
    }
}
