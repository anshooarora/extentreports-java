package com.relevantcodes.extentreports.converters;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogSettings;
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
		
		Elements allTests = doc.select(".test");
		
        ExtentTest extentTest;
		
        // build Test model
        for (Element test : allTests) {
            String name = test.select(".test-name").first().html().trim();
            String description = test.select(".test-desc").first().html().trim();
            
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
            	extentTest.getTest().hasChildNodes = true;
            	extentTest.getTest().setNodeList(nodeList);
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
