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
            test
                .AssignCategory("Cat", "Cat", "Cat")
                .AssignAuthor("Anshoo", "Anshoo");
            extent.EndTest(test);

            test = extent.StartTest("Second", "Random desc");
            var child = extent.StartTest("Child", "Desc");
            child.Log(LogStatus.Warning, "Warning");
            child.AssignCategory("Regression", "Extent");
            test.AppendChild(child);
            extent.EndTest(test);

            test = extent.StartTest("Third", "Random desc");
            test.Log(LogStatus.Error, "Some Details");
            extent.EndTest(test);

            extent.Flush();
        }
    }
}
