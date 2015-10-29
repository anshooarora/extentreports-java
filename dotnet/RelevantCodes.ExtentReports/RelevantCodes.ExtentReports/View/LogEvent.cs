using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    internal class LogEvent
    {
        public static string Source
        {
            get
            {
                return @"
                    <tr>
	                    <td class='status'><i class='fa'></i></td>
	                    <td class='timestamp'></td>
	                    <td class='step-name'></td>
	                    <td class='step-details'></td>
                    </tr>";
            }
        }
    }
}
