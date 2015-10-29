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
                    <li>
	                    <div class='collapsible-header test-node'>
		                    <div class='right test-info'>
			                    <span title='Test started time' class='test-started-time label green lighten-2 text-white'></span>
			                    <span title='Test ended time' class='test-ended-time label red lighten-2 text-white'></span>
			                    <span title='Time taken to finish' class='test-time-taken label blue-grey lighten-2 text-white'></span>
			                    <span class='test-status label capitalize'></span>
		                    </div>
		                    <div class='border-bullet'></div>
		                    <div class='test-node-name'></div>
	                    </div>
	                    <div class='collapsible-body'>
		                    <div class='test-steps'>
			                    <table class='bordered table-results'>
				                    <thead>
					                    <tr>
						                    <th>Status</th>
						                    <th>Timestamp</th>
						                    colStepName +
						                    <th>Details</th>
					                    </tr>
				                    </thead>
				                    <tbody>
				                    </tbody>
			                    </table>
		                    </div>
	                    </div>
                    </li>";
            }
        }
    }
}
