package com.aventstack.extentreports.reporter.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.utils.DateUtil;

class ExtentHtmlNodeConverter {
    
    private static final Logger logger = Logger.getLogger(ExtentHtmlNodeConverter.class.getName());
    
    private Test test;
    private Element testElement;
    private NodeParserUtils parserUtils;
    private Integer level;
    private String docTimeStampFormat;
    
    public ExtentHtmlNodeConverter(Test test, Element testElement, Integer level, String docTimeStampFormat) {
    	this.test = test;
    	this.testElement = testElement;
    	this.level = level;
    	this.docTimeStampFormat = docTimeStampFormat;
    	
    	parserUtils = new NodeParserUtils();
    }
    
    public void parseAndAddNodes() {
    	Elements nodeElementList = level-1 == 0
    			? testElement.select(":root > .test-content > .node-list > .node")
    			: testElement.select(".node-list > .node");
        
        if (nodeElementList.size() == 0)
            return;
        
        for (Element nodeElement : nodeElementList) {
        	parseAndAddNode(nodeElement);
        }
    }
    
    private void parseAndAddNode(Element nodeElement) {
    	Test node = new Test(); 
    	node.setUseManualConfiguration(true);
    	node.setParent(test);
        node.setLevel(level);
        test.getNodeContext().add(node);
    	
    	String name = parserUtils.getName(nodeElement);
    	node.setName(name);
    	
    	Date startTime = parserUtils.getStartTime(nodeElement);
    	node.setStartTime(startTime);
    	
    	Element body = nodeElement.select(":root > .collapsible-body").first();
        
        if (body != null) {
        	List<TestAttribute> categoryCollection = parserUtils.getAttributes(Category.class, nodeElement);
        	List<TestAttribute> authorCollection = parserUtils.getAttributes(Author.class, nodeElement);
            
            if (categoryCollection != null && !categoryCollection.isEmpty())
                for (TestAttribute c : categoryCollection)
                    node.setCategory(c);
            
            if (authorCollection != null && !authorCollection.isEmpty())
                for (TestAttribute a : authorCollection)
                    node.setAuthor(a);
            
            ExtentHtmlLogConverter logConverter = new ExtentHtmlLogConverter(node, nodeElement);
            logConverter.parseAndAddLogsToTest();
        }
        
        ExtentHtmlNodeConverter nodeConverter = new ExtentHtmlNodeConverter(node, nodeElement, level + 1, docTimeStampFormat);
        nodeConverter.parseAndAddNodes();
        node.end();
        
        Status status = parserUtils.getStatus(nodeElement);
        
        if (node.getStatus() != status) {
			logger.log(Level.WARNING, "Woops.  Looks like something went wrong parsing your existing report.");
			logger.log(Level.WARNING, "The current test status for " + name + ": " + status + " does not match the calculated status: " + node.getStatus());
			logger.log(Level.WARNING, "Forcefully setting the status to: " + status);
			node.setStatus(status);
		}
    }
        
    class NodeParserUtils {
    
	    private String getName(Element node) {
	        String name = node
	                .select(":root > .collapsible-header").first()
	                .select(".node-name").first()
	                .html();
	        return name;
	    }
	    
	    private Status getStatus(Element node) {
	        String status = node.attr("status").toUpperCase();
	        return Status.valueOf(status);
	    }
	    
	    private Date getStartTime(Element node) {
	        String startTime = node.select(".node-time").first().text();
	        return DateUtil.parse(startTime, docTimeStampFormat);
	    }
	    
	    private List<TestAttribute> getAttributes(@SuppressWarnings("rawtypes") Class clazz, Element node) {
	        List<TestAttribute> attrCollection = null;
	        TestAttribute attr;
	        
	        String selector = clazz == Category.class
	                ? ":root > .category-list > .category"
	                : ":root > .author-list > .author";
	        
	        Elements elements = node.select(selector);
	        
	        if (!elements.isEmpty()) {
	            attrCollection = new ArrayList<TestAttribute>();
	            
	            for (Element element : elements) {
	                String attrName = element.text();
	                try {
                        attr = (TestAttribute) clazz.newInstance();
                        attr.setName(attrName);
                        attrCollection.add(attr);
                    } catch (InstantiationException|IllegalAccessException e) {
                        e.printStackTrace();
                    }
	            }
	        }
	        
	        return attrCollection;
	    }
    }
    
}
