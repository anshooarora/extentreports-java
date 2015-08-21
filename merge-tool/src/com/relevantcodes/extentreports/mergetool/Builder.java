package com.relevantcodes.extentreports.mergetool;

import java.io.File;

class Builder {
	private Extractor extractor;
	
	public void build(File file) {
		String sourceFile = "com/relevantcodes/extentreports/mergetool/STANDARD.min.html";
		String source = Resources.getText(sourceFile);
		
		String extentSource = source
				 .replace("<!--%%TEST%%-->", extractor.getTestSource())
				 .replace("<!--%%QUICKTESTSUMMARY%%-->", extractor.getQuickTestSummarySource())
				 .replace("<!--%%IMAGESVIEW%%-->", extractor.getImagesViewSource())
				 .replace("<!--%%SYSTEMINFOVIEW%%-->", extractor.getSystemViewSource());
		
		Writer.getInstance().write(file, extentSource);
	}
	
	public Builder(Extractor extractor) {
		this.extractor = extractor;
	}
}
