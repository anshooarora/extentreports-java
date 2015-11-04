using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    internal class Category
    {
        public static string Source
        {
            get
            {
                return @"<li class='category-item displayed @Model.Name.ToLower()'>
				            <div class='cat-head'>
					            <span class='category-name'>@Model.Name</span>
				            </div>
				            <div class='category-status-counts'>
					            <span class='cat-pass label'></span>
					            <span class='cat-fail label'></span>
					            <span class='cat-other label'></span>
				            </div>
				            <div class='cat-body'>
					            <div class='category-status-counts'>
						            <span class='cat-pass label'></span>
						            <span class='cat-fail label'></span>
						            <span class='cat-other label'></span>
					            </div>
					            <div class='cat-tests'>
						            <table class='bordered'>
							            <thead>
								            <tr>
									            <th>Run Date</th>
									            <th>Test Name</th>
									            <th>Status</th>
								            </tr>
							            </thead>
						            </table>
					            </div>
				            </div>+
			            </li>";
            }
        }

        public static string Info
        {
            get
            {
                return @"
                        <tr>
                            <td>@Model.StartTime</td>
                            <td><span class='category-link linked' extentid='@Model.ID'>@Model.Name</span></td>
                            <td><div class='status label capitalize @Model.Status.ToLower()'>@Model.Status.ToLower()</div></td>
                        </tr>
                        ";
            }
        }
    }
}
