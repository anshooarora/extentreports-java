package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.types.ObjectId;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;

public class Test implements Serializable {

    static final long serialVersionUID = 5590943223572254960L;

    /**
     * <p>
     *     Default level of this test, using the numeric hierarchy with 0 indicating
     *     the top-most test, followed by 1, 2, and so on
     * </p>
     */
    private int level = 0;
    
    private Test parent;
    private Status testStatus;
    
    private NodeStructure node;
    private LogStructure log;
    
    private Date endTime;
    private Date startTime;
    
    private int testID;
    
    private static final AtomicInteger id = new AtomicInteger(0);
    private ObjectId mongoId;
    
    private Class<? extends IGherkinFormatterModel> bddType;
    
    private transient List<ScreenCapture> scList;
    private transient List<ExceptionInfo> exceptionList;
    private transient List<TestAttribute> authorList;
    private transient List<TestAttribute> categoryList;
    
    private String name;
    private String hierarchicalName;
    private String description;
    
    private boolean ended = false;
    
    public Test() {
        setStartTime(Calendar.getInstance().getTime());
        setStatus(Status.UNKNOWN);
        
        setID(id.incrementAndGet());
    }   
    
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
    public NodeStructure getNodeContext() {
        if (node == null) {
            node = new NodeStructure();
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

    public Date getEndTime() {
        return endTime;
    }
    
    // has test ended already?
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
        setEndTime(Calendar.getInstance().getTime());
        updateTestStatusRecursive(this);
        endChildrenRecursive(this);
        
        testStatus = testStatus == Status.INFO ? Status.PASS : testStatus;
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
    public LogStructure getLogContext() {
        if (log == null) {
            log = new LogStructure();
        }

        return log;
    }
    
    public boolean hasLogs() {
        return log != null && log.getAll() != null && log.getAll().size() > 0;
    }

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
    public boolean hasCategory() {
        return categoryList != null && !categoryList.isEmpty();
    }
    
    public void setCategory(TestAttribute category) {
        if (categoryList == null) 
            categoryList = new ArrayList<>();
        
        categoryList.add(category);
    }
    
    public List<TestAttribute> getCategoryList() {
        return categoryList;
    }
    
    // authors
    public boolean hasAuthor() {
        return authorList != null && !authorList.isEmpty();
    }
    
    public void setAuthor(TestAttribute author) {
        if (authorList == null) 
            authorList = new ArrayList<>();
        
        authorList.add(author);
    }
    
    public List<TestAttribute> getAuthorList() { return authorList; }

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
        return scList != null && !scList.isEmpty();
    }
    
    public void setScreenCapture(ScreenCapture sc) {
        if (scList == null)
            scList = new ArrayList<>();
        
        scList.add(sc);
    }
    
    public List<ScreenCapture> getScreenCaptureList() { return scList; }
    
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
