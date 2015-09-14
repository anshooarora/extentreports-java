/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.Icon;
import com.relevantcodes.extentreports.source.StepHtml;
import com.relevantcodes.extentreports.source.TestHtml;
import com.relevantcodes.extentreports.support.DateTimeHelper;

class TestBuilder {
    // number of log columns (3 = default)
    // Timestamp | Status | Details
    private static int logSize;
    
    // adds a test with logs and nodes
    public static Element getParsedTest(Test test) {
        logSize = 3;

        // if the log contains StepName, use source with 4 columns
        // Timestamp | Status | StepName | Details
        if (test.getLog().size() > 0 && test.getLog().get(0).getStepName() != "") {
            logSize = 4;
        }
        
        String testSource = TestHtml.getSource(logSize);
        
        Document doc = Jsoup.parseBodyFragment(testSource);
        
        // if description is null, hide the element
        if (test.getDescription().equals("")) {
            doc.select(".test-desc").first().addClass("hide");
        }
        
        long diff = test.getEndedTime().getTime() - test.getStartedTime().getTime();
        long hours = diff / (60 * 60 * 1000) % 24;
        long mins = diff / (60 * 1000) % 60;
        long secs = diff / 1000 % 60;
        
        // testName + internal-warnings
        doc.select(".test-name").first().text(test.getName() + TestHtml.getWarningSource(test.getInternalWarning()));
        
        // id
        doc.select(".test").first().attr("extentId", test.getId().toString());
        
        // test status
        doc.select(".test, .test-status").first().addClass(test.getStatus().toString());
        doc.select(".test-status").first().addClass(test.getStatus().toString()).text(test.getStatus().toString());
        
        // test start time
        doc.select(".test-started-time").first().text(DateTimeHelper.getFormattedDateTime(test.getStartedTime(), LogSettings.logDateTimeFormat));
        
        // test end times
        doc.select(".test-ended-time").first().text(DateTimeHelper.getFormattedDateTime(test.getEndedTime(), LogSettings.logDateTimeFormat));
        
        // test time taken
        doc.select(".test-time-taken").first().text(hours + "h " + mins + "m " + secs + "s");
        
        // test description
        doc.select(".test-desc > span").first().text(test.getDescription());
        
        // test categories
        Element catDiv = doc.select(".categories").first();
        
        for (TestAttribute attr : test.getCategoryList()) {
            catDiv.appendChild(Jsoup.parseBodyFragment(TestHtml.getCategorySource()).select(".category").first().text(attr.getName()));
        }

        // test logs
        if (test.getLog().size() > 0) {
             // 3 columns by default
             String stepSource = StepHtml.getSource(logSize);

             for (int ix = 0; ix < test.getLog().size(); ix++) {
                 Document tr = Jsoup.parseBodyFragment(stepSource);
                 
                 // timestamp
                 tr.select("td").first().text(DateTimeHelper.getFormattedDateTime(test.getLog().get(ix).getTimestamp(), LogSettings.logTimeFormat));
                 
                 // status
                 tr.select("td.status").first().addClass(test.getLog().get(ix).getLogStatus().toString());
                 tr.select("td.status").first().attr("title", test.getLog().get(ix).getLogStatus().toString());
                 tr.select("td.status > i").first().addClass("fa-" + Icon.getIcon(test.getLog().get(ix).getLogStatus()));
                 
                 // stepName
                 if (stepSource.equals(StepHtml.getSource(4))) {
                     tr.select(".step-name").first().text(test.getLog().get(ix).getStepName());
                 }
                 
                 // details
                 tr.select(".step-details").first().text(test.getLog().get(ix).getDetails());
                 
                 doc.select("tbody").first().appendChild(tr.select("tr").first());
             }
        }
                        
        doc = addChildTests(test, doc, 1);
        
        return doc.select(".test").first();
    }

