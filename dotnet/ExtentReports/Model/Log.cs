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

    internal class Log
    {
        public DateTime Timestamp;
        public LogStatus LogStatus;
        public string StepName;
        public string Details;
    }
}
