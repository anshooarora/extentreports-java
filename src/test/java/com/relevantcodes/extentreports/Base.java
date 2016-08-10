package com.relevantcodes.extentreports;

import java.io.File;

public abstract class Base {
    
    protected String getOutputFolder() { 
        return "test-output/";
    }
    
    public Base() {
        File folder = new File(getOutputFolder());
        folder.mkdirs();
    }
}
