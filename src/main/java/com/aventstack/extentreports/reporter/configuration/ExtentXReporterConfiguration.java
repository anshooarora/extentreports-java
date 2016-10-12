package com.aventstack.extentreports.reporter.configuration;

import org.bson.types.ObjectId;

/**
 * Defines configuration settings for the ExtentX reporter
 */
public class ExtentXReporterConfiguration extends BasicConfiguration implements IReporterConfiguration {

    private ObjectId id;
    private String projectName;
    private String url;
    
    /**
     * Set the URL of the ExtentX server
     * 
     * @param url Url
     */
    public void setServerUrl(String url) {
        usedConfigs.put("serverUrl", url);
        this.url = url;
    }
    public String getServerUrl() { return url; }
    
    /**
     * Sets the project name. The report will be added to this project. 
     * 
     * <p>
     * Note: You will have the option to filter reports by project in ExtentX.
     * </p>
     * 
     * @param projectName Project name
     */
    public void setProjectName(String projectName) {
        usedConfigs.put("projectName", projectName);
        this.projectName = projectName;
    }
    public String getProjectName() { return projectName; }
    
    /**
     * Set the report id, in case of appending the current execution to an existing run in ExtentX
     * 
     * @param id Mongo object id
     */
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
