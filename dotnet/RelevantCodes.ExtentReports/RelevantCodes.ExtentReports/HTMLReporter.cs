using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.CompilerServices;
using System.Threading;

using RazorEngine.Configuration;
using RazorEngine.Text;
using RazorEngine.Templating;
using RazorEngine;

using RelevantCodes.ExtentReports.Model;
using RelevantCodes.ExtentReports.View;

namespace RelevantCodes.ExtentReports
{
    public class HTMLReporter : IReporter
    {
         // report instance    
        private Report _report;
    
        // path of the html file
        private string _filePath;
    
        // display-order (default = OLDEST_FIRST)
        private DisplayOrder _displayOrder;
    
        // network mode (default = ONLINE)
        private NetworkMode _networkMode;

        // marks the report session as terminated
        private bool _terminated = false;

        // extent document source
        private string _extentSource = null;

        // lock for document source: _extentSource
        private static readonly object _lock = new Object();

        // lock for extent source
        private object _sourcelock = new object();

        // folder where offline artifacts are stored
        private const string _offlineFolderParent = "extentreports";

        public virtual void Start(Report Report)
        {
            InitializeRazor();

            _report = Report;

            _filePath = Report.FilePath;
            _displayOrder = Report.DisplayOrder;
            _networkMode = Report.NetworkMode;

            Thread.CurrentThread.CurrentUICulture = Report.Culture;

            bool replaceExisting = Report.ReplaceExisting;

            if (!File.Exists(_filePath))
            {
                replaceExisting = true;
            }

            if (replaceExisting)
            {
                _extentSource = Extent.Source;
            }
            else
            {
                _extentSource = File.ReadAllText(_filePath);
            }

            _extentSource = _extentSource.Replace("<!--%SuiteStartedTime%-->", _report.StartTime.ToString());
        }

        public virtual void Stop()
        {
            _terminated = true;
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public virtual void Flush()
        {
            if (_terminated)
            {
                throw new IOException("Unable to write source: Stream closed.");
            }

            var testList = _report.TestList;

            if (testList.Equals(null))
            {
                return;
            }

            lock (_lock)
            {
                _extentSource = Engine.Razor.RunCompile(View.Extent.Source, "extent", typeof(HTMLReporter), this);
            }

            File.WriteAllText(_filePath, _extentSource);
        }

        public virtual void AddTest() { }

        public List<ExtentTest> TestList
        {
            get
            {
                var testList = new List<ExtentTest>();
                testList.AddRange(_report.TestList);

                return testList;
            }
        }

        public IDictionary<string, List<Test>> CategoryMap 
        { 
            get
            {
                return _report.CategoryMap;
            }
        }

        public IDictionary<string, List<Test>> ExceptionMap
        {
            get
            {
                return _report.ExceptionMap;
            }
        }

        public DateTime StartTime
        {
            get
            {
                return _report.StartTime;
            }
        }

        public Dictionary<string, string> SystemInfo
        {
            get
            {
                return _report.SystemInfo;
            }
        }

        public List<string> TestRunnerLogs
        {
            get
            {
                return _report.TestRunnerLogs;
            }
        }

        public Dictionary<string, string> ConfigurationMap
        {
            get
            {
                return _report.ConfigurationMap;
            }
        }

        public string GetRunTime()
        {
            return _report.RunTime;
        }
        
        private void InitializeRazor()
        {
            TemplateServiceConfiguration templateConfig = new TemplateServiceConfiguration();
            templateConfig.DisableTempFileLocking = true;
            templateConfig.EncodedStringFactory = new RawStringFactory();
            templateConfig.CachingProvider = new DefaultCachingProvider(x => { });
            var service = RazorEngineService.Create(templateConfig);
            Engine.Razor = service;
        }
    }
}
