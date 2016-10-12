package com.aventstack.extentreports.utils;

import java.io.File;

public class FileUtil {
    
    public static String getFileNameWithoutExtension(File f) {
        String name = f.getName();
        int i = name.lastIndexOf(".");
        if (i > 0)
            return name.substring(0, i);
        
        return name;
    }
    
    public static String getFileNameWithoutExtension(String filePath) {
        return getFileNameWithoutExtension(new File(filePath));
    }
    
    public static String getExtension(File f) {
        String name = f.getName();
        int i = name.lastIndexOf(".");
        if (i > 0)
            return name.substring(i + 1);
        
        return "";
    }
    
    public static String getExtension(String filePath) {
        return getExtension(new File(filePath));
    }
    
}
