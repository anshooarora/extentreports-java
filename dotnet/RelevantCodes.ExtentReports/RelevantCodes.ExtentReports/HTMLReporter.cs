using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using HtmlAgilityPack;

using RazorEngine.Configuration;
using RazorEngine.Text;
using RazorEngine.Templating;
using RazorEngine;

using RelevantCodes.ExtentReports.View;

namespace RelevantCodes.ExtentReports
{
    internal class HTMLReporter : IReporter
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

        // HtmlDocument
        private HtmlDocument _doc;

        // folder where offline artifacts are stored
        private const string _offlineFolderParent = "extentreports";

        public virtual void Start(Report Report)
        {
            _report = Report;
            _doc = new HtmlDocument();

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
            SetStartedTime();
        }

        private void SetStartedTime()
        {
            _doc.LoadHtml(_extentSource);

            var startTime = _doc.DocumentNode.SelectNodes("//*[contains(@class, 'suite-started-time')]");

            startTime.ToList().ForEach(x => x.InnerHtml = _report.StartTime.ToString());

            _extentSource = _doc.DocumentNode.OuterHtml;
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

            AppendSystemInfo();

            File.WriteAllText(_filePath, _extentSource);
        }

        private void AppendSystemInfo()
        {
            if (_report.SystemInfo.Count == 0)
            {
                return;
            }

            HtmlNode table, tr, td;

            table = _doc.CreateElement("table");

            foreach(KeyValuePair<string, string> entry in _report.SystemInfo)
            {
                tr = _doc.CreateElement("tr");

                td = _doc.CreateElement("td");
                td.InnerHtml = entry.Key;
                tr.AppendChild(td);

                td = _doc.CreateElement("td");
                td.InnerHtml = entry.Value;
                tr.AppendChild(td);

                table.AppendChild(tr);
            }

            _doc.LoadHtml(_extentSource);

            var tbody = _doc.DocumentNode.SelectSingleNode("//div[@class='system-view']//tbody");
            tbody.InnerHtml = table.InnerHtml;

            _extentSource = _doc.DocumentNode.OuterHtml;
        }

        public virtual void AddTest()
        {
            string testSource = Engine.Razor.RunCompile(View.Test.Source, "testKey", typeof(Model.Test), _report.Test);//.Replace("<!--%Node%-->", nodeSource);

            testSource = _displayOrder == DisplayOrder.NewestFirst
                ? testSource + "<!--%Test%-->" 
                : "<!--%Test%-->" + testSource;

            _extentSource = _extentSource.Replace("<!--%Test%-->", testSource);
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
