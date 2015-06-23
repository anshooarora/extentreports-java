/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.support;

import java.io.InputStream;

public class Resources {
	public static String getText(String resourcePath) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	  	InputStream input = classLoader.getResourceAsStream(resourcePath);
		
  		return Stream.toString(input);
	}
}
