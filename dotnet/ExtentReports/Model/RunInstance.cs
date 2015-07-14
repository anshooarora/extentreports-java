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

    internal class RunInstance
    {
        public RunInfo RunInfo;
        public List<Test> Tests;
        public SystemProperties SystemInfo;
        public List<ScreenCapture> ScreenCapture;

        public void init()
        {
            Tests = new List<Test>();
            ScreenCapture = new List<ScreenCapture>();
        }
    }
}
