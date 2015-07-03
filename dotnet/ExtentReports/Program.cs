using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports
{
    class Program
    {
        static void Main(string[] args)
        {
            var r = new ExtentReports("Extent.html", true);
            var t = r.StartTest("Test", "desc");
            t.Log(LogStatus.Pass, "First step");
            r.EndTest(t);
            r.Flush();
        }
    }
}
