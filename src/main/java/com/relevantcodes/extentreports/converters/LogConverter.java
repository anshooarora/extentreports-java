package com.relevantcodes.extentreports.converters;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.relevantcodes.extentreports.LogSettings;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

public class LogConverter extends LogSettings {
	private Element test;
	private Boolean isChildNode = false;
	
	public List<Log> getLogList() {
		 Elements logList = isChildNode 
				? test.select(".collapsible-body tbody > tr") 
				: test.select(".test-body > .test-steps > table > tbody > tr");
        
        ArrayList<Log> extentLogList = new ArrayList<Log>();
        Log extentLog;
        
        for (Element log : logList) {
            extentLog = new Log();
            
            extentLog.setTimestamp(
                    DateTimeUtil.getDate(
                            log.select(".timestamp").first().text(), 
                            getLogTimeFormat()
                    )
            );
            
            if (log.select(".step-name").size() == 1) {
                extentLog.setStepName(log.select(".step-name").first().text());
            }
            
            LogStatus status = getStatusStringMarkup(log.select(".status").first());
            extentLog.setLogStatus(status);
            
            extentLog.setDetails(log.select(".step-details").first().html());
            
            extentLogList.add(extentLog);
        }
        
        return extentLogList;
	}
	
	private LogStatus getStatusStringMarkup(Element log) {
		if (log.hasClass("pass")) {
			return LogStatus.PASS;
		}
		else if (log.hasClass("fail")) {
			return LogStatus.FAIL;
		}
		else if (log.hasClass("fatal")) {
			return LogStatus.FATAL;
		}
		else if (log.hasClass("error")) {
			return LogStatus.ERROR;
		}
		else if (log.hasClass("warning")) {
			return LogStatus.WARNING;
		}
		else if (log.hasClass("info")) {
			return LogStatus.INFO;
		}
		else if (log.hasClass("skip")) {
			return LogStatus.SKIP;
		}
		
		return LogStatus.UNKNOWN;
	}
	
	public LogConverter(Element test, Boolean isChildNode) {
		this.test = test;
		this.isChildNode = isChildNode;
	}
	
	public LogConverter(Element test) {
		this.test = test;
	}
}
