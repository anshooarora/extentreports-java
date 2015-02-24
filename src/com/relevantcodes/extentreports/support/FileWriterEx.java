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

package com.relevantcodes.extentreports.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriterEx {
	public static void write(String filePath, String text) {
		BufferedWriter writer = null;
		
        try {
            File logFile = new File(filePath);

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try {
              writer.close();
            } 
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
	}
	
	public static void createNewFile(String filePath, String text) throws IOException {			
		try {
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			writer.print(text);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
