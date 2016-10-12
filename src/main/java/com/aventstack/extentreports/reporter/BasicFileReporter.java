package com.aventstack.extentreports.reporter;

import java.io.IOException;

import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;

public abstract class BasicFileReporter extends AbstractReporter {

    @Override
    public void onTestStarted(Test test) { }
    
    @Override
    public synchronized void onNodeStarted(Test node) { }

    @Override
    public synchronized void onLogAdded(Test test, Log log) { }
    
    @Override
    public void onCategoryAssigned(Test test, Category category) { }

    @Override
    public void onAuthorAssigned(Test test, Author author) { }
    
    @Override
    public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) throws IOException { }
    
}
