// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports
{
    internal abstract class LogSettings
    {
        protected static string LogTimeFormat = "HH:mm:ss";
        protected static string LogDateFormat = "yyyy-MM-dd";
        protected static string LogDateTimeFormat = LogDateFormat + " " + LogTimeFormat;
    }
}
