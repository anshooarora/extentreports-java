package com.aventstack.extentreports.reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.aventstack.extentreports.mediastorage.MediaStorage;
import com.aventstack.extentreports.mediastorage.MediaStorageManagerFactory;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.BasicReportElement;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Screencast;
import com.aventstack.extentreports.model.SystemAttribute;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.utils.MongoUtil;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * ExtentXReporter is a NoSQL database reporter (MongoDB by default), which updates information in
 * the database which is then used by the ExtentX server to display in-depth analysis. 
 */
public class KlovReporter extends AbstractReporter {

    private static final String DB_NAME = "klov";
    private static final String DEFAULT_PROJECT_NAME = "Default";
    
    private String url;
    
    private Map<String, ObjectId> categoryNameObjectIdCollection;
    private Map<String, ObjectId> exceptionNameObjectIdCollection;
    
    private ObjectId reportId;
    private String reportName;
    private ObjectId projectId;
    private String projectName;
    
    private MongoClient mongoClient;
    
    private MongoDatabase db;
    
    private MongoCollection<Document> projectCollection;
    private MongoCollection<Document> reportCollection;
    private MongoCollection<Document> testCollection;
    private MongoCollection<Document> logCollection;
    private MongoCollection<Document> exceptionCollection;
    private MongoCollection<Document> mediaCollection;
    private MongoCollection<Document> categoryCollection;
    private MongoCollection<Document> authorCollection;
    private MongoCollection<Document> environmentCollection;
    
    private MediaStorage media;
    
