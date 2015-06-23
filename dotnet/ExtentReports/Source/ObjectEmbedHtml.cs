namespace RelevantCodes.ExtentReports.Source
{
    using System;

    internal class ObjectEmbedHtml
    {
        public static string GetColumn()
        {
            return "<div class='col l3 m6 s12'>" +
                        "<div class='card-panel'>" +
                            "<span class='panel-name'><!--%%OBJECTVIEWPARAM%%--></span>" +
                            "<span class='panel-object'><!--%%OBJECTVIEWVALUE%%--></span>" +
                        "</div>" +
                    "</div>";
        }

        public static string GetFullWidth()
        {
            return "<!--%%OBJECTVIEWNULL%%--><div class='col s12'>" +
                    "<div class='card-panel'>" +
                        "<span class='panel-lead'><!--%%OBJECTVIEWVALUE%%--></span>" +
                    "</div>" +
                "</div><!--%%OBJECTVIEWNULL%%-->";
        }
    }
}
