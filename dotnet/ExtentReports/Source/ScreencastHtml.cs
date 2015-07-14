// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports.Source
{
    using System;

    class ScreencastHtml
    {
        public static string GetSource(string ScreencastPath)
        {
            return "<video id='video' src='file:///" + ScreencastPath + "' width='50%' controls />";
        }
    }
}
