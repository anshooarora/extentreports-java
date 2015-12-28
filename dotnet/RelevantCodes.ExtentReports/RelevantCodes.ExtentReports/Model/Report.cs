using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Config;
using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports.Model
{
    public abstract class Report
    {
        private const string InternalWarning = "Close was called before test could end safely using EndTest.";

        private Guid _id;
        private List<IReporter> _reporterList;
        private LogStatus _reportStatus;
        private bool _terminated = false;

        internal string FilePath { get; set; }

        internal bool ReplaceExisting { get; set; }

        internal DisplayOrder DisplayOrder { get; set; }

        internal NetworkMode NetworkMode { get; set; }

        internal List<ExtentTest> TestList { get; set; }

        internal IDictionary<string, List<Test>> CategoryMap { get; private set; }

        internal DateTime StartTime;

        internal Test Test { get; private set; }

        internal Dictionary<string, string> SystemInfo { get; set; }

        internal List<string> TestRunnerLogs { get; set; }

        internal Dictionary<string, string> ConfigurationMap { get; private set; }

        protected void Attach(IReporter Reporter)
        {
            if (_reporterList == null)
            {
                _reporterList = new List<IReporter>();
            }

            _reporterList.Add(Reporter);
            Reporter.Start(this);
        }

        protected void Detach(IReporter Reporter)
        {
            Reporter.Stop();
            _reporterList.Remove(Reporter);
        }

        protected void AddTest(Test Test)
        {
            this.Test = Test;

            Test.PrepareFinalize();

            Test.CategoryList.ForEach(x =>
            {
                if (!CategoryMap.ContainsKey(x.Name))
                {
                    CategoryMap.Add(x.Name, new List<Test>() { Test });
                }
                else
                {
                    CategoryMap[x.Name].Add(Test);
                }
            });

            _reporterList.ForEach(x => x.AddTest());

            UpdateReportStatus(Test.Status);
        }

        protected virtual void Terminate()
        {
            TestList.ForEach(x =>
            {
                if (!x.GetTest().HasEnded)
                {
                    var ex = new ExtentTestInterruptedException(InternalWarning);

                    x.GetTest().InternalWarning = InternalWarning;
                    x.Log(LogStatus.Fail, ex);

                    AddTest(x.GetTest());
                }
            });

            Flush();

            _reporterList.ForEach(x => 
            {
                x.Stop();
            });

            _reporterList.Clear();

            _terminated = true;
        }

        protected virtual void Flush()
        {
            if (_terminated)
            {
                throw new IOException("Unable to write source: Stream closed.");
            }

            _reporterList.ForEach(x => x.Flush());
        }

        protected virtual void LoadConfig(Configuration Config)
        {
            var config = Config.Read();

            if (config != null) 
            {
                ConfigurationMap = config;
            }

            if (!ConfigurationMap.ContainsKey("dateFormat") || string.IsNullOrEmpty(ConfigurationMap["dateFormat"]))
            {
                ConfigurationMap["dateFormat"] = Globals.DateFormat;
            }

            if (!ConfigurationMap.ContainsKey("timeFormat") || string.IsNullOrEmpty(ConfigurationMap["timeFormat"]))
            {
                ConfigurationMap["timeFormat"] = Globals.TimeFormat;
            }

            if (!ConfigurationMap.ContainsKey("protocol") || string.IsNullOrEmpty(ConfigurationMap["protocol"]))
            {
                ConfigurationMap["protocol"] = Globals.Protocol;
            }
        }

        internal string GetRunTime()
        {
            TimeSpan diff = DateTime.Now.Subtract(StartTime);

            return String.Format("{0}h {1}m {2}s+{3}ms", diff.Hours, diff.Minutes, diff.Seconds, diff.Milliseconds);
        }

        private void UpdateReportStatus(LogStatus logStatus)
        {
            if (_reportStatus == LogStatus.Fatal) return;

            if (logStatus == LogStatus.Fatal)
            {
                _reportStatus = logStatus;
                return;
            }

            if (_reportStatus == LogStatus.Fail) return;

            if (logStatus == LogStatus.Fail)
            {
                _reportStatus = logStatus;
                return;
            }

            if (_reportStatus == LogStatus.Error) return;

            if (logStatus == LogStatus.Error)
            {
                _reportStatus = logStatus;
                return;
            }

            if (_reportStatus == LogStatus.Warning) return;

            if (logStatus == LogStatus.Warning)
            {
                _reportStatus = logStatus;
                return;
            }

            if (_reportStatus == LogStatus.Pass) return;

            if (logStatus == LogStatus.Pass)
            {
                _reportStatus = LogStatus.Pass;
                return;
            }

            if (_reportStatus == LogStatus.Skip) return;

            if (logStatus == LogStatus.Skip)
            {
                _reportStatus = LogStatus.Skip;
                return;
            }

            if (_reportStatus == LogStatus.Info) return;

            if (logStatus == LogStatus.Info)
            {
                _reportStatus = LogStatus.Info;
                return;
            }

            _reportStatus = LogStatus.Unknown;
        }

        public Report() 
        {
            _id = Guid.NewGuid();

            CategoryMap = new SortedDictionary<string, List<Test>>();
            StartTime = DateTime.Now;
            SystemInfo = new StandardSystemInfo().GetInfo();
            TestRunnerLogs = new List<string>();
        }
    }
}
