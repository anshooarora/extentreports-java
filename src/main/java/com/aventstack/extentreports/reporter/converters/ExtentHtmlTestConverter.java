package com.aventstack.extentreports.reporter.converters;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.utils.DateUtil;
import com.aventstack.extentreports.utils.Reader;

class ExtentHtmlTestConverter {
	
	private static final Logger logger = Logger.getLogger(ExtentHtmlTestConverter.class.getName());

	private Document doc;
	private TestParserUtils parserUtils;
	private String docTimeStampFormat;
	
	public ExtentHtmlTestConverter(String filePath) {
		String html = Reader.readAllText(filePath);
		doc = Jsoup.parse(html);
		docTimeStampFormat = doc.getElementById("timeStampFormat").attr("content");
		parserUtils = new TestParserUtils();
	}
	
	public List<Test> parseAndGetTests() {
		Elements allTests = doc.select("#test-collection > .test");
		
		if (allTests.size() == 0)
			return null;
		
		List<Test> modelTestList = new ArrayList<>();
		Test test;
		
		for (Element testElement : allTests) {
			test = parseAndGetTest(testElement);
			modelTestList.add(test);
		}
		
		return modelTestList;
	}
	
	private Test parseAndGetTest(Element testElement) {
		Test test = new Test();
		test.setUseManualConfiguration(true);
		test.setLevel(0);
		
		String name = parserUtils.getName(testElement);
		test.setName(name);
		
		String description = parserUtils.getDescription(testElement);
		test.setDescription(description);
		
		Date startTime = parserUtils.getStartTime(testElement);
		test.setStartTime(startTime);
		
		Date endTime = parserUtils.getEndTime(testElement);
		test.setEndTime(endTime);
		
		if (!doc.select("body").first().hasClass("bdd-report"))
		    buildStandardTestDeps(testElement, test);
		else 
		    buildBddTestsDeps(testElement, test);
		
        test.end();
        
		Status status = parserUtils.getStatus(testElement);
		if (test.getStatus() != status) {
		    logger.log(Level.WARNING, "Woops.  Looks like something went wrong parsing your existing report.");
		    logger.log(Level.WARNING, "The current test status for " + name + ": " + status + " does not match the calculated status: " + test.getStatus());
		    logger.log(Level.WARNING, "Forcefully setting the status to: " + status);
			test.setStatus(status);
		}

		return test;
	}
	
	private void buildStandardTestDeps(Element testElement, Test test) {
	    List<TestAttribute> categoryCollection = parserUtils.getAttributes(Category.class, testElement);
        if (categoryCollection != null && !categoryCollection.isEmpty())
            for (TestAttribute c : categoryCollection)
                test.setCategory(c);
        
        List<TestAttribute> authorCollection = parserUtils.getAttributes(Author.class, testElement);
        if (authorCollection != null && !authorCollection.isEmpty())
            for (TestAttribute a : authorCollection)
                test.setAuthor(a);
        
        List<ScreenCapture> mediaList = parserUtils.getMediaElements(testElement);
        if (mediaList != null && !mediaList.isEmpty())
            for (Media m : mediaList)
                test.setScreenCapture((ScreenCapture) m); 
        
        ExtentHtmlLogConverter logConverter = new ExtentHtmlLogConverter(test, testElement);
        logConverter.parseAndAddLogsToTest();
        
        ExtentHtmlNodeConverter nodeConverter = new ExtentHtmlNodeConverter(test, testElement, 1, docTimeStampFormat);
        nodeConverter.parseAndAddNodes();
	}
	
	private void buildBddTestsDeps(Element testElement, Test test) {
	    ExtentHtmlBddNodesConverter nodeConverter = new ExtentHtmlBddNodesConverter(test, testElement);
	    nodeConverter.parseAndAddNodes();
	}
	
	class TestParserUtils {
		
		public String getName(Element test) {
			return test.select(".test-name").first().html();
		}
		
		public Status getStatus(Element test) {
			String status = test.attr("status").toUpperCase();
			return Status.valueOf(status);
		}
		
		public String getDescription(Element test) {
			Elements testDesc = test.select(".test-desc");
			if (!testDesc.isEmpty())
				return testDesc.first().html();
			
			return null;
		}
		
		public Date getStartTime(Element test) {
			Element startTime = test.select(".start-time").first();
			
			if (startTime != null)
			    return DateUtil.parse(startTime.text(), docTimeStampFormat);
			
			return Calendar.getInstance().getTime();
		}
		
		public Date getEndTime(Element test) {
			Element endTime = test.select(".end-time").first();
			
			if (endTime != null)
			    return DateUtil.parse(endTime.text(), docTimeStampFormat);
			
			return Calendar.getInstance().getTime();
		}
		
		public List<ScreenCapture> getMediaElements(Element test) {
		    String selector = ":root > .test-content > .screenshots img";
		    Elements elements = test.select(selector);
		    List<ScreenCapture> scList = null;
		    ScreenCapture sc = null;
		    
		    if (!elements.isEmpty()) {
		        scList = new ArrayList<ScreenCapture>();
		        
		        for (Element element : elements) {
		            String src = element.attr("data-src");
		            sc = new ScreenCapture();		            
		            sc.setPath(src);
		            scList.add(sc);
		        }
		    }
		    
		    return scList;
		}
		
		@SuppressWarnings("unchecked")
        public List<TestAttribute> getAttributes(@SuppressWarnings("rawtypes") Class clazz, Element test) {
			List<TestAttribute> attrCollection = null;
			TestAttribute attr;
			
			String selector = clazz == Category.class
					? ":root > .test-content > .test-attributes > .category-list > .category"
					: ":root > .test-content > .test-attributes > .author-list > .author";
			
			Elements elements = test.select(selector);
			
			if (!elements.isEmpty()) {
				attrCollection = new ArrayList<TestAttribute>();
				
				for (Element element : elements) {
					String attrName = element.text();
					try {
                        attr = (TestAttribute) clazz.getDeclaredConstructor(String.class).newInstance(attrName);
                        attrCollection.add(attr);
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                        e.printStackTrace();
                    }
				}
			}
			
			return attrCollection;
		}	
	}
	
}
