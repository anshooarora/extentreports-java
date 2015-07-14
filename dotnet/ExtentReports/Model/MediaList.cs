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

    internal class MediaList
    {
        public List<ScreenCapture> ScreenCapture;
        public List<Screencast> Screencast;

        public MediaList()
        {
            ScreenCapture = new List<ScreenCapture>();
            Screencast = new List<Screencast>();
        }
    }
}
