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
            
            var test = extent.StartTest("First", "Random desc");
            test.Log(LogStatus.Info, "Some Details");
            extent.EndTest(test);

            test = extent.StartTest("Second", "Random desc");
            test.Log(LogStatus.Fail, "Some Details");
            extent.EndTest(test);

            test = extent.StartTest("Second", "Random desc");
            test.Log(LogStatus.Error, "Some Details");
            extent.EndTest(test);

            extent.Flush();
        }
    }
}
