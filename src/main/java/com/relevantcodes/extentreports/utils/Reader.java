package com.relevantcodes.extentreports.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {
    
    final static Logger logger = Logger.getLogger(Reader.class.getName());
            
    private Reader() { }
    
    public static String readAllText(String filePath) {
        File file = new File(filePath);
        
        if (file.exists()) {
            byte[] data;
            
            try (FileInputStream fis = new FileInputStream(file)) {
                data = new byte[(int)file.length()];
                fis.read(data);
                
                return new String(data, "UTF-8");
            } 
            catch (IOException e) {
                logger.log(Level.SEVERE, filePath, e);
            }
        }
        
        return null;
    }
    
    public static String readAllText(File file) {
        return readAllText(file.getAbsolutePath());
    }
}