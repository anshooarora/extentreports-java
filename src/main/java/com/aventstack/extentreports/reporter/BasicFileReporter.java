package com.aventstack.extentreports.reporter;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Screencast;
import com.aventstack.extentreports.model.Test;

public abstract class BasicFileReporter extends AbstractReporter {

    private List<Status> statusCollection;
    
    @Override
    public void onTestStarted(Test test) { }
    
    @Override
    public synchronized void onNodeStarted(Test node) { }

    @Override
    public synchronized void onLogAdded(Test test, Log log) { 
        if (statusCollection == null)
            statusCollection = new ArrayList<>();
        
        Status status = log.getStatus() == Status.INFO ? Status.PASS : log.getStatus();

        trackUsedStatus(status);
    }
    
    public List<Status> getStatusCollection() {
    	return statusCollection;
    }
    
    public synchronized void refreshStatusCollection(List<Test> testCollection, Boolean renew) {
    	if (renew)
    		statusCollection.clear();
    	
    	testCollection.forEach(x -> {
    		if (x.hasLog())
    			x.getLogContext().getAll().forEach(this::trackUsedStatus);
			
    		if (x.hasChildren())
    			refreshStatusCollection(x.getNodeContext().getAll(), false);
    	});
    }
    
    private void trackUsedStatus(Log log) {
    	trackUsedStatus(log.getStatus());
    }
    
    private void trackUsedStatus(Status status) {
    	if (!statusCollection.contains(status))
            statusCollection.add(status);
    }
    
    @Override
    public void onCategoryAssigned(Test test, Category category) { }

    @Override
    public void onAuthorAssigned(Test test, Author author) { }
    
    @Override
    public void onScreenCaptureAdded(Log log, ScreenCapture screenCapture) { }
    
    @Override
    public void onScreencastAdded(Test test, Screencast screencast) { }
    
    @Override
    public void stop() { }
    
    public abstract String getLongRunDuration();
    
}
