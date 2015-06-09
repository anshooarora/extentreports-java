package com.relevantcodes.extentreports.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Writer {
    private static Writer instance = new Writer();

    public void write(final File f, String text) {
        synchronized (f) {
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
                  writer.close();
                } 
                catch (Exception e) {
                	e.printStackTrace();
                }
            }
        }
    }
    
    private Writer() {
        super();
    }
    
    public static Writer getInstance() {
		if (instance == null) {
			synchronized (Writer.class) {
				if (instance == null) {
					instance = new Writer();
				}
			}
		}
		
		return instance;
	}
    
    //public static Writer getInstance() {
        //return instance;
    //}
}