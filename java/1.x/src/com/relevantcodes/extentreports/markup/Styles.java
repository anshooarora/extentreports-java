package com.relevantcodes.extentreports.markup;

class Styles {
	public static String standardCascade() {
		return ".content { width: 100%; }" +
				".test { border: none; padding: 20px 0; }" + 
				".test:nth-child(2n) { background-color: #f8f8f8;}" +
				".test-expanded {border: none !important; padding-bottom: 40px;}" +
				".test-header, .exec-info { margin: 0 auto !important; width: 1050px; }" +
				".name { color: #222; font-size: 21px; font-weight: 600;}";
	}
}
