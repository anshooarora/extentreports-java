/*
Copyright 2015 Cube Reports committer(s)

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

public class FontAwesomeIco {
	private static HashMap<LogStatus, String> map = new HashMap<LogStatus, String>();
	
	public static void override(LogStatus status, String icon) {
		map.put(status, icon);
	}
	
	public static String get(LogStatus status) {
		if (map.containsKey(status))
			return map.get(status);
		
		switch (status.toString().toLowerCase()) {
			case "fail":
				return "times";
			case "error":
				return "exclamation-circle";
			case "fatal":
				return "exclamation-circle";
			case "pass":
				return "check";
			case "info":
				return "info";
			case "warning":
				return "warning";
			default:
				return null;
		}
	}
}
