/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Writer {
    public synchronized void write(final File f, String text) {
    	BufferedWriter writer = null;
    	
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write(text);
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try {
            	if(writer != null)
            		writer.close();
            } 
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
    
    private Writer() { }
	
	private static class Instance {
        static final Writer INSTANCE = new Writer();
    }
	
	public static Writer getInstance() {
		return Instance.INSTANCE;
	}
}