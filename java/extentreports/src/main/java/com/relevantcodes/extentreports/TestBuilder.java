/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.Icon;
import com.relevantcodes.extentreports.source.StepHtml;
import com.relevantcodes.extentreports.source.TestHtml;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

class TestBuilder {
    // number of log columns (3 = default)
    // Timestamp | Status | Details
    private static int logSize;
    
    /**
     * Converts Model.Test into a Jsoup element represented as com.relevantcodes.extentreports.source.TestHtml
     * 
     * @param test {@link Test} 
     * @return {@link Element} Jsoup element
     */
    public static Element getHTMLTest(Test test) {
        logSize = test.getLogColumnSize();

        String testSource = TestHtml.getSource(logSize);
        
        Document doc = Jsoup.parseBodyFragment(testSource);
        
        // if description is null, hide the element
        if (test.getDescription().equals("")) {
            doc.select(".test-desc").first().addClass("hide");
        }
        
        // testName + internal-warnings
        doc.select(".test-name").first().text(test.getName() + TestHtml.getWarningSource(test.getInternalWarning()));
        
        // id
        doc.select(".test").first().attr("extentId", test.getId().toString());
        
        // test status
        doc.select(".test").first().addClass(test.getStatus().toString());
        doc.select(".test-status").first().addClass(test.getStatus().toString()).text(test.getStatus().toString());
        
        // test start time
        doc.select(".test-started-time").first().text(DateTimeUtil.getFormattedDateTime(test.getStartedTime(), LogSettings.getLogDateTimeFormat()));
        
        // test end times
        doc.select(".test-ended-time").first().text(DateTimeUtil.getFormattedDateTime(test.getEndedTime(), LogSettings.getLogDateTimeFormat()));
        
        // test time taken
        doc.select(".test-time-taken").first().text(DateTimeUtil.getDiff(test.getEndedTime(), test.getStartedTime()));
        
        // test description
        doc.select(".test-desc").first().text(test.getDescription());
        
        // test categories
        Element catDiv = doc.select(".categories").first();

        for (TestAttribute attr : test.getCategoryList()) {
            catDiv.appendChild(Jsoup.parseBodyFragment(TestHtml.getCategorySource()).select(".category").first().text(attr.getName()));
            doc.select(".category-assigned").first().addClass(attr.getName().toLowerCase());
        }
        
        // marker for tests with child-nodes
        if (test.hasChildNodes) {
        	doc.select(".test").first().addClass("hasChildren");
        }
        
        Document details;
    	
        // 3 columns by default
        String stepSource = StepHtml.getSource(logSize);

        Iterator<Log> iter = test.logIterator();
        Log log;
        
        // test logs
        while (iter.hasNext()) {
            log = iter.next();

            Document tr = Jsoup.parseBodyFragment(stepSource);
             
            // timestamp
            tr.select("td.timestamp").first().text(DateTimeUtil.getFormattedDateTime(log.getTimestamp(), LogSettings.getLogTimeFormat()));
             
            // status
            tr.select("td.status").first().addClass(log.getLogStatus().toString());
            tr.select("td.status").first().attr("title", log.getLogStatus().toString());
            tr.select("td.status > i").first().addClass("fa-" + Icon.getIcon(log.getLogStatus()));
            
            // stepName
            if (stepSource.equals(StepHtml.getSource(4))) {
                tr.select(".step-name").first().text(log.getStepName());
            }
             
            // details
            details = Jsoup.parse(log.getDetails().replaceAll("(\\r\\n|\\n)", "<br />"));
            tr.select(".step-details").first().text(details.select("body").first().html());
             
            doc.select("tbody").first().appendChild(tr.select("tr").first());
        }
        
        doc = addChildTests(test, doc, 1);
        
        return doc.select(".collection-item").first();
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
        	logSize = node.getLogColumnSize();
            
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
            li.select(".test-started-time").first().text(DateTimeUtil.getFormattedDateTime(node.getStartedTime(), LogSettings.getLogDateTimeFormat()));
            
            // end time
            li.select(".test-ended-time").first().text(DateTimeUtil.getFormattedDateTime(node.getEndedTime(), LogSettings.getLogDateTimeFormat()));
            
            // time taken
            li.select(".test-time-taken").first().text(hours + "h " + mins + "m " + secs + "s");
            
            Iterator<Log> iter = node.logIterator();
            
            if (iter.hasNext()) {
                li.select(".test-node").first().addClass(node.getStatus().toString());
                li.select(".test-status").first().addClass(node.getStatus().toString()).text(node.getStatus().toString());
                
                stepSource = StepHtml.getSource(logSize);

                Log log;
                
                while (iter.hasNext()) {
                	log = iter.next();
                	
                    Document tr = Jsoup.parseBodyFragment(stepSource);
                    
                    // timestamp
                    tr.select("td.timestamp").first().text(DateTimeUtil.getFormattedDateTime(log.getTimestamp(), LogSettings.getLogTimeFormat()));
                     
                    // status
                    tr.select("td.status").first().addClass(log.getLogStatus().toString());
                    tr.select("td.status").first().attr("title", log.getLogStatus().toString());
                    tr.select("td.status > i").first().addClass("fa-" + Icon.getIcon(log.getLogStatus()));
                    
                    // stepName
                    if (stepSource.equals(StepHtml.getSource(4))) {
                        tr.select(".step-name").first().text(log.getStepName());
                    }
                     
                    // details
                    tr.select(".step-details").first().append(log.getDetails());
                     
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
}
