package com.relevantcodes.extentreports;

import java.util.ArrayList;

import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.ObjectEmbedHtml;

class ImageViewBuilder {
	public static String getSource(ArrayList<ScreenCapture> imgList) {
		String src = "";
		
		if (imgList.size() == 0) {
			src = ObjectEmbedHtml.getFullWidth().replace(ExtentFlag.getPlaceHolder("imagesViewValue"), "No images were embed for the tests in this report.");
			
			return src;
		}
		
		for (ScreenCapture s : imgList) {
			src += ObjectEmbedHtml.getColumn();
			
			src = src.replace(ExtentFlag.getPlaceHolder("imagesViewParam"), s.testName)
					.replace(ExtentFlag.getPlaceHolder("imagesViewValue"), s.src); 
		}
		
		return src;
	}
}
