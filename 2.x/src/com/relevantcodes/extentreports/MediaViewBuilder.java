package com.relevantcodes.extentreports;

import java.util.ArrayList;

import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.ObjectEmbedHtml;

public class MediaViewBuilder {
	public static String getSource(ArrayList<ScreenCapture> mediaList) {
		String src = "";
		
		if (mediaList == null || mediaList.size() == 0) {
			src = ObjectEmbedHtml.getFullWidth().replace(ExtentFlag.getPlaceHolder("objectViewValue"), "No media was embed for the tests in this report.");
			
			return src;
		}
		
		for (ScreenCapture sc : mediaList) {
			src += ObjectEmbedHtml.getColumn();
			
			src = src.replace(ExtentFlag.getPlaceHolder("objectViewParam"), sc.testName)
					.replace(ExtentFlag.getPlaceHolder("objectViewValue"), sc.src); 
		}
		
		return src;
	}
}
