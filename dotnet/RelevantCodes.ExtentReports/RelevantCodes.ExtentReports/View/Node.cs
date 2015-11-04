using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    class Node
    {
        public static string Source
        {
            get
            {
                return @"
                    <li class='node-1x' extentid='@Model.ID'>
                        <div class='collapsible-header test-node @Model.Status.ToString().ToLower()'>
                            <div class='right test-info'>
                                <span title='Test started time' class='test-started-time label green lighten-2 text-white'>@Model.StartTime</span>
                                <span title='Test ended time' class='test-ended-time label red lighten-2 text-white'>@Model.EndTime</span>
                                <span title='Time taken to finish' class='test-time-taken label blue-grey lighten-2 text-white'>@Model.GetRunTime()</span>
                                <span class='test-status label capitalize @Model.Status.ToString().ToLower()'>@Model.Status.ToString().ToLower()</span>
                            </div>
                            <div class='test-node-name'>@Model.Name</div>
                        </div>
                        <div class='collapsible-body'>
                            <div class='test-steps'>
                                @if (Model.LogList != null && Model.LogList.Count() > 0)
                                {
                                    <table class='bordered table-results'>
                                        <thead>
                                            <tr>
                                                <th>Status</th>
                                                <th>Timestamp</th>
                                                @if (Model.LogList[0].StepName != null)
                                                {                                                
                                                    <th>StepName</th>
                                                }
                                                <th>Details</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            @foreach (var log in Model.LogList)
                                            {
                                                <tr>
                                                    <td class='status @log.LogStatus.ToString().ToLower()'><i class='fa'></i>@log.LogStatus</td>" +
                                                    "<td class='timestamp'>@string.Format(\"{0:HH:mm:ss}\", log.Timestamp)</td>" +
                                                    @"@if (Model.LogList[0].StepName != null)
                                                    {
                                                        <td class='step-name'>@log.StepName</td>
                                                    }
                                                    <td class='step-details'>@log.Details</td>
                                                </tr>
                                            }
                                        </tbody>
                                    </table>
                                }
                            </div>
                        </div>
                    </li>";
            }
        }
    }
}
