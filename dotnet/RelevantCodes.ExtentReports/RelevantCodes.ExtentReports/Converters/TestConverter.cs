using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using HtmlAgilityPack;

using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports.Converters
{
    class TestConverter : IConvertible<ExtentTest>
    {
        private ExtentReports _extent;
        private string _filePath;

        public TestConverter(ExtentReports Extent, string FilePath)
        {
            _extent = Extent;
            _filePath = FilePath;
        }

        public List<ExtentTest> Convert()
        {
            var document = new HtmlAgilityPack.HtmlDocument();
            document.Load(_filePath);

            var documentNode = document.DocumentNode;

            var testCollectionNode = documentNode.Descendants("ul").Where(x => x.Id == "test-collection");

            if (testCollectionNode != null && testCollectionNode.Any() && testCollectionNode.First().ChildNodes.Any())
            {
                var tests = testCollectionNode.First().ChildNodes.Where(x => x.Name == "li");

                var list = new List<ExtentTest>();

                tests.ToList().ForEach(htmlTestNode => 
                {
                    var test = HTMLNodeToExtentTest(htmlTestNode);

                    _extent.EndTest(test);
                    list.Add(test);
                });

                return list;
            }

            return null;
        }

        private ExtentTest HTMLNodeToExtentTest(HtmlNode HtmlTestNode)
        {
            var testName = HtmlTestNode.Descendants("span")
                    .Where(x => x.Attributes["class"].Value.Contains("test-name"))
                    .First().InnerHtml;

            var description = HtmlTestNode.Descendants("div")
                .Where(x => x.Attributes["class"].Value.Contains("test-desc"))
                .First().InnerHtml;

            var extentTest = _extent.StartTest(testName, description);
            extentTest.GetTest().ID = new Guid(HtmlTestNode.Attributes["extentid"].Value);

            var status = HtmlTestNode.Descendants("span")
                .Where(x => x.Attributes["class"].Value.Contains("test-status"))
                .First().InnerText;
            
            var startTime = HtmlTestNode.Descendants("span")
                .Where(x => x.Attributes["class"].Value.Contains("test-started-time"))
                .First().InnerText;
            extentTest.GetTest().StartTime = System.Convert.ToDateTime(startTime);

            var endTime = HtmlTestNode.Descendants("span")
                .Where(x => x.Attributes["class"].Value.Contains("test-ended-time"))
                .First().InnerText;
            extentTest.GetTest().EndTime = System.Convert.ToDateTime(endTime);

            var categories = HtmlTestNode.Descendants("span")
                .Where(x => x.Attributes["class"].Value.Contains("category"));
            var authors = HtmlTestNode.Descendants("span")
                .Where(x => x.Attributes["class"].Value.Contains("author"));
            AssignTestAttributes(categories, authors, extentTest);

            var testStepsNode = HtmlTestNode.Descendants("div").Where(x => x.Attributes["class"].Value.Contains("test-steps"));
            ParseAndAddLogs(testStepsNode, extentTest);

            var nodeListNode = HtmlTestNode.Descendants("ul").Where(x => x.Attributes["class"].Value.Contains("node-list"));
            ParseAndAddNodes(nodeListNode, extentTest);

            return extentTest;
        }

        private void AssignTestAttributes(IEnumerable<HtmlNode> CategoriesNode, IEnumerable<HtmlNode> AuthorsNode, ExtentTest ExtentTest)
        {
            CategoriesNode.ToList().ForEach(cat =>
            {
                if (!string.IsNullOrEmpty(cat.InnerText.Trim()))
                {
                    ExtentTest.AssignCategory(cat.InnerText);
                }
            });

            AuthorsNode.ToList().ForEach(author =>
            {
                if (!string.IsNullOrEmpty(author.InnerText.Trim()))
                {
                    ExtentTest.AssignAuthor(author.InnerText);
                }
            });
        }

        private void ParseAndAddLogs(IEnumerable<HtmlNode> TestStepsNode, ExtentTest ExtentTest)
        {
            if (TestStepsNode != null & TestStepsNode.Any())
            {
                if (TestStepsNode.First().ChildNodes.Where(x => x.Name.Equals("table")).Count() == 0)
                {
                    return;
                }

                var logs = new LogConverter(TestStepsNode.First()).Convert();

                if (logs != null)
                {
                    ExtentTest.GetTest().LogList = logs;
                }
            }
        }

        private void ParseAndAddNodes(IEnumerable<HtmlNode> NodeListNode, ExtentTest ExtentTest)
        {
            if (NodeListNode != null & NodeListNode.Any())
            {
                ExtentTest.GetTest().ContainsChildNodes = true;

                var nodes = new NodeConverter(NodeListNode.First(), ExtentTest).Convert();

                if (nodes != null)
                {
                    ExtentTest.GetTest().NodeList.AddRange(nodes);
                }
            }
        }
    }
}
