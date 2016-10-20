package com.relevantcodes.extentreports.converters;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.relevantcodes.extentreports.*;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogSettings;
import com.relevantcodes.extentreports.model.ExceptionInfo;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.utils.DateTimeUtil;
import com.relevantcodes.extentreports.utils.FileReaderEx;

public class TestConverter extends LogSettings {
	private static final Logger logger = Logger.getLogger(TestConverter.class.getName());
	private Document doc;
	private File file;
	private ExtentReports extent;
	
	public void createTestList() {
		Elements body = doc.select("body.extent");
		
		if (body == null || body.size() == 0) {
			logger.log(
					Level.SEVERE, 
					"The supplied file " + file.getAbsolutePath() + " is not a valid Extent file. " +
					"Parsing failed, tests from the supplied file will not be listed in the current report."
			);
			
			return;
		}
		
        Elements allExceptions = doc.select(".exception-item");

        Hashtable<String, List<ExceptionInfo>> exceptions = new Hashtable<String, List<ExceptionInfo>>();
        for (Element element : allExceptions) {
            String exceptionName = element.select(".exception-name").text();

            Elements failelements = element.select(".fail").not(".label");
            failelements.addAll(element.select(".error"));
            failelements.addAll(element.select(".fatal"));
            failelements.addAll(element.select(".unknown"));
            failelements.addAll(element.select(".pass"));
            failelements.addAll(element.select(".warning"));
            failelements.addAll(element.select(".info"));
            failelements.addAll(element.select(".skip"));

            for (Element failelement : failelements) {
                String testId = failelement.select(".exception-link").attr("extentid").trim();
                String exceptionMessage = failelement.select(".exception-message").text();

                ExceptionInfo exceptionInfo = new ExceptionInfo();
                exceptionInfo.setExceptionName(exceptionName);
                exceptionInfo.setStackTrace(exceptionMessage);

                if (!exceptions.containsKey(testId)) {
                    exceptions.put(testId, new ArrayList<ExceptionInfo>());
                }
                exceptions.get(testId).add(exceptionInfo);
            }
        }

		Elements allTests = doc.select("#test-collection > li.test");
		
        ExtentTest extentTest;
		
        // build Test model
        for (Element test : allTests) {
            String name = test.select(".test-name").first().html().trim();
            String description = test.select(".test-desc").first().html().trim();
            String id = test.attr("extentid").trim();
            
            extentTest = extent.startTest(name, description);
            
            extentTest.getTest().setStartedTime(
            		DateTimeUtil.getDate(
            				test.select(".test-started-time").first().text(), 
            				LogSettings.getLogDateTimeFormat()
            		)
            );
            extentTest.getTest().setEndedTime(
            		DateTimeUtil.getDate(
            				test.select(".test-ended-time").first().text(),
            				LogSettings.getLogDateTimeFormat()
            		)
            );
            extentTest.getTest().setUUID(UUID.fromString(id));
            
            Elements categories = test.select(".category");
            
            for (Element category : categories) {
            	extentTest.assignCategory(category.text());
            }
            
            Elements authors = test.select(".author");
            
            for (Element author : authors) {
            	extentTest.assignAuthor(author.text());
            }
            
            LogConverter logConverter = new LogConverter(test);
            List<Log> logList = logConverter.getLogList();
            
            if (logList != null && logList.size() > 0) {
            	extentTest.getTest().setLog(logList);
            }
            
            ChildTestConverter nodeConverter = new ChildTestConverter(test);
            List<Test> nodeList = nodeConverter.getNodeList();
            
            if (nodeList != null && nodeList.size() > 0) {
            	extentTest.getTest().hasChildNodes(true);
            	extentTest.getTest().setNodeList(nodeList);
            }
            
            List<ExceptionInfo> exceptionsList = exceptions.get(id);
            if (exceptionsList != null) {
                for (ExceptionInfo exception : exceptionsList) {
                    exception.setTest((Test) extentTest.getTest());
                    extentTest.getTest().setException(exception);
                }
            }
            extent.endTest(extentTest);
        }
    }
	
	public TestConverter(ExtentReports extent, File file) {
		this.file = file;
		this.extent = extent;
		
		String source = FileReaderEx.readAllText(file);
		doc = Jsoup.parse(source);
	}
}
