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

import java.util.HashMap;

import com.relevantcodes.extentreports.LogStatus;

public class Configuration {
	private HashMap<String, String> param = new HashMap<String, String>();
	private IContent content;
	private IDocumentHead documentHead;
	private IHeader header;
	private IFooter footer;
	private IScripts scripts;
	
	public void statusIcon(LogStatus status, String newIcon) {
		FontAwesomeIco.override(status, newIcon);
	}
	
	public void params(String varName, String varValue) {
		param.put(varName, varValue);
	}
	
	public IContent content() {
		if (!(content instanceof IContent)) 
			content = new Content(param.get("filePath"));
		
		return content;
	}
	
	public IDocumentHead documentHead() {
		if (!(documentHead instanceof IDocumentHead))
			documentHead = new DocumentHead(param.get("filePath"));
		
		return documentHead;
	}
	
	public IHeader header() {
		if (!(header instanceof IHeader))
			header = new Header(param.get("filePath"));
		
		return header;
	}
	
	public IFooter footer() {
		if (!(footer instanceof IFooter))
			footer = new Footer(param.get("filePath"));
		
		return footer;
	}
	
	public IScripts scripts() {
		if (!(scripts instanceof IScripts))
			scripts = new Scripts(param.get("filePath"));
		
		return scripts;
	}
	
	public Configuration() {}
}
