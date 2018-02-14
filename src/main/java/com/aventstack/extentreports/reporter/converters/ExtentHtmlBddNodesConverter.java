package com.aventstack.extentreports.reporter.converters;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Background;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;

public class ExtentHtmlBddNodesConverter {

    private static final Logger logger = Logger.getLogger(ExtentHtmlBddNodesConverter.class.getName());
    
    private Test test;
    private Element testElement;
    
    public ExtentHtmlBddNodesConverter(Test test, Element testElement) {
        this.test = test;
        this.testElement = testElement;
    }
    
    public void parseAndAddNodes() {
        Elements nodeElementList = testElement.select(".test-content > .node");
        
        if (nodeElementList.size() == 0)
            return;
        
        for (Element nodeElement : nodeElementList) {
            parseAndAddNode(nodeElement);
        }
    }
    
    public void parseAndAddNode(Element nodeElement) {
        Status status;
        
        Test node = new Test(); 
        test.getNodeContext().add(node);
        node.setUseManualConfiguration(true);
        node.setParent(test);
        node.setLevel(1);
        
        Class<? extends IGherkinFormatterModel> bddType = getBddType(nodeElement);
        node.setBehaviorDrivenType(bddType);
        
        Element descText =  nodeElement.select(".desc-text").first();
        String description = descText != null
                ? nodeElement.select(".desc-text").first().html()
                : "";
        node.setName(description);        
        
        Elements steps = nodeElement.select(".steps > li");
        Test grandchild;
        for (Element step : steps) {
            grandchild = new Test();
            grandchild.setLevel(2);
            grandchild.setParent(node);
            node.getNodeContext().add(grandchild);
            
            bddType = getBddType(step);
            grandchild.setBehaviorDrivenType(bddType);
            
            status = getStatus(step);
            grandchild.setStatus(status);
            
            String name = step.select(".bdd-step-name").first().html();
            grandchild.setName(name);

            Log l = new Log(grandchild);
            grandchild.getLogContext().add(l);
            l.setSequence(0);
            l.setStatus(status);
            
            Element pre = step.select(".pre").first();
            String preText = pre != null
                    ? pre.html()
                    : null;
            if (preText != null)
                l.setDetails(preText);
            
            grandchild.end();
        }
        
        node.end();
        
        status = getStatus(nodeElement);
        if (node.getStatus() != status) {
            logger.log(Level.WARNING, "Woops.  Looks like something went wrong parsing your existing report.");
            logger.log(Level.WARNING, "The current test status for " + descText + ": " + status + " does not match the calculated status: " + node.getStatus());
            logger.log(Level.WARNING, "Forcefully setting the status to: " + status);
            node.setStatus(status);
        }
    }
    
    private Class<? extends IGherkinFormatterModel> getBddType(Element el) {
        if (el.hasClass("background"))
            return Background.class;
        
        if (el.hasClass("scenario"))
            return Scenario.class;
        
        if (el.hasClass("given"))
            return Given.class;
        
        if (el.hasClass("when"))
            return When.class;
        
        if (el.hasClass("Then"))
            return Then.class;

        return And.class;
    }
    
    private Status getStatus(Element node) {
        String status = node.attr("status").toUpperCase();
        return Status.valueOf(status);
    }
    
}
