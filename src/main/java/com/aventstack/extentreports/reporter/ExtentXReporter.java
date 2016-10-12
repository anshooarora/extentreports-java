package com.aventstack.extentreports.reporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.aventstack.extentreports.configuration.Config;
import com.aventstack.extentreports.configuration.ConfigMap;
import com.aventstack.extentreports.mediastorage.MediaStorage;
import com.aventstack.extentreports.mediastorage.MediaStorageManagerFactory;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.configuration.ExtentXReporterConfiguration;
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
public class ExtentXReporter extends AbstractReporter {

    private static final String DEFAULT_CONFIG_FILE = "extentx-config.properties";
    private static final String DEFAULT_PROJECT_NAME = "Default";
    
    private String url;
    
    private Map<String, ObjectId> categoryNameObjectIdCollection;
    
    private ObjectId reportId;
    private ObjectId projectId;
    
    private MongoClient mongoClient;
    
    private MongoDatabase db;
    
    private MongoCollection<Document> projectCollection;
    private MongoCollection<Document> reportCollection;
    private MongoCollection<Document> testCollection;
    private MongoCollection<Document> nodeCollection;
    private MongoCollection<Document> logCollection;
    private MongoCollection<Document> mediaCollection;
    private MongoCollection<Document> categoryCollection;
    private MongoCollection<Document> authorCollection;
    private MongoCollection<Document> categoryTestsTestCategories;
    private MongoCollection<Document> authorTestsTestAuthors;
    
    private MediaStorage media;
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
        mediaCollection = db.getCollection("media");
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
        
        ClassLoader loader = getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream(DEFAULT_CONFIG_FILE);
        loadConfig(is);
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
        if (projectName == null || projectName.isEmpty())
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
        if (reportName == null || reportName.isEmpty())
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
                        .append("parentLength", sc.getParentCount())
                        .append("passParentLength", sc.getParentCountPass())
                        .append("failParentLength", sc.getParentCountFail())
                        .append("fatalParentLength", sc.getParentCountFatal())
                        .append("errorParentLength", sc.getParentCountError())
                        .append("warningParentLength", sc.getParentCountWarning())
                        .append("skipParentLength", sc.getParentCountSkip())
                        .append("unknownParentLength", sc.getParentCountUnknown())
                        .append("childLength", sc.getChildCount())
                        .append("passChildLength", sc.getChildCountPass())
                        .append("failChildLength", sc.getChildCountFail())
                        .append("fatalChildLength", sc.getChildCountFatal())
                        .append("errorChildLength", sc.getChildCountError())
                        .append("warningChildLength", sc.getChildCountWarning())
                        .append("skipChildLength", sc.getChildCountSkip())
                        .append("unknownChildLength", sc.getChildCountUnknown())
                        .append("infoChildLength", sc.getChildCountInfo())
                        .append("grandChildLength", sc.getGrandChildCount())
                        .append("passGrandChildLength", sc.getGrandChildCountPass())
                        .append("failGrandChildLength", sc.getGrandChildCountFail())
                        .append("fatalGrandChildLength", sc.getGrandChildCountFatal())
                        .append("errorGrandChildLength", sc.getGrandChildCountError())
                        .append("warningGrandChildLength", sc.getGrandChildCountWarning())
                        .append("skipGrandChildLength", sc.getGrandChildCountSkip())
                        .append("unknownGrandChildLength", sc.getGrandChildCountUnknown())
                        .append("infoGrandChildLength", sc.getGrandChildCountInfo());
        
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
        String model = "test";
        
        if (test.getLevel() > 0)
            model = "node";
        
        Document doc = new Document(model, test.getObjectId())
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
        if (categoryNameObjectIdCollection == null)
            categoryNameObjectIdCollection = new HashMap<>();
        
        Document doc;
        
        if (!categoryNameObjectIdCollection.containsKey(category.getName())) {
            doc = new Document("report", reportId)
                    .append("name", category.getName());
                    
            FindIterable<Document> iterable = categoryCollection.find(doc);
            Document docCategory = iterable.first();
            
            if (docCategory != null) {
                categoryNameObjectIdCollection.put(category.getName(), docCategory.getObjectId("_id"));
            } else {
                doc = new Document("tests", test.getObjectId())
                        .append("report", reportId)
                        .append("name", category.getName())
                        .append("status", test.getStatus().toString())
                        .append("testName", test.getName());
                
                categoryCollection.insertOne(doc);
                
                ObjectId categoryId = MongoUtil.getId(doc);
                
                categoryNameObjectIdCollection.put(category.getName(), categoryId);
            }
        }
        
        /* create association with category
         * tests (many) <-> categories (many)
         * tests and categories have a many to many relationship
         *   - a test can be assigned with one or more categories
         *   - a category can have one or more tests
         */
        doc = new Document("test_categories", test.getObjectId())
                .append("category_tests", categoryNameObjectIdCollection.get(category.getName()))
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
    public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) throws IOException {
        storeUrl();
        screenCapture.setReportObjectId(reportId);
        
        createMedia(test, screenCapture);
        
        if (media == null) {
            media = new MediaStorageManagerFactory().getManager("http");
            media.init(url);
        }
        
        media.storeMedia(screenCapture);
    }
    
    private void storeUrl() throws IOException {
        if (this.url == null) {
            Object url = configContext.getValue("serverUrl");
            
            if (url == null) {
                throw new IOException("server url cannot be null, use extentxConfig.setServerUrl(url)");
            }
            
            this.url = url.toString().trim();
        }
    }
    
    private void createMedia(Test test, ScreenCapture screenCapture) {
        String model = "test";
        if (test.getLevel() > 0)
            model = "node";
        
        Document doc = new Document(model, test.getObjectId())
                .append("report", reportId)
                .append("testName", test.getName())
                .append("sequence", screenCapture.getSequence());

        mediaCollection.insertOne(doc);
        
        ObjectId mediaId = MongoUtil.getId(doc);
        screenCapture.setObjectId(mediaId);
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
}
