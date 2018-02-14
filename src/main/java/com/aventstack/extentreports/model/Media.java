package com.aventstack.extentreports.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class Media implements Serializable {

    private static final long serialVersionUID = -5706630485211806728L;
    
    private ObjectId id;
    private ObjectId reportId;
    private ObjectId testId;
    private ObjectId logId;
    
    private String name;
    private String description;
    private String path;
    private String base64String;
    
    private int seq;

    private MediaType mediaType;
    
    public void setObjectId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getObjectId() { return id; }
    
    public void setReportObjectId(ObjectId reportId) {
        this.reportId = reportId;
    }
    public ObjectId getReportObjectId() { return reportId; }

    public void setTestObjectId(ObjectId testId) {
        this.testId = testId;
    }
    public ObjectId getTestObjectId() { return testId; }
    
    public void setLogObjectId(ObjectId logId) {
        this.logId = logId;
    }
    public ObjectId getLogObjectId() { return logId; }
    
    public void setName(String name) {
        this.name = name;
    }    
    public String getName() { return name; }
    
    protected void setDescription(String description) {
        this.description = description;
    }
    protected String getDescription() { return description; }
    
    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() { return path; }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
    public MediaType getMediaType() { return mediaType; }

    public void setSequence(int seq) {
        this.seq = seq;
    }
    public int getSequence() { return seq; }

    public void setBase64String(String string) {
        string = "data:image/gif;base64," + string;
        this.base64String = string;
    }
    public String getBase64String() { 
        return base64String;
    }
    
}
