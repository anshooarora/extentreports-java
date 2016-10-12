package com.aventstack.extentreports;

import java.util.List;

import com.aventstack.extentreports.model.Test;

public class SessionStatusStats {
    
    List<Test> testList;
    
    int parentPass = 0; 
    int parentFail = 0;
    int parentFatal = 0;
    int parentError = 0;
    int parentWarning = 0;
    int parentSkip = 0;
    int parentUnknown = 0;
                
    int childPass = 0; 
    int childFail = 0;
    int childFatal = 0;
    int childError = 0;
    int childWarning = 0;
    int childSkip = 0;
    int childUnknown = 0;
    int childInfo = 0;
    
    int grandChildPass = 0; 
    int grandChildFail = 0;
    int grandChildFatal = 0;
    int grandChildError = 0;
    int grandChildWarning = 0;
    int grandChildSkip = 0;
    int grandChildUnknown = 0;
    int grandChildInfo = 0;
    
    public SessionStatusStats(List<Test> testList) { 
        this.testList = testList;
        updateCounts();
    }
    
    public int getParentCount() { 
        return getParentCountPass() + 
            getParentCountFail() + 
            getParentCountFatal() +
            getParentCountError() +
            getParentCountWarning() +
            getParentCountSkip() +
            getParentCountUnknown(); 
    }
    public int getParentCountPass() { return parentPass; }
    public int getParentCountFail() { return parentFail; }
    public int getParentCountFatal() { return parentFatal; }
    public int getParentCountError() { return parentError; }
    public int getParentCountWarning() { return parentWarning; }
    public int getParentCountSkip() { return parentSkip; }
    public int getParentCountUnknown() { return parentUnknown; }
    
    public int getChildCount() { 
        return getChildCountPass() + 
            getChildCountFail() + 
            getChildCountFatal() +
            getChildCountError() +
            getChildCountWarning() +
            getChildCountSkip() +
            getChildCountInfo() +
            getChildCountUnknown(); 
    }
    public int getChildCountPass() { return childPass; }
    public int getChildCountFail() { return childFail; }
    public int getChildCountFatal() { return childFatal; }
    public int getChildCountError() { return childError; }
    public int getChildCountWarning() { return childWarning; }
    public int getChildCountSkip() { return childSkip; }
    public int getChildCountUnknown() { return childUnknown; }
    public int getChildCountInfo() { return childInfo; }
    
    public int getGrandChildCount() { 
        return getGrandChildCountPass() + 
            getGrandChildCountFail() + 
            getGrandChildCountFatal() +
            getGrandChildCountError() +
            getGrandChildCountWarning() +
            getGrandChildCountSkip() +
            getGrandChildCountInfo() +
            getGrandChildCountUnknown(); 
    }
    public int getGrandChildCountPass() { return grandChildPass; }
    public int getGrandChildCountFail() { return grandChildFail; }
    public int getGrandChildCountFatal() { return grandChildFatal; }
    public int getGrandChildCountError() { return grandChildError; }
    public int getGrandChildCountWarning() { return grandChildWarning; }
    public int getGrandChildCountSkip() { return grandChildSkip; }
    public int getGrandChildCountUnknown() { return grandChildUnknown; }
    public int getGrandChildCountInfo() { return grandChildInfo; }
    
    void updateCounts() {
        testList.forEach(this::addTestForStatusStatsUpdate);
    }
    
    void addTestForStatusStatsUpdate(Test test) {
        if (test.isBehaviorDrivenType() || (test.hasChildren() && test.getNodeContext().get(0).isBehaviorDrivenType())) {
            updateGroupCountsBDD(test);
            return;
        }
        
        updateGroupCounts(test);
    }
    
    void updateGroupCountsBDD(Test test) {
        incrementItemCountByStatus(ItemLevel.PARENT, test.getStatus());
        
        if (test.hasChildren()) {
            test.getNodeContext().getAll().forEach(x -> {
                incrementItemCountByStatus(ItemLevel.CHILD, x.getStatus());

                if (x.getNodeContext() != null && x.getNodeContext().getAll().size() > 0)
                    x.getNodeContext().getAll().forEach(n -> n.getLogContext().getAll().forEach(l -> incrementItemCountByStatus(ItemLevel.GRANDCHILD, l.getStatus())));
            });
        }
    }
    
    void updateGroupCounts(Test test) {
        if (!test.hasChildren()) {
            incrementItemCountByStatus(ItemLevel.PARENT, test.getStatus());
            test.getLogContext().getAll().forEach(x -> incrementItemCountByStatus(ItemLevel.CHILD, x.getStatus()));
        }
        else {
            test.getNodeContext().getAll().forEach(this::updateGroupCounts);
        }
    }
    
    enum ItemLevel {
        PARENT,
        CHILD,
        GRANDCHILD
    }
    
    void incrementItemCountByStatus(ItemLevel item, Status status) {
        switch (item) {
            case PARENT:
                incrementParent(status);
                break;
            
            case CHILD:
                incrementChild(status);
                break;
                
            case GRANDCHILD:
                incrementGrandChild(status);
                break;
                
            default:
                break;
        }
    }
    
    void incrementParent(Status status) {
        switch (status) {
            case PASS: 
                parentPass++; 
                break;
            case FAIL: 
                parentFail++; 
                break;
            case FATAL: 
                parentFatal++; 
                break;
            case ERROR: 
                parentError++; 
                break;
            case WARNING: 
                parentWarning++; 
                break;
            case SKIP: 
                parentSkip++; 
                break;
            case UNKNOWN: 
                parentUnknown++; 
                break;
            default: 
                break;
        }
    }

    void incrementChild(Status status) {
        switch (status) {
            case PASS: 
                childPass++; 
                break;
            case FAIL: 
                childFail++; 
                break;
            case FATAL: 
                childFatal++; 
                break;
            case ERROR: 
                childError++; 
                break;
            case WARNING: 
                childWarning++; 
                break;
            case SKIP: 
                childSkip++; 
                break;
            case UNKNOWN: 
                childUnknown++; 
                break;
            case INFO: 
                childInfo++; 
                break;
            default: 
                break;
        }
    }
    
    void incrementGrandChild(Status status) {
        switch (status) {
            case PASS: 
                grandChildPass++; 
                break;
            case FAIL: 
                grandChildFail++; 
                break;
            case FATAL: 
                grandChildFatal++; 
                break;
            case ERROR: 
                grandChildError++; 
                break;
            case WARNING: 
                grandChildWarning++; 
                break;
            case SKIP: 
                grandChildSkip++; 
                break;
            case UNKNOWN: 
                grandChildUnknown++; 
                break;
            case INFO: 
                grandChildInfo++; 
                break;
            default: 
                break;
        }
    }
}
