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
