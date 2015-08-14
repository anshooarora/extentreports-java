package com.relevantcodes.extentmerge;

import java.io.File;

public class Builder {
	public static void build(File file, String testSource, String quickTestSummarySource) {
		 String sourceFile = "com/relevantcodes/extentmerge/STANDARD.min.html";
		 String source = Resources.getText(sourceFile);
		 String extentSource = source
				 .replace("<!--%%TEST%%-->", testSource.replace("<!--%%TEST%%-->", ""))
				 .replace("<!--%%QUICKTESTSUMMARY%%-->", quickTestSummarySource.replace("<!--%%QUICKTESTSUMMARY%%-->", ""));
		 
		 Writer.getInstance().write(file, extentSource);
	}
}
