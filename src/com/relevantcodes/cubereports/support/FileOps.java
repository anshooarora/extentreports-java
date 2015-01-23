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
