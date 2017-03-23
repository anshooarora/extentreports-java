package com.aventstack.extentreports.model;

import org.bson.types.ObjectId;

//marker interface for Test and Log
public interface BasicReportElement {
 
 public ObjectId getObjectId();
 public void setObjectId(ObjectId id);
 
}