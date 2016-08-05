package com.relevantcodes.extentreports.reporter.configuration;

import org.bson.types.ObjectId;

public class ExtentXReporterConfiguration extends BasicConfiguration implements IReporterConfiguration {

    private ObjectId id;
    private String projectName;
    private String url;
    
    public void setServerUrl(String url) {
        usedConfigs.put("serverUrl", url);
        this.url = url;
    }
    public String getServerUrl() { return url; }
    
    public void setProjectName(String projectName) {
        usedConfigs.put("projectName", projectName);
        this.projectName = projectName;
    }
    public String getProjectName() { return projectName; }
    
    public void setReportObjectId(ObjectId id) {
        usedConfigs.put("reportId", id.toString());
        this.id = id;
    }
    public void setReportObjectId(String id) {
        usedConfigs.put("reportId", id);
        this.id = new ObjectId(id);
    }
    public ObjectId getReportObjectId() { return id; }
    
}
