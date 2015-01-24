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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOps {
	public static void write(String filePath, String text) {
		BufferedWriter writer = null;
		
        try {
            File logFile = new File(filePath);

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } 
        catch (Exception ex) {
            
        } 
        finally {
            try {
              writer.close();
            } 
            catch (Exception ex) {
            	
            }
        }
	}
	
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
			catch (FileNotFoundException ex) {
				
			}
			catch (IOException ex) {
				
			}   
		}
		
		return null;
	}
	
	public static void createNewFile(String filePath, String text) throws IOException {			
		try {
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			writer.print(text);
			writer.close();
		}
		catch (IOException ex) {}
	}
}
