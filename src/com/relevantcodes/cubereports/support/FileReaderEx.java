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

package com.relevantcodes.cubereports.support;

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
