using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using HtmlAgilityPack;

using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports.Converters
{
    class NodeConverter : IConvertible<Test>
    {
        private HtmlNode _nodeListNode;
        private ExtentTest _extentTest;

        public NodeConverter(HtmlNode ULCollapsibleNodeListNode, ExtentTest ExtentTest)
        {
            _nodeListNode = ULCollapsibleNodeListNode;
            _extentTest = ExtentTest;
        }

        public List<Test> Convert()
        {
            List<Test> nodeList = null;

            var li = _nodeListNode.ChildNodes.Where(x => x.Name.Equals("li"));

            if (li != null & li.Any())
            {
                nodeList = new List<Test>();

                li.ToList().ForEach(node =>
                {
                    var test = new Test();
                    test.ChildNode = true;

                    var nodeName = node.Descendants("div").Where(x => x.Attributes["class"].Value.Contains("test-node-name")).First().InnerHtml;
                    test.Name = nodeName;

                    //var status = node.Descendants("span").Where(x => x.Attributes["class"].Value.Contains("test-status")).First().InnerText;
                    //test.Status = (LogStatus) Enum.Parse(typeof(LogStatus), status);

                    var startTime = node.Descendants("span").Where(x => x.Attributes["class"].Value.Contains("test-started-time")).First().InnerText;
                    test.StartTime = System.Convert.ToDateTime(startTime);

                    var endTime = node.Descendants("span").Where(x => x.Attributes["class"].Value.Contains("test-started-time")).First().InnerText;
                    test.EndTime = System.Convert.ToDateTime(endTime);

                    var testStepsNode = node.Descendants("div").Where(x => x.Attributes["class"].Value.Contains("test-steps")).First();

                    var logs = new LogConverter(testStepsNode).Convert();
                    test.LogList = logs;

                    test.PrepareFinalize();
                    test.HasEnded = true;

                    nodeList.Add(test);
                });
            }

            return nodeList;
        }
    }
}
