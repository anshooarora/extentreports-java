package com.relevantcodes.extentreports.common;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import com.relevantcodes.extentreports.ReporterType;

public class ExtentManager {
	private static ExtentReports extent;
    
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
        	extent = new ExtentReports(filePath, true, NetworkMode.ONLINE);
        	extent.startReporter(ReporterType.DB, (new File(filePath)).getParent() + File.separator + "extent.db");
        	
        	extent
                .addSystemInfo("Host Name", "Anshoo")
                .addSystemInfo("Environment", "QA");
        }
        
        return extent;
    }
}
