package com.aventstack.extentreports;

public class ReportConfigurator {

    private static class ReportConfiguratorInstance {
        static final ReportConfigurator INSTANCE = new ReportConfigurator();
        
        private ReportConfiguratorInstance() { }
    }

    private ReportConfigurator() { }
    
    public static ReportConfigurator getInstance() {
        return ReportConfiguratorInstance.INSTANCE;
    }

    public StatusConfigurator statusConfigurator() {
        return StatusConfigurator.getInstance();
    }
    
}