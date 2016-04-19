package com.relevantcodes.extentreports;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
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
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.utils.MongoUtil;

public class ExtentX extends LogSettings implements IReporter {
    private Report report;
    
    private String testName;
    
    private ObjectId reportId;
    
    private MongoClient mongoClient;
    private MongoDatabase db;
    
    private MongoCollection<Document> projectCollection;
    private MongoCollection<Document> reportCollection;
    private MongoCollection<Document> testCollection;
    private MongoCollection<Document> nodeCollection;
    private MongoCollection<Document> logCollection;
    private MongoCollection<Document> categoryCollection;
    private MongoCollection<Document> authorCollection;
    private MongoCollection<Document> categoriesTests;
    private MongoCollection<Document> authorsTests;

    @Override
    public void start(Report report) {
        this.report = report;
        
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
        categoriesTests = db.getCollection("category_tests__test_categories");
        authorsTests = db.getCollection("author_tests__test_authors");
    }
    
    @Override
    public void stop() {
        mongoClient.close();
    }
    
    @Override
    public void flush() {
        if (reportId == null)
            insertReport();
        
        reportCollection.updateOne(
                new Document("_id", reportId),
                new Document("$set", 
                            new Document("endTime", new Date(report.getSuiteTimeInfo().getSuiteEndTimestamp()))
                                .append("status", report.getStatus().toString())));
    }
    
    private ObjectId getProjectId() {
        String projectName = report.getProjectName();
        
        Document doc = new Document("name", projectName);
        FindIterable<Document> iterable = projectCollection.find(doc);
        
        Document project = iterable.first();
        ObjectId projectId;
        
        if (project != null) {
            projectId = project.getObjectId("_id");
        }
        else {
            projectCollection.insertOne(doc);
            projectId = MongoUtil.getId(doc);
        }
        
        return projectId;
    }
    
    private void insertReport() {
        ObjectId projectId = getProjectId();
        
        String id = report.getMongoDBObjectID();
        
        // if extent is started with [replaceExisting = false] and ExtentX is used,
        // use the same report ID for the 1st report run and update the database for
        // the corresponding report-ID
        if (id != null & !id.isEmpty()) {
            FindIterable<Document> iterable = reportCollection.find(new Document("_id", new ObjectId(id)));
            Document report = iterable.first();
            
            if (report != null) {
                reportId = report.getObjectId("_id");
                return;
            }
        }
        
        // if [replaceExisting = true] or the file does not exist, create a new
        // report-ID and assign all components to it
        Document doc = new Document("project", projectId)
                        .append("fileName", new File(report.getFilePath()).getName())
                        .append("startTime", report.getStartedTime());
        
        reportCollection.insertOne(doc);
        
        reportId = MongoUtil.getId(doc);
        report.setMongoDBObjectID(reportId.toString());
    }
    
    @Override
    public void addTest(Test test) {
        if (reportId == null)
            insertReport();

        testName = test.getName();
        
        Document doc = new Document("report", reportId)
                        .append("name", testName)
                        .append("status", test.getStatus().toString())
                        .append("description", test.getDescription())
                        .append("startTime", test.getStartedTime())
                        .append("endTime", test.getEndedTime())
                        .append("childNodesCount", getNodeLength(test, 0))
                        .append("categorized", test.getCategoryList().size() > 0 ? true : false);
        
        testCollection.insertOne(doc);
        
        ObjectId testId = MongoUtil.getId(doc);
        
        // add logs
        addLogs(test, testId);
        
        // add child nodes
        addNodes(test, testId, 0);
        
        // add categories
        addCategories(test, testId);
        
        // add authors
        addAuthors(test, testId);
    }
    
    private int getNodeLength(Test test, int length) {
        for (Test node : test.getNodeList()) {
            length++;
            
            if (node.hasChildNodes) {
                length = getNodeLength(node, length);
            }
        }
        
        return length;
    }
    
    private void addLogs(Test test, ObjectId testId) {
        Iterator<Log> iter = test.logIterator();
        Log log; Document doc;
        int ix = 0;
        
        while (iter.hasNext()) {
            log = iter.next();
            
            doc = new Document("test", testId)
                    .append("report", reportId)
                    .append("testName", test.getName())
                    .append("logSequence", ix++)
                    .append("status", log.getLogStatus().toString())
                    .append("timestamp", log.getTimestamp())
                    .append("stepName", log.getStepName())
                    .append("details", log.getDetails());
            
            logCollection.insertOne(doc);
        }
    }
    
