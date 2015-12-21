using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using HtmlAgilityPack;

using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports.Converters
{
    class LogConverter : IConvertible<Log>
    {
        private HtmlNode _testStepsNode;

        public LogConverter(HtmlNode TestStepsNode)
        {
            _testStepsNode = TestStepsNode;
        }

        public List<Log> Convert()
        {
            List<Log> logList = null;

            var table = _testStepsNode.Descendants("table").Where(x => x.Descendants("tbody") != null & x.Descendants("tbody").Any());
            
            if (table != null & table.Any())
            {
                var tbody = table.First().Descendants("tbody").First();
                var tr = tbody.ChildNodes.Where(x => x.Name.Equals("tr"));

                logList = new List<Log>(); ;

                tr.ToList().ForEach(row => 
                {
                    var log = new Log();

                    var cellCount = row.ChildNodes.Where(x => x.Name.Equals("td")).Count();

                    var statusNode = row.SelectSingleNode(".//td[contains(@class, 'status')]");
                    var status = GetStatus(statusNode.GetAttributeValue("class", "unknown"));

                    var timestamp = row.SelectSingleNode(".//td[contains(@class, 'timestamp')]").InnerText;
                    var details = row.SelectSingleNode(".//td[contains(@class, 'step-details')]").InnerHtml;

                    if (cellCount == 4)
                    {
                        var stepName = row.SelectSingleNode(".//td[contains(@class, 'step-name')]").InnerHtml;
                        log.StepName = stepName;
                    }

                    log.Timestamp = System.Convert.ToDateTime(timestamp);
                    log.LogStatus = status;
                    log.Details = details;

                    logList.Add(log);
                });
            }

            return logList;
        }

        private LogStatus GetStatus(string HtmlClass)
        {
            HtmlClass = HtmlClass.ToLower();

            if (HtmlClass.Contains("pass")) return LogStatus.Pass;
            if (HtmlClass.Contains("fail")) return LogStatus.Fail;
            if (HtmlClass.Contains("fatal")) return LogStatus.Fatal;
            if (HtmlClass.Contains("error")) return LogStatus.Error;
            if (HtmlClass.Contains("warning")) return LogStatus.Warning;
            if (HtmlClass.Contains("skip")) return LogStatus.Skip;
            if (HtmlClass.Contains("info")) return LogStatus.Pass;

            return LogStatus.Unknown;
        }
    }
}
