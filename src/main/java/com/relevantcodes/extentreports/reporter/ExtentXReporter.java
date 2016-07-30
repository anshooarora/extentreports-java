package com.relevantcodes.extentreports.reporter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.relevantcodes.extentreports.configuration.Config;
import com.relevantcodes.extentreports.configuration.ConfigMap;
import com.relevantcodes.extentreports.model.Author;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.reporter.configuration.ExtentXReporterConfiguration;
import com.relevantcodes.extentreports.utils.MongoUtil;

public class ExtentXReporter extends AbstractReporter {

    private static final String DEFAULT_CONFIG_FILE = "extentx-config.properties";
    private static final String DEFAULT_PROJECT_NAME = "Default";
    
    private ObjectId reportId;
    private ObjectId projectId;
    
    private MongoClient mongoClient;
    
    private MongoDatabase db;
    
    private MongoCollection<Document> projectCollection;
    private MongoCollection<Document> reportCollection;
    private MongoCollection<Document> testCollection;
    private MongoCollection<Document> nodeCollection;
    private MongoCollection<Document> logCollection;
    private MongoCollection<Document> categoryCollection;
    private MongoCollection<Document> authorCollection;
    private MongoCollection<Document> categoryTestsTestCategories;
    private MongoCollection<Document> authorTestsTestAuthors;
    
    private ExtentXReporterConfiguration userConfig;
    
