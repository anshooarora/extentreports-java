package com.relevantcodes.extentreports.reporter;

import com.relevantcodes.extentreports.model.Author;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;

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
    
}
