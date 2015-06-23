namespace RelevantCodes.ExtentReports.Source
{
    using System;

    internal class StepHtml
    {
        public static string GetSource(int ColSpan)
        {
            if (ColSpan == 2)
            {
                return "<tr>" +
                            "<td><!--%%TIMESTAMP%%--></td>" +
                            "<td title='<!--%%STEPSTATUSU%%-->' class='status <!--%%STEPSTATUS%%-->'><i class='fa fa-<!--%%STATUSICON%%-->'></i></td>" +
                            "<td class='step-details' colspan='2'><!--%%DETAILS%%--></td>" +
                        "</tr>";

            }

            return "<tr>" +
                        "<td><!--%%TIMESTAMP%%--></td>" +
                        "<td title='<!--%%STEPSTATUSU%%-->' class='status <!--%%STEPSTATUS%%-->'><i class='fa fa-<!--%%STATUSICON%%-->'></i></td>" +
                        "<td class='step-name'><!--%%STEPNAME%%--></td>" +
                        "<td class='step-details'><!--%%DETAILS%%--></td>" +
                    "</tr>";
        }
    }
}
