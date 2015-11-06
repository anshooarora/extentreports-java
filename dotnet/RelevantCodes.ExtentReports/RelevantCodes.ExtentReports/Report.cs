using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports
{
    public abstract class Report
    {
        private Guid _id;
        private List<IReporter> _reporterList;
        private LogStatus _reportStatus;
        private bool _terminated = false;

        public string FilePath { get; protected set; }

        public bool ReplaceExisting { get; protected set; }

        public DisplayOrder DisplayOrder { get; protected set; }

        public NetworkMode NetworkMode { get; protected set; }

        public List<ExtentTest> TestList { get; protected set; }

        public DateTime StartTime;

        internal Test Test { get; private set; }

        public Dictionary<string, string> SystemInfo { get; protected set; }

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

            _reporterList.ForEach(x => x.AddTest());

            UpdateReportStatus(Test.Status);
        }

        protected virtual void Terminate()
        {
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
            
            StartTime = DateTime.Now;
            SystemInfo = new StandardSystemInfo().GetInfo();
        }
    }
}
