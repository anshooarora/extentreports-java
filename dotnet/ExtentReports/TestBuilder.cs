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
    using System.Linq;
    using System.Text;

    using Model;
    using Source;

    internal class TestBuilder
    {
        public static string GetSource(Test test) {
    	    if (test.IsChildNode) {
    		    return "";
    	    }
    	
            var testSource = TestHtml.GetSource(3);
            var stepSource = StepHtml.GetSource(2);

            if (test.Logs.Count > 0 && test.Logs[0].StepName != "")
            {
                testSource = TestHtml.GetSource(4);
                stepSource = StepHtml.GetSource(-1);
            }

            if (test.Description == "")
            {
                testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("descVis"), "style='display:none;'");
            }
        
            string[] testFlags = { ExtentFlag.GetPlaceHolder("testName"),
        		    ExtentFlag.GetPlaceHolder("testStatus"),
        		    ExtentFlag.GetPlaceHolder("testStartTime"),
        		    ExtentFlag.GetPlaceHolder("testEndTime"),
        		    ExtentFlag.GetPlaceHolder("testTimeTaken"),
        		    ExtentFlag.GetPlaceHolder("testDescription"),
        		    ExtentFlag.GetPlaceHolder("descVis"),
        		    ExtentFlag.GetPlaceHolder("category"),
        		    ExtentFlag.GetPlaceHolder("testWarnings")
            };            
            string[] testValues = { test.Name,
        		    test.Status.ToString().ToLower(),
        		    test.StartedTime.ToString(),
        		    test.EndedTime.ToString(),
        		    (test.EndedTime - test.StartedTime).Minutes + "m " + (test.EndedTime - test.StartedTime).Seconds + "s",
        		    test.Description,
        		    "",
        		    "",
        		    TestHtml.GetWarningSource(test.InternalWarning)
            };
        
            testSource = SourceBuilder.Build(testSource, testFlags, testValues);
        
            foreach (TestAttribute attr in test.CategoryList) {
                   testSource = testSource
                        .Replace(ExtentFlag.GetPlaceHolder("testCategory"), TestHtml.GetCategorySource() + ExtentFlag.GetPlaceHolder("testCategory"))
                        .Replace(ExtentFlag.GetPlaceHolder("category"), attr.GetName());
            }

            var stepSrc = StepHtml.GetSource(2);
        
            string[] stepFlags = { ExtentFlag.GetPlaceHolder("step"),
        		    ExtentFlag.GetPlaceHolder("timeStamp"),
        		    ExtentFlag.GetPlaceHolder("stepStatusU"),
        		    ExtentFlag.GetPlaceHolder("stepStatus"),
        		    ExtentFlag.GetPlaceHolder("statusIcon"),
        		    ExtentFlag.GetPlaceHolder("stepName"),
        		    ExtentFlag.GetPlaceHolder("details")
            };
            string[] stepValues;
        
            if (test.Logs.Count > 0) {
                if (test.Logs[0].StepName != "") {
                    stepSrc = StepHtml.GetSource(3);
                }
            
                for (int ix = 0; ix < test.Logs.Count; ix++) {
            	    stepValues = new string[] { stepSrc + ExtentFlag.GetPlaceHolder("step"),
            			    test.Logs[ix].Timestamp.ToShortTimeString(),
            			    test.Logs[ix].LogStatus.ToString().ToUpper(),
            			    test.Logs[ix].LogStatus.ToString().ToLower(),
            			    Icon.GetIcon(test.Logs[ix].LogStatus),
            			    test.Logs[ix].StepName,
            			    test.Logs[ix].Details
            	    };
            	
            	    testSource = SourceBuilder.Build(testSource, stepFlags, stepValues);
                }
            }
        
            testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("step"), "");
        
            testSource = AddChildTests(test, testSource, 1);

            return testSource;
        }

        private static string AddChildTests(Test test, String testSource, int nodeLevel) {
    	    string nodeSource, stepSrc = "";
            string[] testValues, stepValues;
        
            string[] testFlags = { ExtentFlag.GetPlaceHolder("nodeList"),
        		    ExtentFlag.GetPlaceHolder("nodeName"),
        		    ExtentFlag.GetPlaceHolder("nodeStartTime"),
        		    ExtentFlag.GetPlaceHolder("nodeEndTime"),
        		    ExtentFlag.GetPlaceHolder("nodeTimeTaken"),
        		    ExtentFlag.GetPlaceHolder("nodeLevel")
            };
            string[] stepFlags = { ExtentFlag.GetPlaceHolder("nodeStep"),
        		    ExtentFlag.GetPlaceHolder("timeStamp"),
        		    ExtentFlag.GetPlaceHolder("stepStatusU"),
        		    ExtentFlag.GetPlaceHolder("stepStatus"),
        		    ExtentFlag.GetPlaceHolder("statusIcon"),
        		    ExtentFlag.GetPlaceHolder("stepName"),
        		    ExtentFlag.GetPlaceHolder("details")
            };

            foreach (Test node in test.NodeList) {
                nodeSource = TestHtml.GetNodeSource(3);
            
                if (node.Logs.Count > 0 && node.Logs[0].StepName != "") {
                    nodeSource = TestHtml.GetNodeSource(4);
                }

                testValues = new string[] { nodeSource + ExtentFlag.GetPlaceHolder("nodeList"),
	            	    node.Name,
	            	    node.StartedTime.ToString(),
	            	    node.EndedTime.ToString(),
	            	    (node.EndedTime - node.StartedTime).Minutes + "m " + (node.EndedTime - node.StartedTime).Seconds + "s",
	            	    "node-" + nodeLevel + "x"
                };
            
                testSource = SourceBuilder.Build(testSource, testFlags, testValues);

                if (node.Logs.Count > 0) {
            	    testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("nodeStatus"), node.Status.ToString().ToLower());

                    stepSrc = StepHtml.GetSource(2);
            	
	                if (node.Logs[0].StepName != "") {
                        stepSrc = StepHtml.GetSource(3);
	                }
	            
	                for (int ix = 0; ix < node.Logs.Count; ix++) {
	            	    stepValues = new string[] { stepSrc + ExtentFlag.GetPlaceHolder("nodeStep"),
	            			    node.Logs[ix].Timestamp.ToShortTimeString(),
	            			    node.Logs[ix].LogStatus.ToString().ToUpper(),
	            			    node.Logs[ix].LogStatus.ToString().ToLower(),
	            			    Icon.GetIcon(node.Logs[ix].LogStatus),
	            			    node.Logs[ix].StepName,
	            			    node.Logs[ix].Details	            		
		                };
	            	
	            	    testSource = SourceBuilder.Build(testSource, stepFlags, stepValues);
	                }
                }

                testSource = SourceBuilder.Build(testSource, new string[] { ExtentFlag.GetPlaceHolder("step"), ExtentFlag.GetPlaceHolder("nodeStep") }, new string[] { "", "" });
            
                if (node.HasChildNodes) {
            	    testSource = AddChildTests(node, testSource, ++nodeLevel);
            	    --nodeLevel;
                }
            }
        
    	    return testSource;
        }

        public static string GetQuickSummary(Test test)
        {
            if (test.IsChildNode) 
            {
    		    return "";
    	    }
    	
    	    string src = TestHtml.GetSourceQuickView();
    	    var lc = new LogCounts().GetLogCounts(test);
    	
    	    string[] flags = { ExtentFlag.GetPlaceHolder("testName"), 
    			    ExtentFlag.GetPlaceHolder("testWarnings"),
    			    ExtentFlag.GetPlaceHolder("currentTestPassedCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestFailedCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestFatalCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestErrorCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestWarningCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestInfoCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestSkippedCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestUnknownCount"),
    			    ExtentFlag.GetPlaceHolder("currentTestRunStatus"),
    			    ExtentFlag.GetPlaceHolder("currentTestRunStatusU")
    	    };
    	    string[] values = { test.Name,
    			    TestHtml.GetWarningSource(test.InternalWarning),
    			    lc.Pass.ToString(),
    			    lc.Fail.ToString(),
    			    lc.Fatal.ToString(),
    			    lc.Error.ToString(),
    			    lc.Warning.ToString(),
    			    lc.Info.ToString(),
    			    lc.Skip.ToString(),
    			    lc.Unknown.ToString(),
    			    test.Status.ToString().ToLower(),
    			    test.Status.ToString().ToUpper()
    	    };
    	
    	    src = SourceBuilder.Build(src, flags, values);
        
            return src;
        }
    }
}