    static {
        /* use mongodb reporting for only critical/severe events */
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    /**
     * Initializes the KlovReporter
     */
    public KlovReporter() { }
    
    /**
     * Initializes the KlovReporter with project and report names
     * 
     * @param projectName Name of the project
     * @param reportName Name of the report
     */
    public KlovReporter(String projectName, String reportName) {
        this.projectName = projectName;
        this.reportName = reportName;
    }

    public String getProjectName() {
        return projectName;
    }
    /**
     * Sets the project name
     * 
     * @param projectName Name of the project
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getReportName() {
        return reportName;
    }
    /**
     * Sets the report name
     * 
     * @param reportName Name of the report
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    public String getKlovUrl() {
        return url;
    }
    /**
     * Sets the Klov url
     * 
     * <p>
     * Note: its mandatory to set the url if you plan to view images on the Klov server
     * 
     * @param url Url of Klov Server
     */
    public void setKlovUrl(String url) {
        this.url = url;
    }
    
    /**
     * Initialize Mongo DB connection with host and default port: 27017
     * 
     * @param host host name
     */
    public void initMongoDbConnection(String host) {
        mongoClient = new MongoClient(host, 27017);
    }
    
    /**
     * Initialize Mongo DB connection with host and {@link MongoClientOptions}
     * 
     * @param host host name
     * @param options {@link MongoClientOptions} options
     */
    public void initMongoDbConnection(String host,  MongoClientOptions options) {
        mongoClient = new MongoClient(host, options);
    }
    
    /**
     * Initialize Mongo DB connection with host and post
     * 
     * @param host host name
     * @param port port number
     */
    public void initMongoDbConnection(String host, int port) {
        mongoClient = new MongoClient(host, port);
    }
    
    /**
     * Initialize Mongo DB connection with a {@link MongoClientURI}
     * 
     * @param uri {@link MongoClientURI} uri
     */
    public void initMongoDbConnection(MongoClientURI uri) {
        mongoClient = new MongoClient(uri);        
    }
    
    /**
     * Initializes the Mongo DB connection with {@link ServerAddress}
     *  
     * @param addr {@link ServerAddress} server address
     */
    public void initMongoDbConnection(ServerAddress addr) {
        mongoClient = new MongoClient(addr);
    }
    
    /**
     * Initializes the Mongo DB connection with a list of {@link ServerAddress} addresses
     * 
     * @param seeds A list of {@link ServerAddress} server addresses
     */
    public void initMongoDbConnection(List<ServerAddress> seeds) {
        mongoClient = new MongoClient(seeds);
    }
    
    /**
     * Initializes the Mongo DB connection with a list of {@link ServerAddress} and {@link MongoCredential}
     * 
     * @param seeds A list of {@link ServerAddress} server addresses
     * @param credentialsList A list of {@link MongoCredential} credentials
     */
    public void initMongoDbConnection(List<ServerAddress> seeds, List<MongoCredential> credentialsList) {
        mongoClient = new MongoClient(seeds, credentialsList);
    }
    
    /**
     * Initializes the Mongo DB connection with a list of {@link ServerAddress}, {@link MongoCredential}
     * and {@link MongoClientOptions}
     * 
     * @param seeds A list of {@link ServerAddress} server addresses
     * @param credentialsList A list of {@link MongoCredential} credentials
     * @param options {@link MongoClientOptions} options
     */
    public void initMongoDbConnection(List<ServerAddress> seeds, List<MongoCredential> credentialsList, MongoClientOptions options) {
        mongoClient = new MongoClient(seeds, credentialsList, options);
    }
    
    /**
     * Initializes the Mongo DB connection with a list of {@link ServerAddress} and {@link MongoClientOptions}
     * 
     * @param seeds A list of {@link ServerAddress} server addresses
     * @param options {@link MongoClientOptions} options
     */
    public void initMongoDbConnection(List<ServerAddress> seeds, MongoClientOptions options) {
        mongoClient = new MongoClient(seeds, options);
    }
    
    /**
     * Initializes the Mongo DB connection with {@link ServerAddress} and a list of {@link MongoCredential} credentials
     * 
     * @param addr {@link ServerAddress} server address
     * @param credentialsList A list of {@link MongoCredential} credentials
     */
    public void initMongoDbConnection(ServerAddress addr, List<MongoCredential> credentialsList) {
        mongoClient = new MongoClient(addr, credentialsList);
    }
    
    /**
     * Initializes the Mongo DB connection with a list of {@link ServerAddress}, {@link MongoCredential}
     * and {@link MongoClientOptions}
     * 
     * @param addr A list of {@link ServerAddress} server addresses
     * @param credentialsList A list of {@link MongoCredential} credentials
     * @param options {@link MongoClientOptions} options
     */
    public void initMongoDbConnection(ServerAddress addr, List<MongoCredential> credentialsList, MongoClientOptions options) {
        mongoClient = new MongoClient(addr, credentialsList, options);
    }
    
    /**
     * Initializes the Mongo DB connection with a {@link ServerAddress} and {@link MongoClientOptions}
     * 
     * @param addr A list of {@link ServerAddress} server addresses
     * @param options {@link MongoClientOptions} options
     */
    public void initMongoDbConnection(ServerAddress addr, MongoClientOptions options) {
        mongoClient = new MongoClient(addr, options);
    }
    
    /**
     * Initializes the Mongo DB connection with a connection url
     * 
     * @param url Url string
     */
    public void initKlovServerConnection(String url) {
        this.url = url;
    }
    
    @Override
    public void start() {
        db = mongoClient.getDatabase(DB_NAME);
        
        // collections
        projectCollection = db.getCollection("project");
        reportCollection = db.getCollection("report");
        testCollection = db.getCollection("test");
        logCollection = db.getCollection("log");
        exceptionCollection = db.getCollection("exception");
        mediaCollection = db.getCollection("media");
        categoryCollection = db.getCollection("category");
        authorCollection = db.getCollection("author");
        environmentCollection = db.getCollection("environment");
        
        setupProject();
    }

    private void setupProject() {
        String projectName = 
                this.projectName == null || this.projectName.isEmpty()
                ? DEFAULT_PROJECT_NAME
                : this.projectName;

        Document doc = new Document("name", projectName);
        Document project = projectCollection.find(doc).first();
        
        if (project != null) {
            projectId = project.getObjectId("_id");
        }
        else {
            projectCollection.insertOne(doc);
            projectId = MongoUtil.getId(doc);
        }
        
        setupReport(projectName);
    }
    
    private void setupReport(String projectName) {
        String reportName =
                this.reportName == null || this.reportName.isEmpty()
                ? "Build " + Calendar.getInstance().getTimeInMillis()
                : this.reportName;
        
        Document doc = new Document("name", reportName)
                .append("startTime", getStartTime())
                .append("project", projectId)
                .append("projectName", projectName);

        reportCollection.insertOne(doc);
        reportId = MongoUtil.getId(doc);
    }
    
    @Override
    public void stop() {
        mongoClient.close();
    }

    @Override
    public synchronized void flush() {
        setEndTime(Calendar.getInstance().getTime());
        
        if (testList == null || testList.size() == 0)
            return;
        
        List<String> categoryNameList = null;
        List<ObjectId> categoryIdList = null;
        
        if (categoryNameObjectIdCollection == null)
            categoryNameObjectIdCollection = new HashMap<String, ObjectId>();
        
        if (!categoryNameObjectIdCollection.isEmpty()) {
            categoryNameList = categoryNameObjectIdCollection.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
            
            categoryIdList = categoryNameObjectIdCollection.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        
        Document doc = new Document("endTime", getEndTime())
                        .append("duration", getRunDuration())
                        .append("parentLength", sc.getParentCount())
                        .append("passParentLength", sc.getParentCountPass())
                        .append("failParentLength", sc.getParentCountFail())
                        .append("fatalParentLength", sc.getParentCountFatal())
                        .append("errorParentLength", sc.getParentCountError())
                        .append("warningParentLength", sc.getParentCountWarning())
                        .append("skipParentLength", sc.getParentCountSkip())
                        .append("exceptionsParentLength", sc.getChildCountExceptions())
                        
                        .append("childLength", sc.getChildCount())
                        .append("passChildLength", sc.getChildCountPass())
                        .append("failChildLength", sc.getChildCountFail())
                        .append("fatalChildLength", sc.getChildCountFatal())
                        .append("errorChildLength", sc.getChildCountError())
                        .append("warningChildLength", sc.getChildCountWarning())
                        .append("skipChildLength", sc.getChildCountSkip())
                        .append("infoChildLength", sc.getChildCountInfo())
                        .append("exceptionsChildLength", sc.getChildCountExceptions())
                        
                        .append("grandChildLength", sc.getGrandChildCount())
                        .append("passGrandChildLength", sc.getGrandChildCountPass())
                        .append("failGrandChildLength", sc.getGrandChildCountFail())
                        .append("fatalGrandChildLength", sc.getGrandChildCountFatal())
                        .append("errorGrandChildLength", sc.getGrandChildCountError())
                        .append("warningGrandChildLength", sc.getGrandChildCountWarning())
                        .append("skipGrandChildLength", sc.getGrandChildCountSkip())
                        .append("exceptionsGrandChildLength", sc.getGrandChildCountExceptions())
                        
                        .append("categoryNameList", categoryNameList)
                        .append("categoryIdList", categoryIdList);
        
        reportCollection.updateOne(
                new Document("_id", reportId), 
                new Document("$set", doc));
        
        insertUpdateSystemAttribute();
    }
    
    private void insertUpdateSystemAttribute() {
        Document doc;
        
        List<SystemAttribute> systemAttrList = getSystemAttributeContext().getSystemAttributeList();
        for (SystemAttribute sysAttr : systemAttrList) {
            doc = new Document("project", projectId)
                    .append("report", reportId)
                    .append("name", sysAttr.getName());
            
            Document envSingle = environmentCollection.find(doc).first();
            
            if (envSingle == null) {
                doc.append("value", sysAttr.getValue());
                environmentCollection.insertOne(doc);
            } else {
                ObjectId id = envSingle.getObjectId("_id");
                
                doc = new Document("_id", id)
                        .append("value", sysAttr.getValue());
                
                environmentCollection.updateOne(
                        new Document("_id", id),
                        new Document("$set", doc));
            }
        }
    }
    
    @Override
    public void onTestStarted(Test test) {
        Document doc = new Document("project", projectId)
                .append("report", reportId)
                .append("level", test.getLevel())
                .append("name", test.getName())
                .append("status", test.getStatus().toString())
                .append("description", test.getDescription())
                .append("startTime", test.getStartTime())
                .append("endTime", test.getEndTime())
                .append("bdd", test.isBehaviorDrivenType())
                .append("leaf", test.getNodeContext().size()==0)
                .append("childNodesLength", test.getNodeContext().size());
        
        if (test.isBehaviorDrivenType())
            doc.append("bddType", test.getBehaviorDrivenType().getSimpleName());
        
        testCollection.insertOne(doc);
    
        ObjectId testId = MongoUtil.getId(doc);
        test.setObjectId(testId);
    }
    
    @Override
    public synchronized void onNodeStarted(Test node) {
        Document doc = new Document("parent", node.getParent().getObjectId())
                .append("parentName", node.getParent().getName())
                .append("project", projectId)
                .append("report", reportId)
                .append("level", node.getLevel())
                .append("name", node.getName())
                .append("status", node.getStatus().toString())
                .append("description", node.getDescription())
                .append("startTime", node.getStartTime())
                .append("endTime", node.getEndTime())
                .append("bdd", node.isBehaviorDrivenType())
                .append("leaf", node.getNodeContext().size()==0)
                .append("childNodesLength", node.getNodeContext().size());

        if (node.isBehaviorDrivenType())
            doc.append("bddType", node.getBehaviorDrivenType().getSimpleName());
        
        testCollection.insertOne(doc);
        
        ObjectId nodeId = MongoUtil.getId(doc);
        node.setObjectId(nodeId);
        
        // update parent test stats
        updateTestBasedOnNode(node.getParent());
    }
    
    private void updateTestBasedOnNode(Test test) {
        Document doc = new Document("childNodesLength", test.getNodeContext().size());
        
        testCollection.updateOne(
                new Document("_id", test.getObjectId()),
                new Document("$set", doc));
    }

    @Override
    public synchronized void onLogAdded(Test test, Log log) {
        Document doc = new Document("test", test.getObjectId())
                .append("project", projectId)
                .append("report", reportId)
                .append("testName", test.getName())
                .append("sequence", log.getSequence())
                .append("status", log.getStatus().toString())
                .append("timestamp", log.getTimestamp())
                .append("details", log.getDetails());

        logCollection.insertOne(doc);
        
        ObjectId logId = MongoUtil.getId(doc);
        log.setObjectId(logId);
        
        // check for exceptions..
        if (test.hasException()) {
            if (exceptionNameObjectIdCollection == null)
                exceptionNameObjectIdCollection = new HashMap<>();
            
            ExceptionInfo ex = test.getExceptionInfoList().get(0);
            
            ObjectId exceptionId;
            doc = new Document("report", reportId)
                    .append("project", projectId)
                    .append("name", ex.getExceptionName());
                    
            FindIterable<Document> iterable = exceptionCollection.find(doc);
            Document docException = iterable.first();
            
            // check if a matching exception name is available in 'Exception' collection (MongoDB)
            // if a matching exception name is found, associate with this exception's ObjectId
            if (!exceptionNameObjectIdCollection.containsKey(ex.getExceptionName())) {               
                if (docException != null) {
                    exceptionNameObjectIdCollection.put(ex.getExceptionName(), docException.getObjectId("_id"));
                } else {
                    doc = new Document("project", projectId)
                            .append("report", reportId)
                            .append("name", ex.getExceptionName())
                            .append("stacktrace", ex.getStackTrace())
                            .append("testCount", 0);
                    
                    exceptionCollection.insertOne(doc);
                    
                    exceptionId = MongoUtil.getId(doc);
                    docException = exceptionCollection.find(new Document("_id", exceptionId)).first();
                    
                    exceptionNameObjectIdCollection.put(ex.getExceptionName(), exceptionId);
                }
            }

            Integer testCount = ((Integer) (docException.get("testCount"))) + 1;
            doc = new Document("testCount", testCount);
            
            exceptionCollection.updateOne(
                    new Document("_id", docException.getObjectId("_id")),
                    new Document("$set", doc));
            
            doc = new Document("exception", exceptionNameObjectIdCollection.get(ex.getExceptionName()));
            
            testCollection.updateOne(
                    new Document("_id", test.getObjectId()),
                    new Document("$set", doc));
        }
        
        endTestRecursive(test);
    }
    
    private void endTestRecursive(Test test) {
        Document doc = new Document("status", test.getStatus().toString())
                .append("endTime", test.getEndTime())
                .append("duration", test.getRunDurationMillis())
                .append("leaf", test.getNodeContext().size()==0)
                .append("childNodesLength", test.getNodeContext().size())
                .append("categorized", test.hasCategory());
        
        if (test.hasCategory()) {
            List<String> categoryNameList = test.getCategoryContext().getAll()
                .stream()
                .map(TestAttribute::getName)
                .collect(Collectors.toList());
            
            doc.append("categoryNameList", categoryNameList);
        }
        
        testCollection.updateOne(
                new Document("_id", test.getObjectId()),
                new Document("$set", doc));

        if (test.getLevel() > 0)
            endTestRecursive(test.getParent());
    }

    @Override
    public void onCategoryAssigned(Test test, Category category) {
        if (categoryNameObjectIdCollection == null)
            categoryNameObjectIdCollection = new HashMap<>();
        
        Document doc;
        
        if (!categoryNameObjectIdCollection.containsKey(category.getName())) {
            doc = new Document("report", reportId)
                    .append("project", projectId)
                    .append("name", category.getName());
                    
            FindIterable<Document> iterable = categoryCollection.find(doc);
            Document docCategory = iterable.first();
            
            if (docCategory != null) {
                categoryNameObjectIdCollection.put(category.getName(), docCategory.getObjectId("_id"));
            } else {
                doc = new Document("tests", test.getObjectId())
                        .append("project", projectId)
                        .append("report", reportId)
                        .append("name", category.getName())
                        .append("status", test.getStatus().toString())
                        .append("testName", test.getName());
                
                categoryCollection.insertOne(doc);
                
                ObjectId categoryId = MongoUtil.getId(doc);
                
                categoryNameObjectIdCollection.put(category.getName(), categoryId);
            }
        }
    }

    @Override
    public void onAuthorAssigned(Test test, Author author) { 
        Document doc = new Document("tests", test.getObjectId())
                .append("project", projectId)
                .append("report", reportId)
                .append("name", author.getName())
                .append("status", test.getStatus().toString())
                .append("testName", test.getName());

        authorCollection.insertOne(doc);
    }
    
    @Override
    public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) throws IOException {
        initOnScreenCaptureAdded(screenCapture);
        createMedia(test, screenCapture);
        storeMedia(screenCapture);
    }
    
    @Override
    public void onScreenCaptureAdded(Log log, ScreenCapture screenCapture) throws IOException {
        screenCapture.setLogObjectId(log.getObjectId());
        
        initOnScreenCaptureAdded(screenCapture);
        createMedia(log, screenCapture);
        storeMedia(screenCapture);
    }
    
    private void storeMedia(ScreenCapture screenCapture) throws IOException {
        media.storeMedia(screenCapture);
    }
    
    private void initOnScreenCaptureAdded(ScreenCapture screenCapture) throws IOException {
        storeUrl();
        screenCapture.setReportObjectId(reportId);
        initMedia();
    }
    
    private void storeUrl() throws IOException {
        if (url == null) {
            throw new IOException("server url cannot be null, use klov.setKlovUrl(url)");
        }
    }
    
    private void createMedia(BasicReportElement el, Media media) {
        Document doc = new Document("project", projectId)
                .append("report", reportId)
                .append("sequence", media.getSequence())
                .append("mediaType", media.getMediaType().toString().toLowerCase())
                .append("test", media.getTestObjectId());

        if (el.getClass() != Test.class) {
            doc.append("log", el.getObjectId());
        }
        
        mediaCollection.insertOne(doc);
        
        ObjectId mediaId = MongoUtil.getId(doc);
        media.setObjectId(mediaId);
    }
    
    private void initMedia() throws IOException {
        if (media == null) {
            media = new MediaStorageManagerFactory().getManager("http-klov");
            media.init(url);
        }
    }
    
    @Override
    public void onScreencastAdded(Test test, Screencast screencast) throws IOException {
        storeUrl();
        screencast.setReportObjectId(reportId);
        
        createMedia(test, screencast);
        
        initMedia();
        
        media.storeMedia(screencast);
    }
    
    @Override
    public void setTestList(List<Test> reportTestList) {
        testList = reportTestList;
    }
    
    public List<Test> getTestList() {
        if (testList == null)
            testList = new ArrayList<>();
        
        return testList;
    }

    /**
     * Returns the active Project ID
     * 
     * @return A {@link ObjectId} object
     */
    public ObjectId getProjectId() {       
        return projectId;       
    }      
    
    /**
     * Returns the active Report ID 
     * 
     * @return A {@link ObjectId} object
     */
    public ObjectId getReportId() {     
        return reportId;        
    }
    
}