// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;

    internal class Test
    {
        public bool HasEnded = false;
        public bool IsChildNode = false;
        public bool HasChildNodes = false;
        public DateTime StartedTime;
        public DateTime EndedTime; 
        public List<TestAttribute> CategoryList;
        public List<TestAttribute> AuthorList;
        public List<Log> Logs;
        public List<Test> NodeList;
        public List<ScreenCapture> ScreenCapture;
        public List<Screencast> Screencast;
        public LogStatus Status = LogStatus.Unknown;
        public string Description;
        public string InternalWarning;
        public string Name;

        internal void PrepareFinalize()
        {
            updateTestStatusRecursively(this);

            if (Status == LogStatus.Info)
            {
                Status = LogStatus.Pass;
            }
        }

        internal void TrackLastRunStatus()
        {
            Logs.ForEach(l => findStatus(l.LogStatus));

            if (Status == LogStatus.Info)
            {
                Status = LogStatus.Pass;
            }
        }

        private void updateTestStatusRecursively(Test test) {
            test.Logs.ForEach(l => findStatus(l.LogStatus));

            if (test.HasChildNodes) {
                test.NodeList.ForEach(n => updateTestStatusRecursively(n));
    	    }
        }

        private void findStatus(LogStatus logStatus)
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
            InternalWarning = "";

            CategoryList = new List<TestAttribute>();
            AuthorList = new List<TestAttribute>();
            Logs = new List<Log>();
            NodeList = new List<Test>();
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }
    }
}
