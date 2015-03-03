/*
Copyright 2015 ExtentReports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class Footer implements IFooter {
	private String filePath;
	
	public void useExtentFooter(Boolean use) {
		String start = MarkupFlag.get("PROJECTFOOTER");
		String end = MarkupFlag.get("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (use) {
			markup = markup.replace(start + "<!--", start).replace("-->" + end, end);
		}
		else {
			markup = markup.replace(start, start + "<!--");
			markup = markup.replace(end, "-->" + end);
		}
		
		FileWriterEx.write(filePath, markup);
	}
	
	// ToDo
	// provide ability to add custom footers
	// 	addCustomFooter("MyCompanyName", [ "<a href=''>Link1</a>", "<a href=''>Link2</a>" ]);
	//		MyCompanyName
	//			+ Link1
	//			+ Link2
	// private void addCustomFooter(String header, String[] chilren) { }
	
	@Deprecated
	public void addExtentFooter() {
		String placeHolderStart = MarkupFlag.get("PROJECTFOOTER");
		String placeHolderEnd = MarkupFlag.get("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (markup.contains(placeHolderStart + "<!--")) {
			markup = markup.replace(placeHolderStart + "<!--", placeHolderStart);
			markup = markup.replace("-->" + placeHolderEnd, placeHolderEnd);
			
			FileWriterEx.write(filePath, markup);
		}
	}
	
	@Deprecated
	public void removeExtentFooter() {
		String placeHolderStart = MarkupFlag.get("PROJECTFOOTER");
		String placeHolderEnd = MarkupFlag.get("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (markup.contains(placeHolderStart + "<!--")) {
			return;
		}
		
		markup = markup.replace(placeHolderStart, placeHolderStart + "<!--");
		markup = markup.replace(placeHolderEnd, "-->" + placeHolderEnd);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Footer setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Footer(String filePath) {
		this.filePath = filePath;
	}
}
