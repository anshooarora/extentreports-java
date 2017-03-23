package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.types.ObjectId;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.RunResult;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;

public class Test implements RunResult, Serializable, BasicReportElement {

    private static final long serialVersionUID = 5590943223572254960L;

    /**
     * Default level of this test, using the numeric hierarchy with 0 indicating
     * the top-most test, followed by 1, 2, and so on
     */
    private int level = 0;
    private int testID;
    
    private ExtentReports extent;
    private Test parent;
    private Status testStatus;
    
    private AbstractStructure<Test> node;
    private AbstractStructure<Log> log;
    private AbstractStructure<TestAttribute> category;
    private AbstractStructure<TestAttribute> author;
    
    private Date endTime;
    private Date startTime;
    
    private static final AtomicInteger id = new AtomicInteger(0);
    private ObjectId mongoId;
    
    private Class<? extends IGherkinFormatterModel> bddType;
    
    private transient List<ScreenCapture> screenCaptureList;
    private transient List<Screencast> screencastList;
    private transient List<ExceptionInfo> exceptionList;
    
    private String name;
    private String hierarchicalName;
    private String description;
    
    private boolean ended = false;
    private boolean usesManualConfiguration = false;
    
    public Test() {
        setStartTime(Calendar.getInstance().getTime());
        setEndTime(getStartTime());
        setStatus(Status.PASS);
        
        setID(id.incrementAndGet());
    }

    // if used via listener, allow manual configuration of model
    public void setUseManualConfiguration(boolean b) {
        this.usesManualConfiguration = b;
    }
    
    public ExtentReports getExtentInstance() {
        return extent;
    }
    
    public void setExtentInstance(ExtentReports extent) {
        this.extent = extent;
    }

    // child
    public boolean isChildNode() {
        return level > 0;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }

    // parent    
    public void setParent(Test parent) {
        hierarchicalName = parent.getHierarchicalName() + "." + getName();
        this.parent = parent;
    }
    
    public Test getParent() { return parent; }

    // nodes
    public AbstractStructure<Test> getNodeContext() {
        if (node == null) {
            node = new AbstractStructure<>();
        }

        return node;
    }
    
    public boolean hasChildren() {
        return node != null && node.getAll() != null && node.getAll().size() > 0;
    }

