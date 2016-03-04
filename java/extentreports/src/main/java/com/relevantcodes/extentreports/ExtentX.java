package com.relevantcodes.extentreports;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.utils.MongoUtil;

public class ExtentX extends LogSettings implements IReporter {
    private Report report;
    
    private ObjectId reportId;
    
    private MongoClient mongoClient;
    private MongoDatabase db;
    
    private MongoCollection<Document> reportCollection;
    private MongoCollection<Document> testCollection;
    private MongoCollection<Document> nodeCollection;
    private MongoCollection<Document> logCollection;
    private MongoCollection<Document> categoryCollection;
    private MongoCollection<Document> authorCollection;

    @Override
    public void start(Report report) {
        this.report = report;
        
        db = mongoClient.getDatabase("extent");
        
        // collections
        reportCollection = db.getCollection("report");
        testCollection = db.getCollection("test");
        nodeCollection = db.getCollection("node");
        logCollection = db.getCollection("log");
        categoryCollection = db.getCollection("category");
        authorCollection = db.getCollection("author");
        
        insertReport();
    }
    
    private void insertReport() {
        Document doc = new Document("fileName", new File(report.getFilePath()).getName())
                        .append("startTime", report.getStartedTime());
        
        reportCollection.insertOne(doc);
        
        reportId = MongoUtil.getId(doc);
    }
    
    @Override
    public void stop() {
        System.out.println("stop");
    }
    
    @Override
    public void flush() {
        reportCollection.updateOne(
                new Document("_id", reportId),
                new Document("$set", 
                            new Document("endTime", new Date(report.getSuiteTimeInfo().getSuiteEndTimestamp()))
                                .append("status", report.getStatus().toString()))
        );
    }
    
    @Override
    public void addTest() {
        Test test = report.getCurrentTest();
        
        Document doc = new Document("owner", reportId)
                        .append("name", test.getName())
                        .append("status", test.getStatus().toString())
                        .append("description", test.getDescription())
                        .append("startTime", test.getStartedTime())
                        .append("endTime", test.getEndedTime())
                        .append("childNodesCount", getNodeLength(test, 0));
        
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
        Log log;
        int ix = 0;
        Document doc;
        
        while (iter.hasNext()) {
            log = iter.next();
            
            doc = new Document("owner", testId)
                    .append("reportId", reportId)
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
        if (test.hasChildNodes) {
            for (Test node : test.getNodeList()) {
                addNode(node, testId, ++level);
                --level;
                
                if (node.hasChildNodes) {
                    addNodes(node, testId, ++level);
                    --level;
                }
            }
        }
    }
    
    private void addNode(Test node, ObjectId testId, int level) {
        Document doc = new Document("owner", testId)
                        .append("reportId", reportId)
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
            
            Document doc = new Document("owner", testId)
                            .append("reportId", reportId)
                            .append("name", category.getName())
                            .append("testName", test.getName())
                            .append("status", test.getStatus().toString());
            
            categoryCollection.insertOne(doc);
        }
    }
    
    private void addAuthors(Test test, ObjectId testId) {
        Iterator<TestAttribute> authors = test.authorIterator();
        
        while (authors.hasNext()) {
            TestAttribute category = authors.next();
            
            Document doc = new Document("owner", testId)
                            .append("reportId", reportId)
                            .append("name", category.getName())
                            .append("testName", test.getName())
                            .append("status", test.getStatus().toString());
            
            authorCollection.insertOne(doc);
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
}
