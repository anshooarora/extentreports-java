using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports
{
    class Program
    {
        static void Main(string[] args)
        {
            var extent = new ExtentReports("Extent.Net.html");
            var report = extent.StartTest("Test");
            
            report.Log(LogStatus.Info, "Created new Lead email: ");
            report.Log(LogStatus.Pass, "Test");

            extent.EndTest(report);//here exception
            extent.Flush();
            extent.Close();
        }
    }
}
