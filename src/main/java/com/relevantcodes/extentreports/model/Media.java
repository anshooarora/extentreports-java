package com.relevantcodes.extentreports.model;

public class Media {
    
    private String name;
    private String description;
    private String path;   
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    protected void setDescription(String description) {
        this.description = description;
    }
    
    protected String getDescription() {
        return description;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }

}
