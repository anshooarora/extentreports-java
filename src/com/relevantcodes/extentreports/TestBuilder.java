package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.Icon;
import com.relevantcodes.extentreports.source.StepHtml;
import com.relevantcodes.extentreports.source.TestHtml;

class TestBuilder {
	public static String getSource(Test test) {
		String src = TestHtml.getSource();
		
		if (test.description == null) {
			src = src.replace(ExtentFlag.getPlaceHolder("descVis"), "style='display:none;'");
		}
		
		src = src.replace(ExtentFlag.getPlaceHolder("testName"), test.name)
				.replace(ExtentFlag.getPlaceHolder("testStatus"), test.status.toString().toLowerCase())
				.replace(ExtentFlag.getPlaceHolder("testStartTime"), test.startedAt)
				.replace(ExtentFlag.getPlaceHolder("testEndTime"), test.endedAt)
				.replace(ExtentFlag.getPlaceHolder("testStatus"), test.status.toString().toLowerCase())
				.replace(ExtentFlag.getPlaceHolder("testDescription"), test.description)
				.replace(ExtentFlag.getPlaceHolder("descVis"), "");	
		
		String stepSrc = StepHtml.getSrc(2);
		
		if (test.log.size() > 0) {
			if (test.log.get(0).stepName != "") {
				stepSrc = StepHtml.getSrc(0);
			}
			
			for (int ix = 0; ix < test.log.size(); ix++) {
				src = src.replace(ExtentFlag.getPlaceHolder("step"), stepSrc + ExtentFlag.getPlaceHolder("step"))
						.replace(ExtentFlag.getPlaceHolder("timeStamp"), test.log.get(ix).timestamp)
						.replace(ExtentFlag.getPlaceHolder("stepStatusU"), test.log.get(ix).logStatus.toString().toUpperCase())
						.replace(ExtentFlag.getPlaceHolder("stepStatus"), test.log.get(ix).logStatus.toString().toLowerCase())
						.replace(ExtentFlag.getPlaceHolder("statusIcon"), Icon.getIcon(test.log.get(ix).logStatus))
						.replace(ExtentFlag.getPlaceHolder("stepName"), test.log.get(ix).stepName)
						.replace(ExtentFlag.getPlaceHolder("details"), test.log.get(ix).details);
			}
		}
		
		src = src.replace(ExtentFlag.getPlaceHolder("step"), "");
		
		return src;
	}
}
