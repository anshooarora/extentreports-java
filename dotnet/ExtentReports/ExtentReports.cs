// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;

    using Model;

    /// <summary>
    /// ExtentReports
    /// </summary>
    public class ExtentReports
    {
        private List<ExtentTest> testList;
        private ReportConfig config; 
        private ReportInstance reportInstance;
        private SystemInfo systemInfo;
        
        /// <summary>
        /// Initializes the reporting by setting the file-path and test DisplayOrder
        /// </summary>
        /// <param name="FilePath">Path of the file, in .htm or .html format</param>
        /// <param name="ReplaceExisting">Setting to overwrite (true) the existing file or append (false) to it</param>
        /// <param name="DisplayOrder">Determines the order in which your tests will be displayed</param>
        public ExtentReports(string FilePath, bool ReplaceExisting, DisplayOrder DisplayOrder = DisplayOrder.OldestFirst)
        {
            reportInstance = new ReportInstance();
            reportInstance.Initialize(FilePath, ReplaceExisting, DisplayOrder);

            systemInfo = new SystemInfo();
        }

        /// <summary>
        /// Calling startTest() generates a toggle for the test in the HTML file 
        /// and adds all log events under this level. This is a required step and 
        /// without calling this method the toggle will not be created for the test 
        /// and log will not be added.
        /// </summary>
        /// <param name="TestName">Name of the test</param>
        /// <param name="Description">A short description of the test</param>
        /// <returns>ExtentTest object</returns>
        public ExtentTest StartTest(string TestName, string Description = "")
        {
            if (testList == null)
            {
                testList = new List<ExtentTest>();
            }

            var test = new ExtentTest(TestName, Description);
            testList.Add(test);

            return test;
        }

        /// <summary>
        /// Ends the test. Calling this method will ensure the test gets safely 
        /// added to the report.
        /// </summary>
        /// <param name="Test">ExtentTest object</param>
        public void EndTest(ExtentTest Test)
        {
            Test.GetTest().HasEnded = true;

            reportInstance.AddTest(Test.GetTest());
        }

        /// <summary>
        /// Allows various configurations to be applied to the report file
        /// </summary>
        /// <returns>ReportConfig object</returns>
        public ReportConfig Config()
        {
            if (config == null)
            {
                if (reportInstance == null)
                {
                    throw new Exception("Cannot apply config before ExtentReports is initialized");
                }

                config = new ReportConfig(reportInstance);
            }

            return config;
        }

        /// <summary>
        /// Add system information to the SystemInfo view
        /// </summary>
        /// <param name="SystemInfo">SystemInfo values as Key-Value pairs</param>
        /// <returns>ExtentReports object</returns>
        public ExtentReports AddSystemInfo(Dictionary<string, string> SystemInfo)
        {
            systemInfo.SetInfo(SystemInfo);

            return this;
        }

        /// <summary>
        /// Add system information to the SystemInfo view
        /// </summary>
        /// <param name="Param">Name of system parameter</param>
        /// <param name="Value">Value</param>
        /// <returns>ExtentReports object</returns>
        public ExtentReports AddSystemInfo(string Param, string Value)
        {
            systemInfo.SetInfo(Param, Value);

            return this;
        }

        /// <summary>
        /// Writes all info to the report file
        /// </summary>
        public void Flush()
        {
            removeChildTests();

            reportInstance.WriteAllResources(testList, systemInfo);

            if (testList != null)
                systemInfo.Clear();
        }

        /// <summary>
        /// Closes the underlying stream and clears all resources
        /// </summary>
        public void Close()
        {
            removeChildTests();

            Flush();

            reportInstance.Terminate(testList);

            if (testList != null)
                testList.Clear();
        }

        private void removeChildTests()
        {
            if (testList == null)
            {
                return;
            }

            testList.ForEach(t => { 
                if (t.GetTest().IsChildNode) 
                    testList.Remove(t); 
            });
        }
    }
}
