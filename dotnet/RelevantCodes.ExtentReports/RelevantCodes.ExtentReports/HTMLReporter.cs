using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        // lock for extent source
        private object _sourcelock = new object();

        // folder where offline artifacts are stored
        private const string _offlineFolderParent = "extentreports";

        public virtual void Start(Report Report)
        {
            _report = Report;

            _filePath = Report.FilePath;
            _displayOrder = Report.DisplayOrder;
            _networkMode = Report.NetworkMode;
            
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

            File.WriteAllText(_filePath, _extentSource);
        }

        public virtual void AddTest()
        {
            _extentSource = Engine.Razor.RunCompile(View.Extent.Source, "extent", typeof(HTMLReporter), this);
        }

        public List<ExtentTest> TestList
        {
            get
            {
                return _report.TestList;
            }
        }

        public Dictionary<string, List<Test>> CategoryMap 
        { 
            get
            {
                return _report.CategoryMap;
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
            return _report.GetRunTime();
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
