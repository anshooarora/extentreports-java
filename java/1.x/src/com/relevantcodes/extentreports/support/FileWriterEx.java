/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
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
