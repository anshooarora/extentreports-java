using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Config;
using RelevantCodes.ExtentReports.Model;
using System.Xml.Linq;

namespace RelevantCodes.ExtentReports
{
    public class ExtentReports : Report
    {
        /// <summary>
        /// Initializes Extent HTML report
        /// </summary>
        /// 
        /// <param name="FilePath">Path of the file, in .htm or .html format</param>
        /// <param name="Order">
        /// Determines the order in which your tests will be displayed
        /// <list type="bullet">
        ///     <item>
        ///         <description>OldestFirst (default) - oldest test at the top, newest at the end</description>
        ///     </item>
        ///     <item>
        ///         <description>NewestFirst - newest test at the top, oldest at the end</description>
        ///     </item>
        /// </list>
        /// </param>
        public ExtentReports(string FilePath, DisplayOrder Order = DisplayOrder.OldestFirst)
        {
            this.FilePath = FilePath;
            this.DisplayOrder = DisplayOrder;

            var xdoc = XDocument.Parse(Properties.Resources.extent_config);
            LoadConfig(new Configuration(xdoc));
            
            Attach(new HTMLReporter());
        }

        /// <summary>
        /// Allows performing configuration and customization to the HTML report from 
        /// configuration external file
        /// </summary>
        /// 
        /// <param name="FilePath">Config file</param>
        /// <returns>An ExtentReports object</returns>
        public ExtentReports LoadConfig(string FilePath)
        {
            LoadConfig(new Configuration(FilePath));

            return this;
        }

        /// <summary>
        /// Calling startTest() generates a toggle for the test in the HTML file and adds all 
        /// log events under this level. This is a required step and without calling this method 
        /// the toggle will not be created for the test and log will not be added.
        /// </summary>
        /// 
        /// <param name="Name">Name of the test</param>
        /// <param name="Description">A short description of the test</param>
        /// <returns>An ExtentTest object</returns>
        public ExtentTest StartTest(string Name, string Description = "")
        {
            if (TestList == null)
            {
                TestList = new List<ExtentTest>();
            }

            var test = new ExtentTest(Name, Description);

            if (DisplayOrder == DisplayOrder.OldestFirst)
            {
                TestList.Insert(0, test);
            }
            else
            {
                TestList.Add(test); 
            }

            return test;
        }

        /// <summary>
        /// Ends the current test
        /// </summary>
        /// 
        /// <param name="Test">The ExtentTest object that is coming to an end</param>
        public void EndTest(ExtentTest Test)
        {
            Test.GetTest().Ended = true;

            AddTest(Test.GetTest());

            RemoveChildTests();
        }

        /// <summary>
        /// Add system information to the SystemInfo view
        /// </summary>
        /// 
        /// <param name="Param">Name of system parameter</param>
        /// <param name="Value">Value of system parameter</param>
        /// <returns>An ExtentReports object</returns>
        public ExtentReports AddSystemInfo(string Param, string Value)
        {
            SystemInfo[Param] = Value;

            return this;
        }

        /// <summary>
        /// Add system information to the SystemInfo view
        /// </summary>
        /// 
        /// <param name="Info">Dictionary of system-info values</param>
        /// <returns>An ExtentReports object</returns>
        public ExtentReports AddSystemInfo(Dictionary<string, string> Info)
        {
            foreach (KeyValuePair<string, string> entry in Info)
            {
                AddSystemInfo(entry.Key, entry.Value);
            }

            return this;
        }

        /// <summary>
        /// Adds logs from test framework tools such as NUnit
        /// </summary>
        /// 
        /// <param name="log">Log string from the TestRunner</param>
        public void AddTestRunnerOutput(string log)
        {
            TestRunnerLogs.Add(log);
        }

        /// <summary>
        /// Appends the HTML file with all the ended tests. There must be at least 1 ended test
        /// for anything to be appended to the report
        /// 
        /// Note: If <code>flush()</code> is called before any of the ended tests, 
        /// no information will be appended.
        /// 
        /// Note: If <code>flush()</code> while the test is running (not yet ended), 
        /// it will not be appended to the report.
        /// </summary>
        new public void Flush()
        {
            base.Flush();
        }

        /// <summary>
        /// Closes the underlying stream and clears all resources
        /// 
        /// If any of your test ended abruptly causing any side-affects  (not all logs sent 
        /// to ExtentReports, information missing), this method will ensure that the test is 
        /// still appended to the report with a warning message.
        /// </summary>
        public void Close()
        {
            Terminate();
        }

        /// <summary>
        /// Removes all child nodes in <code>TestList</code> - a container for parent tests only. 
        /// When <code>Flush()</code> is called, it adds all parent tests to the report and child 
        /// tests as nodes. This method makes sure no child tests are added as top-level nodes. 
        /// </summary>
        private void RemoveChildTests()
        {
            TestList = TestList.Where(x => !x.GetTest().ChildNode).ToList();
        }
    }
}