    static {
        /* use mongodb reporting for only critical/severe events */
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    ExtentXReporter() {
        loadDefaultConfig();
    }
    
    public ExtentXReporter(String host) {
        this();
        mongoClient = new MongoClient(host, 27017);
    }
    
    public ExtentXReporter(String host,  MongoClientOptions options) {
        this();
        mongoClient = new MongoClient(host, options);
    }
    
    public ExtentXReporter(String host, int port) {
        this();
        mongoClient = new MongoClient(host, port);
    }
    
    public ExtentXReporter(MongoClientURI uri) {
        this();
        mongoClient = new MongoClient(uri);        
    }
    
    public ExtentXReporter(ServerAddress addr) {
        this();
        mongoClient = new MongoClient(addr);
    }
    
    public ExtentXReporter(List<ServerAddress> seeds) {
        this();
        mongoClient = new MongoClient(seeds);
    }
    
    public ExtentXReporter(List<ServerAddress> seeds, List<MongoCredential> credentialsList) {
        this();
        mongoClient = new MongoClient(seeds, credentialsList);
    }
    
    public ExtentXReporter(List<ServerAddress> seeds, List<MongoCredential> credentialsList, MongoClientOptions options) {
        this();
        mongoClient = new MongoClient(seeds, credentialsList, options);
    }
    
    public ExtentXReporter(List<ServerAddress> seeds, MongoClientOptions options) {
        this();
        mongoClient = new MongoClient(seeds, options);
    }
    
    public ExtentXReporter(ServerAddress addr, List<MongoCredential> credentialsList) {
        this();
        mongoClient = new MongoClient(addr, credentialsList);
    }
    
    public ExtentXReporter(ServerAddress addr, List<MongoCredential> credentialsList, MongoClientOptions options) {
        this();
        mongoClient = new MongoClient(addr, credentialsList, options);
    }
    
    public ExtentXReporter(ServerAddress addr, MongoClientOptions options) {
        this();
        mongoClient = new MongoClient(addr, options);
    }
    
    public ExtentXReporterConfiguration config() {       
        return userConfig;
    }
    
    @Override
    public void start() {
        loadUserConfig();
        
        db = mongoClient.getDatabase("extent");
        
        // collections
        projectCollection = db.getCollection("project");
        reportCollection = db.getCollection("report");
        testCollection = db.getCollection("test");
        nodeCollection = db.getCollection("node");
        logCollection = db.getCollection("log");
        categoryCollection = db.getCollection("category");
        authorCollection = db.getCollection("author");
        
        // many-to-many
        categoryTestsTestCategories = db.getCollection("category_tests__test_categories");
        authorTestsTestAuthors = db.getCollection("author_tests__test_authors");
        
        setupProject();
    }
    
    private void loadDefaultConfig() {
        configContext = new ConfigMap();
        userConfig = new ExtentXReporterConfiguration();
        
        loadConfig(getClass().getClassLoader().getResource(DEFAULT_CONFIG_FILE).getPath());
    }
    
    private void loadUserConfig() {
        userConfig.getConfigMap().forEach(
            (k, v) -> {
                if (v != null) {
                    Config c = new Config();
                    c.setKey(k);
                    c.setValue(v);
                    
                    configContext.setConfig(c); 
                }
            }
        );
    }

    private void setupProject() {
        String projectName = configContext.getValue("projectName").toString().trim();
        if (projectName.equals(null) || projectName.isEmpty())
            projectName = DEFAULT_PROJECT_NAME;

        Document doc = new Document("name", projectName);
        FindIterable<Document> iterable = projectCollection.find(doc);
        
        Document project = iterable.first();
        
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
        String reportName = configContext.getValue("reportName").toString().trim();
        if (reportName.equals(null) || reportName.isEmpty())
            reportName = projectName + " - " + Calendar.getInstance().getTimeInMillis();
        
        Object id = configContext.getValue("reportId");
        if (id != null && !id.toString().isEmpty()) {
            FindIterable<Document> iterable = reportCollection.find(new Document("_id", new ObjectId(id.toString())));
            Document report = iterable.first();
            
            if (report != null) {
                reportId = report.getObjectId("_id");
                return;
            }
        }
        
        Document doc = new Document("fileName", reportName)
                .append("startTime", getStartTime())
                .append("project", projectId);

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
        
        Document doc = new Document("endTime", getEndTime())
                        .append("passParent", sc.getParentCountPass())
                        .append("failParent", sc.getParentCountFail())
                        .append("fatalParent", sc.getParentCountFatal())
                        .append("errorParent", sc.getParentCountError())
                        .append("warningParent", sc.getParentCountWarning())
                        .append("skipParent", sc.getParentCountSkip())
                        .append("unknownParent", sc.getParentCountUnknown())
                        .append("passChild", sc.getChildCountPass())
                        .append("failChild", sc.getChildCountFail())
                        .append("fatalChild", sc.getChildCountFatal())
                        .append("errorChild", sc.getChildCountError())
                        .append("warningChild", sc.getChildCountWarning())
                        .append("SkipChild", sc.getChildCountSkip())
                        .append("unknownChild", sc.getChildCountUnknown())
                        .append("infoChild", sc.getChildCountInfo())
                        .append("passGrandChild", sc.getGrandChildCountPass())
                        .append("failGrandChild", sc.getGrandChildCountFail())
                        .append("fatalGrandChild", sc.getGrandChildCountFatal())
                        .append("errorGrandChild", sc.getGrandChildCountError())
                        .append("warningGrandChild", sc.getGrandChildCountWarning())
                        .append("skipGrandChild", sc.getGrandChildCountSkip())
                        .append("unknownGrandChild", sc.getGrandChildCountUnknown())
                        .append("infoGrandChild", sc.getGrandChildCountInfo());
        
        reportCollection.updateOne(
                new Document("_id", reportId), 
                new Document("$set", doc));
    }
    
    @Override
    public void onTestStarted(Test test) {
        Document doc = new Document("report", reportId)
                .append("name", test.getName())
                .append("status", test.getStatus().toString())
                .append("description", test.getDescription())
                .append("startTime", test.getStartTime())
                .append("endTime", test.getEndTime())
                .append("childNodesCount", test.getNodeContext().getAll().size());
        
        testCollection.insertOne(doc);
    
        ObjectId testId = MongoUtil.getId(doc);
        test.setObjectId(testId);
    }
    
    @Override
    public synchronized void onNodeStarted(Test node) {
        Document doc = new Document("test", node.getParent().getObjectId())
                .append("parentTestName", node.getParent().getName())
                .append("report", reportId)
                .append("name", node.getName())
                .append("level", node.getLevel())
                .append("status", node.getStatus().toString())
                .append("description", node.getDescription())
                .append("startTime", node.getStartTime())
                .append("endTime", node.getEndTime());
        
        nodeCollection.insertOne(doc);
        
        ObjectId nodeId = MongoUtil.getId(doc);
        node.setObjectId(nodeId);
    }

    @Override
    public synchronized void onLogAdded(Test test, Log log) {
        Document doc = new Document("test", test.getObjectId())
                .append("report", reportId)
                .append("testName", test.getName())
                .append("logSequence", log.getSequence())
                .append("status", log.getStatus().toString())
                .append("timestamp", log.getTimestamp())
                .append("stepName", log.getStepName())
                .append("details", log.getDetails());

        logCollection.insertOne(doc);

        endTestRecursive(test);
    }
    
    private void endTestRecursive(Test test) {
        Document doc = new Document("status", test.getStatus().toString())
                .append("endTime", test.getEndTime());
        
        if (test.getLevel() == 0) {
            testCollection.updateOne(
                    new Document("_id", test.getObjectId()),
                    new Document("$set", doc));
        }
        else {
            nodeCollection.updateOne(
                    new Document("_id", test.getObjectId()), 
                    new Document("$set", doc));
            
            endTestRecursive(test.getParent());
        }
        
    }

    @Override
    public void onCategoryAssigned(Test test, Category category) { 
        Document doc = new Document("tests", test.getObjectId())
                .append("report", reportId)
                .append("name", category.getName())
                .append("status", test.getStatus().toString())
                .append("testName", test.getName());
        
        categoryCollection.insertOne(doc);
        
        ObjectId categoryId = MongoUtil.getId(doc);
        
        /* create association with category
         * tests (many) <-> categories (many)
         * tests and categories have a many to many relationship
         *   - a test can be assigned with one or more categories
         *   - a category can have one or more tests
         */
        doc = new Document("test_categories", test.getObjectId())
                .append("category_tests", categoryId)
                .append("category", category.getName())
                .append("test", test.getName());
        
        categoryTestsTestCategories.insertOne(doc);
    }

    @Override
    public void onAuthorAssigned(Test test, Author author) { 
        Document doc = new Document("tests", test.getObjectId())
                .append("report", reportId)
                .append("name", author.getName())
                .append("status", test.getStatus().toString())
                .append("testName", test.getName());

        authorCollection.insertOne(doc);
        
        ObjectId authorId = MongoUtil.getId(doc);
        
        doc = new Document("test_authors", test.getObjectId())
                .append("author_tests", authorId)
                .append("author", author.getName())
                .append("test", test.getName());
        
        authorTestsTestAuthors.insertOne(doc);
    }
    
    @Override
    public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) {}
    
    @Override
    public void setTestList(List<Test> reportTestList) {
        testList = reportTestList;
    }
    
    public List<Test> getTestList() {
        if (testList == null)
            testList = new ArrayList<>();
        
        return testList;
    }
}
