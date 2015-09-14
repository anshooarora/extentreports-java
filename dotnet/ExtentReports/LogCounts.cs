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
    using System.Linq;
    using System.Text;

    using Model;

    internal class LogCounts
    {        
        public LogCounts GetLogCounts(Test test) {
            Pass += test.Logs.Where(l => l.LogStatus == LogStatus.Pass).Count();
            Fail += test.Logs.Where(l => l.LogStatus == LogStatus.Fail).Count();
            Fatal += test.Logs.Where(l => l.LogStatus == LogStatus.Fatal).Count();
            Error += test.Logs.Where(l => l.LogStatus == LogStatus.Error).Count();
            Warning += test.Logs.Where(l => l.LogStatus == LogStatus.Warning).Count();
            Info += test.Logs.Where(l => l.LogStatus == LogStatus.Info).Count();
            Skip += test.Logs.Where(l => l.LogStatus == LogStatus.Skip).Count();
            Unknown += test.Logs.Where(l => l.LogStatus == LogStatus.Unknown).Count();

            // recursively count for all child tests
            test.NodeList.ForEach(n => GetLogCounts(n));
    		
    		return this;
    	}

        public int Pass
        {
            get;
            private set;
        }

        public int Fail
        {
            get;
            private set;
        }

        public int Fatal
        {
            get;
            private set;
        }

        public int Error
        {
            get;
            private set;
        }

        public int Warning
        {
            get;
            private set;
        }

        public int Info
        {
            get;
            private set;
        }

        public int Skip
        {
            get;
            private set;
        }

        public int Unknown
        {
            get;
            private set;
        }

        public LogCounts() { }
    }
}