    // started time
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    // ended time
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    private void setEndTimeFromChildren() {
        if (hasLog()) {
            int logSize = getLogContext().size();
            Date lastLogEndTime = getLogContext().get(logSize - 1).getTimestamp(); 
            setEndTime(lastLogEndTime);
        }
        
        if (hasChildren()) {
            int nodeSize = getNodeContext().size();
            Date lastNodeEndTime = getNodeContext().get(nodeSize - 1).getEndTime();
            setEndTime(lastNodeEndTime);
        }
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean hasEnded() {
        return ended;
    }
    
    // run duration as string
    public String getRunDuration() {
        long diff = endTime.getTime() - startTime.getTime();
        
        long secs = diff / 1000;
        long millis = diff % 1000;
        long mins = secs / 60;
        secs = secs % 60;
        long hours = mins / 60;
        mins = mins % 60;
        
        return hours + "h " + mins + "m " + secs + "s+" + millis + "ms";
    }
    
    public Long getRunDurationMillis() {
    	long diff = endTime.getTime() - startTime.getTime();
    	return diff;
    }

    // default status when the test starts
    public void setStatus(Status status) {
        this.testStatus = status;
    }

    public Status getStatus() {
        return testStatus;
    }

    public void trackLastRunStatus() {
        getLogContext().getAll().forEach(x -> updateStatus(x.getStatus()));

        if (testStatus == Status.INFO) 
            testStatus = Status.PASS;
    }
    
    private synchronized void updateStatus(Status logStatus) {
        int logStatusIndex = Status.getStatusHierarchy().indexOf(logStatus);        
        int testStatusIndex = Status.getStatusHierarchy().indexOf(testStatus);
        
        testStatus = logStatusIndex < testStatusIndex ? logStatus : testStatus;
    }

    public void end() {
        updateTestStatusRecursive(this);
        endChildrenRecursive(this);
        
        testStatus = testStatus == Status.INFO ? Status.PASS : testStatus;
        
        if (!usesManualConfiguration || endTime == null)
            setEndTimeFromChildren();
    }

    private synchronized void updateTestStatusRecursive(Test test) {
        test.getLogContext().getAll().forEach(x -> updateStatus(x.getStatus()));

        if (test.hasChildren())
            test.node.getAll().forEach(this::updateTestStatusRecursive);
    }
    
    private void endChildrenRecursive(Test test) {
        test.getNodeContext().getAll().forEach(Test::end);
    }

    // logs
    public AbstractStructure<Log> getLogContext() {
        if (log == null) {
            log = new AbstractStructure<>();
        }

        return log;
    }
    
    public boolean hasLog() {
        return log != null && log.getAll() != null && log.size() > 0;
    }
    
    // test description
    public void setDescription(String description) { 
        this.description = description; 
    }

    public String getDescription() { return description; }

    // test name
    public void setName(String name) {
        this.name = name;
        
        if (hierarchicalName == null)
            hierarchicalName = name;
    }

    public String getName() { return name; }

    public String getHierarchicalName() { return hierarchicalName; }
    
    // categories
    public AbstractStructure<TestAttribute> getCategoryContext() {
        if (category == null)
            category = new AbstractStructure<>();
        
        return category;
    }
    
    public boolean hasCategory() {
        return category != null && category.getAll() != null && category.size() > 0;
    }
    
    public void setCategory(TestAttribute category) {
        getCategoryContext().add(category);
    }
    
    public TestAttribute getCategory(Integer index) {
        if (hasCategory() && index >= category.size() - 1)
            return category.get(index);
            
        return null;
    }
    
    // authors
    public AbstractStructure<TestAttribute> getAuthorContext() {
        if (author == null)
            author = new AbstractStructure<>();
        
        return author;
    }
    
    public boolean hasAuthor() {
        return author != null && author.getAll() != null && author.size() > 0;
    }
    
    public void setAuthor(TestAttribute author) {
        getAuthorContext().add(author);
    }
    
    public TestAttribute getAuthor(Integer index) {
        if (hasAuthor() && index >= author.size() - 1)
            return author.get(index);
            
        return null;
    }

    // exceptions
    public void setExceptionInfo(ExceptionInfo ei) {
        if (exceptionList == null)
            exceptionList = new ArrayList<>();
        
        exceptionList.add(ei);
    }
    
    public List<ExceptionInfo> getExceptionInfoList() { return exceptionList; }
    
    public boolean hasException() {
        return exceptionList != null && !exceptionList.isEmpty();
    }
    
    // media - screenshots
    public boolean hasMedia() {
        return screenCaptureList != null 
                && !screenCaptureList.isEmpty()
                && screencastList != null
                && !screencastList.isEmpty();
    }
    
    public void setScreenCapture(ScreenCapture sc) {
        if (screenCaptureList == null)
            screenCaptureList = new ArrayList<>();
        
        screenCaptureList.add(sc);
    }
    
    public List<ScreenCapture> getScreenCaptureList() { return screenCaptureList; }
    
    // media - screencast
    public void setScreencast(Screencast screencast) {
        if (screencastList == null)
            screencastList = new ArrayList<>();
        
        screencastList.add(screencast);
    }
    
    public List<Screencast> getScreencastList() { return screencastList; }
    
    // bdd
    public boolean isBehaviorDrivenType() { return bddType != null; }
    
    public void setBehaviorDrivenType(IGherkinFormatterModel type) { bddType = type.getClass(); }
    
    public void setBehaviorDrivenType(Class<? extends IGherkinFormatterModel> type) { bddType = type; }
    
    public Class<? extends IGherkinFormatterModel> getBehaviorDrivenType() { return bddType; }
    
    // test-id    
    void setID(int id) { testID = id; }
    
    public int getID() { return testID; }
    
    // mongo-db id
    public void setObjectId(ObjectId id) { mongoId = id; }
    
    public ObjectId getObjectId() { return mongoId; }
}
