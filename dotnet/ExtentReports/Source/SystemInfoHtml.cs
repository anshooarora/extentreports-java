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

    internal class SystemInfoHtml
    {
        public static string GetColumn()
        {
            return "<div class='col l3 m6 s12'>" +
                        "<div class='card-panel'>" +
                            "<span class='panel-name'><!--%%SYSTEMINFOPARAM%%--></span>" +
                            "<span class='panel-lead'><!--%%SYSTEMINFOVALUE%%--></span>" +
                        "</div>" +
                    "</div>";
        }
    }
}
