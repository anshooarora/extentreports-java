package com.aventstack.extentreports;

import java.util.List;

public class StatusConfigurator {

    private static class StatusConfiguratorInstance {
        static final StatusConfigurator INSTANCE = new StatusConfigurator();
        
        private StatusConfiguratorInstance() { }
    }

    private StatusConfigurator() { }
    
    public static StatusConfigurator getInstance() {
        return StatusConfiguratorInstance.INSTANCE;
    }
    
    public List<Status> getStatusHierarchy() {
        return Status.getStatusHierarchy();
    }
    public void setStatusHierarchy(List<Status> statusHierarchy) {
        Status.setStatusHierarchy(statusHierarchy);
    }
    
    public void resetStatusHierarchy() {
        Status.resetStatusHierarchy();
    }
}