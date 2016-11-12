package com.aventstack.extentreports.reporter.converters;

import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.utils.DateUtil;

public class ExtentHtmlLogConverter {
	
	private static final String DATE_FORMAT = "hh:mm:ss a";
	
	private Element testElement;
	private Test test;
	
	public ExtentHtmlLogConverter(Test test, Element testElement) {
		this.test = test;
		this.testElement = testElement;
	}
	
	public void parseAndAddLogsToTest() {
		if (test.getLevel() == 0) {
			addHtmlLogsToParent();
			return;
		}
		
		addHtmlLogsToNode();
	}

	private void addHtmlLogsToParent() {
		Elements testSteps = testElement.select(":root > .test-content > .test-steps");
		
		if (!testSteps.isEmpty()) {
			Elements logs = testSteps.first().select(".log"); 
			addLogsFromPreExistingMarkup(logs);
		}
	}
	
	private void addHtmlLogsToNode() {
		Elements nodeSteps = testElement.select(":root > .collapsible-body > .node-steps");
		
		if (!nodeSteps.isEmpty()) {
			Elements logs = nodeSteps.first().select(".log");
			addLogsFromPreExistingMarkup(logs);
		}
	}
	
	private void addLogsFromPreExistingMarkup(Elements logs) {
		for (Element log : logs) {
			Status status = getStatus(log);
			Date timestamp = getTimestamp(log.select(".timestamp").first());
			String details = getDetails(log.select(".step-details").first());
			
			Log logModel = new Log(test);
			logModel.setStatus(status);
			logModel.setDetails(details);
			logModel.setTimestamp(timestamp);

			test.getLogContext().add(logModel);
		}
	}
	
	private Status getStatus(Element trLog) {
		String status = trLog.attr("status").toUpperCase();
		return Status.valueOf(status);
	}
	
	private Date getTimestamp(Element tdTimestamp) {
		String timestamp = tdTimestamp.text();
		return DateUtil.parse(timestamp, DATE_FORMAT);
	}
	
	private String getDetails(Element tdDetails) {
		String details = tdDetails.html();
		return details;
	}
	
}
