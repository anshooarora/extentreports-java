/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReaderEx {
	public static String readAllText(String filePath) {
		File file = new File(filePath);
		
		if (file.exists()) {
		    FileInputStream fis;
		    byte[] data;
		    
			try {
				fis = new FileInputStream(file);
				data = new byte[(int)file.length()];
				fis.read(data);
				fis.close();
				
				return new String(data, "UTF-8");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}   
		}
		
		return null;
	}
}
