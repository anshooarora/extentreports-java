package com.relevantcodes.extentreports.reporter.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.relevantcodes.extentreports.Status;

abstract class BasicConfiguration {

    List<Status> levels;
    String reportName;
    Map<String, String> usedConfigs;

    public BasicConfiguration() {
        usedConfigs = new HashMap<>();
    }
    
    public Map<String, String> getConfigMap() { return usedConfigs; }
    
    public void setLevel(Status... level) {
        if (levels == null)
            levels = new ArrayList<>();
        
        Arrays.stream(level).forEach(x -> levels.add(x));
    }
    public List<Status> getLevel() { return levels; }
    
    public void setReportName(String reportName) {
        usedConfigs.put("reportName", reportName);
        this.reportName = reportName; 
    }    
    public String getReportName() { return reportName; }

}
