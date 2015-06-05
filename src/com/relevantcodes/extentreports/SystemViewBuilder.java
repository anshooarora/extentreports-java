package com.relevantcodes.extentreports;

import java.util.Map;

import com.relevantcodes.extentreports.model.SystemProperties;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.SystemInfoHtml;

class SystemInfoViewBuilder {
	public static String getSource(SystemProperties systemProperties) {
		String src = "";
		
		for (Map.Entry<String, String> entry : systemProperties.info.entrySet()) {
			src += SystemInfoHtml.getColumn();
			
			src = src.replace(ExtentFlag.getPlaceHolder("systemInfoParam"), entry.getKey())
						.replace(ExtentFlag.getPlaceHolder("systemInfoValue"), entry.getValue());
		}
		
		return src;
	}
}
