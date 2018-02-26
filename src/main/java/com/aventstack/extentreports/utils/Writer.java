package com.aventstack.extentreports.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer {

    private static class WriterInstance {
        static final Writer INSTANCE = new Writer();
        
        private WriterInstance() { }
    }
    
    static final Logger logger = Logger.getLogger(Writer.class.getName());
    
    private Writer() { }
    
    public synchronized void write(final File f, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            writer.write(text);
        } 
        catch (Exception e) {
            logger.log(Level.SEVERE, f.getPath(), e);
        } 
    }
    
    public static Writer getInstance() {
        return WriterInstance.INSTANCE;
    }

}