    // adds nodes to parent tests, eg:
    // parent-test
    //   node 1
    //   node 2
    //     node 3
    // ...
    private static Document addChildTests(Test test, Document parentDoc, int nodeLevel) {
        String nodeSource;
        long diff, hours, mins, secs;
        String stepSource = "";
        
        for (Test node : test.getNodeList()) {
            logSize = 3;

            // if stepName is set in logs, use 4 columns
            if (node.getLog().size() > 0 && node.getLog().get(0).getStepName() != "") {
                logSize = 4;
            }
            
            nodeSource = TestHtml.getNodeSource(logSize);
            
            diff = node.getEndedTime().getTime() - node.getStartedTime().getTime();
            hours = diff / (60 * 60 * 1000) % 24;
            mins = diff / (60 * 1000) % 60;
            secs = diff / 1000 % 60;
            
            Element li = Jsoup.parseBodyFragment(nodeSource).select("li").first();
            
            // add class to node
            // level 1: node-1x
            // level 2: node-2x
            // and so on..
            li.addClass("node-" + nodeLevel + "x").attr("extentId", node.getId().toString());
            
            // test-node name
            li.select(".test-node-name").first().text(node.getName());
            
            // start time
            li.select(".test-started-time").first().text(DateTimeHelper.getFormattedDateTime(node.getStartedTime(), LogSettings.logDateTimeFormat));
            
            // end time
            li.select(".test-ended-time").first().text(DateTimeHelper.getFormattedDateTime(node.getEndedTime(), LogSettings.logDateTimeFormat));
            
            // time taken
            li.select(".test-time-taken").first().text(hours + "h " + mins + "m " + secs + "s");
            
            if (node.getLog().size() > 0) {
                li.select(".test-node").first().addClass(node.getStatus().toString());
                li.select(".test-status").first().addClass(node.getStatus().toString()).text(node.getStatus().toString());
                
                stepSource = StepHtml.getSource(logSize);

                for (int ix = 0; ix < node.getLog().size(); ix++) {
                    Document tr = Jsoup.parseBodyFragment(stepSource);
                    
                    // timestamp
                    tr.select("td").first().text(DateTimeHelper.getFormattedDateTime(node.getLog().get(ix).getTimestamp(), LogSettings.logTimeFormat));
                     
                    // status
                    tr.select("td.status").first().addClass(node.getLog().get(ix).getLogStatus().toString());
                    tr.select("td.status").first().attr("title", node.getLog().get(ix).getLogStatus().toString());
                    tr.select("td.status > i").first().addClass("fa-" + Icon.getIcon(node.getLog().get(ix).getLogStatus()));
                    
                    // stepName
                    if (stepSource.equals(StepHtml.getSource(4))) {
                        tr.select(".step-name").first().text(node.getLog().get(ix).getStepName());
                    }
                     
                    // details
                    tr.select(".step-details").first().append(node.getLog().get(ix).getDetails());
                     
                    li.select("tbody").first().appendChild(tr.select("tr").first());
                }
            }
            
            parentDoc.select(".node-list").first().appendChild(li);
            
            if (node.hasChildNodes) {
                parentDoc = addChildTests(node, parentDoc, ++nodeLevel);
                --nodeLevel;
            }
        }
        
        return parentDoc;
    }
    
    // builds a row for quick-test-summary view
    // test-name | pass | fail | fatal | error | warning | info | skip | unknown | status
    public static Element getQuickTestSummary(Test test) {
        if (test.isChildNode) {
            return null;
        }
        
        // @see TestHtml.getSourceQuickView()
        String source = TestHtml.getSourceQuickView();
        LogCounts lc = new TestBuilder().new LogCounts().getLogCounts(test);

        Element tr = Jsoup.parseBodyFragment(source).select("tr").first();
        
        tr.select(".quick-view-test").first().text(test.getName()).attr("extentId", test.getId().toString()).parent().append(test.getInternalWarning());
        tr.select("td:nth-child(2)").first().text(String.valueOf(lc.pass));
        tr.select("td:nth-child(3)").first().text(String.valueOf(lc.fail));
        tr.select("td:nth-child(4)").first().text(String.valueOf(lc.fatal));
        tr.select("td:nth-child(5)").first().text(String.valueOf(lc.error));
        tr.select("td:nth-child(6)").first().text(String.valueOf(lc.warning));
        tr.select("td:nth-child(7)").first().text(String.valueOf(lc.info));
        tr.select("td:nth-child(8)").first().text(String.valueOf(lc.skip));
        tr.select("td:nth-child(9)").first().text(String.valueOf(lc.unknown));
        tr.select(".status").first().text(test.getStatus().toString()).addClass(test.getStatus().toString());
        
        return tr;
    }
    
    private class LogCounts {
        private int pass = 0;
        private int fail = 0;
        private int fatal = 0;
        private int error = 0;
        private int warning = 0;
        private int info = 0;
        private int skip = 0;
        private int unknown = 0;
        
        // counts each type of log for the test
        public LogCounts getLogCounts(Test test) {
            for (int ix = 0; ix < test.getLog().size(); ix++) {
                if (test.getLog().get(ix).getLogStatus() == LogStatus.PASS)
                    pass++; 
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.FAIL)
                    fail++;
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.FATAL)
                    fatal++;
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.ERROR)
                    error++;
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.WARNING)
                    warning++;
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.INFO)
                    info++;
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.SKIP)
                    skip++;
                else if (test.getLog().get(ix).getLogStatus() == LogStatus.UNKNOWN)
                    unknown++;
            }
            
            // recursively count status events
            for (Test node : test.getNodeList()) {
                getLogCounts(node);
            }
            
            return this;
        }

        public LogCounts() { }
    }
}