    private void addNodes(Test test, ObjectId testId, int level) {
        if (!test.hasChildNodes)
            return;
        
        for (Test node : test.getNodeList()) {
            addNode(node, testId, ++level);
            --level;
            
            if (node.hasChildNodes) {
                addNodes(node, testId, ++level);
                --level;
            }
        }
    }
    
    private void addNode(Test node, ObjectId testId, int level) {
        Document doc = new Document("test", testId)
                        .append("parentTestName", testName)
                        .append("report", reportId)
                        .append("name", node.getName())
                        .append("level", level)
                        .append("status", node.getStatus().toString())
                        .append("description", node.getDescription())
                        .append("startTime", node.getStartedTime())
                        .append("endTime", node.getEndedTime())
                        .append("childNodesCount", node.getNodeList().size());
        
        nodeCollection.insertOne(doc);
        
        ObjectId nodeId = MongoUtil.getId(doc);
        
        addLogs(node, nodeId);
    }
    
    private void addCategories(Test test, ObjectId testId) {
        Iterator<TestAttribute> categories = test.categoryIterator();
        
        while (categories.hasNext()) {
            TestAttribute category = categories.next();
            
            Document doc = new Document("tests", testId)
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
            doc = new Document("test_categories", testId)
                    .append("category_tests", categoryId)
                    .append("category", category.getName())
                    .append("test", test.getName());
            categoriesTests.insertOne(doc);
        }
    }
    
    private void addAuthors(Test test, ObjectId testId) {
        Iterator<TestAttribute> authors = test.authorIterator();
        
        while (authors.hasNext()) {
            TestAttribute author = authors.next();
            
            Document doc = new Document("tests", testId)
                            .append("report", reportId)
                            .append("name", author.getName())
                            .append("status", test.getStatus().toString())
                            .append("testName", test.getName());
            
            authorCollection.insertOne(doc);
            
            ObjectId authorId = MongoUtil.getId(doc);
            
            doc = new Document("test_authors", testId)
                    .append("author_tests", authorId)
                    .append("author", author.getName())
                    .append("test", test.getName());
            authorsTests.insertOne(doc);
        }
    }

    @Override
    public void setTestRunnerLogs() {
        
    }
    
    public ExtentX() {
        mongoClient = new MongoClient("localhost", 27017);
    }
    
    public ExtentX(String host) {
        mongoClient = new MongoClient(host, 27017);
    }
    
    public ExtentX(String host,  MongoClientOptions options) {
        mongoClient = new MongoClient(host, options);
    }
    
    public ExtentX(String host, int port) {
        mongoClient = new MongoClient(host, port);
    }
    
    public ExtentX(MongoClientURI uri) {
        mongoClient = new MongoClient(uri);        
    }
    
    public ExtentX(ServerAddress addr) {
        mongoClient = new MongoClient(addr);
    }
    
    public ExtentX(List<ServerAddress> seeds) {
        mongoClient = new MongoClient(seeds);
    }
    
    public ExtentX(List<ServerAddress> seeds, List<MongoCredential> credentialsList) {
        mongoClient = new MongoClient(seeds, credentialsList);
    }
    
    public ExtentX(List<ServerAddress> seeds, List<MongoCredential> credentialsList, MongoClientOptions options) {
        mongoClient = new MongoClient(seeds, credentialsList, options);
    }
    
    public ExtentX(List<ServerAddress> seeds, MongoClientOptions options) {
        mongoClient = new MongoClient(seeds, options);
    }
    
    public ExtentX(ServerAddress addr, List<MongoCredential> credentialsList) {
        mongoClient = new MongoClient(addr, credentialsList);
    }
    
    public ExtentX(ServerAddress addr, List<MongoCredential> credentialsList, MongoClientOptions options) {
        mongoClient = new MongoClient(addr, credentialsList, options);
    }
    
    public ExtentX(ServerAddress addr, MongoClientOptions options) {
        mongoClient = new MongoClient(addr, options);
    }
    
    static {
        // set mongodb reporting for only critical/severe events
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }
}
