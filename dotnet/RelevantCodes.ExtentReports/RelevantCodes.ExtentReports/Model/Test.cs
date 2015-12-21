using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    public class Test
    {
        public string Name { get; set; }

        public string Description { get; set; }

        public DateTime StartTime { get; internal set; }

        public DateTime EndTime { get; internal set; }

        public string RunTime
        {
            get
            {
                TimeSpan diff = EndTime.Subtract(StartTime);

                return String.Format("{0}h {1}m {2}s+{3}ms", diff.Hours, diff.Minutes, diff.Seconds, diff.Milliseconds);
            }
        }

        public LogStatus Status { get; private set; }

        public Guid ID { get; internal set; }

        public bool ChildNode { get; set;}

        public bool Ended { get; set; }

        public bool ContainsChildNodes { get; set; }

        public List<TestAttribute> CategoryList;

        internal List<ScreenCapture> ScreenCapture;

        internal List<Screencast> Screencast;

        internal Boolean HasEnded = false;

        public string InternalWarning = null;

        public void AddCategory(string Category)
        {
            if (!CategoryList.Select(x => x.Name).ToList().Contains(Category))
            {
                CategoryList.Add(new Category(Category));
            }
        }

        public List<TestAttribute> AuthorList;

        public void AddAuthor(string Author)
        {
            if (!AuthorList.Select(x => x.Name).ToList().Contains(Author))
            {
                AuthorList.Add(new Author(Author));
            }
        }

        public List<Test> NodeList { get; set; }

        public IEnumerable<Test> NodeIterator()
        {
            return new ExtentIterator<Test>(NodeList);
        }

        public List<Log> LogList { get; set; }

        public IEnumerable<Log> LogIterator()
        {
            return new ExtentIterator<Log>(LogList);
        }

        public string GetCombinedCategories()
        {
            string cats = "";

            CategoryList.ForEach(x => cats += " " + x.Name);

            return cats;
        }

        public void TrackLastRunStatus()
        {
            LogList.ForEach(x =>
            {
                FindStatus(x.LogStatus);
            });

            Status = Status == LogStatus.Info ? LogStatus.Pass : Status;
        }

        public void PrepareFinalize()
        {
            EndTime = DateTime.Now;
            HasEnded = true;

            UpdateStatusRecursive(this);
        }

        private void UpdateStatusRecursive(Test test)
        {
            test.LogList.ForEach(x => FindStatus(x.LogStatus));

            if (test.ContainsChildNodes)
            {
                test.NodeList.ForEach(x => UpdateStatusRecursive(x));
            }
        }

        private void FindStatus(LogStatus logStatus)
        {
            if (Status == LogStatus.Fatal) return;

            if (logStatus == LogStatus.Fatal)
            {
                Status = logStatus;
                return;
            }

            if (Status == LogStatus.Fail) return;

            if (logStatus == LogStatus.Fail)
            {
                Status = logStatus;
                return;
            }

            if (Status == LogStatus.Error) return;

            if (logStatus == LogStatus.Error)
            {
                Status = logStatus;
                return;
            }

            if (Status == LogStatus.Warning) return;

            if (logStatus == LogStatus.Warning)
            {
                Status = logStatus;
                return;
            }

            if (Status == LogStatus.Pass) return;

            if (logStatus == LogStatus.Pass)
            {
                Status = LogStatus.Pass;
                return;
            }

            if (Status == LogStatus.Skip) return;

            if (logStatus == LogStatus.Skip)
            {
                Status = LogStatus.Skip;
                return;
            }

            if (Status == LogStatus.Info) return;

            if (logStatus == LogStatus.Info)
            {
                Status = LogStatus.Info;
                return;
            }

            Status = LogStatus.Unknown;
        }

        public Test()
        {
            Status = LogStatus.Unknown;
            
            StartTime = DateTime.Now;
            ID = Guid.NewGuid();

            CategoryList = new List<TestAttribute>();
            AuthorList = new List<TestAttribute>();
            LogList = new List<Log>();
            NodeList = new List<Test>();
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }

        public const string ChildNodeClass = "hasChildren";
    }
}
