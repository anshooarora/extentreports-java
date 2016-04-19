/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;

/**
 * Concrete DBReporter class
 * 
 * @author Anshoo
 *
 */
class DBReporter extends LogSettings implements IReporter {
    static final Logger logger = Logger.getLogger(LogSettings.class.getName()); 
    
    private boolean isReady = false;
    
    private Report report;
    
    private Connection connection;
    
    private Test test;
    private Map<String, String> systemMap;
    
    private final String CREATE_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS Report " + 
                        "(" +
                            "ReportIDExtent TEXT NOT NULL, " +
                            "StartMillis TIME NOT NULL, " +
                            "EndMillis TIME NOT NULL, " +
                            "ReportStatus TEXT NOT NULL" +
                        ")";
    private final String INSERT_REPORT = "INSERT INTO Report " +
                        "( " +
                            "ReportIDExtent, " +
                            "StartMillis, " +
                            "EndMillis, " +
                            "ReportStatus " +
                        ")" +
                        "VALUES (?, ?, ?, ?)";
    private final String UPDATE_REPORT = "UPDATE Report " +
                        "SET " +
                            "EndMillis = ?, " +
                            "ReportStatus = ? " +
                        "WHERE " +
                            "ReportIDExtent = '%%REPORTID%%'";
    
    private final String CREATE_SYSTEM_INFO_TABLE = "CREATE TABLE IF NOT EXISTS SystemInfo " + 
                        "(" +
                            "ReportIDExtent TEXT NOT NULL, " +
                            "Param TEXT NOT NULL, " +
                            "Value TEXT NOT NULL" +
                        ")";
    private final String INSERT_SYSTEM_INFO = "INSERT INTO SystemInfo " +
                        "( " +
                            "ReportIDExtent, " +
                            "Param, " +
                            "Value" +
                        ")" +
                        "VALUES (?, ?, ?)";

    private final String CREATE_TEST_TABLE = "CREATE TABLE IF NOT EXISTS Test " + 
                        "(" +
                            "ReportIDExtent TEXT NOT NULL, " +
                            "TestIDExtent TEXT NOT NULL, " +
                            "TestName TEXT NOT NULL, " +
                            "Status TEXT NOT NULL, " +
                            "Description TEXT, " +
                            "InternalWarning TEXT, " +
                            "StartMillis TIME NOT NULL, " +
                            "EndMillis TIME NOT NULL, " +
                            "StepsPass INTEGER NOT NULL, " +
                            "StepsFail INTEGER NOT NULL, " + 
                            "StepsFatal INTEGER NOT NULL, " + 
                            "StepsError INTEGER NOT NULL, " +
                            "StepsWarning INTEGER NOT NULL, " + 
                            "StepsInfo INTEGER NOT NULL, " + 
                            "StepsSkip INTEGER NOT NULL, " + 
                            "StepsUnknown INTEGER NOT NULL, " + 
                            "ChildNodesCount INTEGER" +
                        ")";
    private final String INSERT_TEST = "INSERT INTO Test " +
                        "( " +
                            "ReportIDExtent, " +
                            "TestIDExtent, " +
                            "TestName, " +
                            "Status, " +
                            "Description, " +
                            "InternalWarning, " +
                            "StartMillis, " +
                            "EndMillis, " +
                            "StepsPass, " +
                            "StepsFail, " +
                            "StepsFatal, " +
                            "StepsError, " +
                            "StepsWarning, " +
                            "StepsInfo, " +
                            "StepsSkip, " +
                            "StepsUnknown, " +
                            "ChildNodesCount " +
                        ") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
    private final String CREATE_NODE_TABLE = "CREATE TABLE IF NOT EXISTS Node " + 
                        "(" +
                            "NodeIDExtent TEXT NOT NULL, " +
                            "NodeName TEXT NOT NULL, " +
                            "NodeLevel INTEGER NOT NULL, " +
                            "ReportIDExtent TEXT NOT NULL, " +
                            "TestIDExtent TEXT NOT NULL, " +
                            "ParentTestName TEXT NOT NULL, " +
                            "Status TEXT NOT NULL, " +
                            "Description TEXT, " +
                            "StartMillis TIME NOT NULL, " +
                            "EndMillis TIME NOT NULL, " +
                            "ChildNodesCount INTEGER" +
                        ")";
    private final String INSERT_NODE = "INSERT INTO Node " +
                            "( " +
                                "NodeIDExtent, " +
                                "NodeName, " +
                                "NodeLevel, " +
                                "ReportIDExtent, " +
                                "TestIDExtent, " +
                                "ParentTestName, " +
                                "Status, " +
                                "Description, " +
                                "StartMillis, " +
                                "EndMillis, " +
                                "ChildNodesCount " +
                            ") " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String CREATE_LOG_TABLE = "CREATE TABLE IF NOT EXISTS Log " + 
                        "(" +
                            "ReportIDExtent TEXT NOT NULL, " +
                            "TestIDExtent TEXT NOT NULL, " +
                            "TestName TEXT NOT NULL, " +
                            "LogID INTEGER NOT NULL, " +
                            "Status TEXT NOT NULL, " +
                            "StepName TEXT, " +
                            "Details TEXT, " +
                            "Timestamp TIME NOT NULL" +
                        ")";
    private final String INSERT_LOG = "INSERT INTO Log " +
                            "(" +
                                "ReportIDExtent, " + 
                                "TestIDExtent, " +
                                "TestName, " +
                                "LogID, " +
                                "Status, " +
                                "StepName, " +
                                "Details, " +
                                "Timestamp " +
                            ") " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                
    private final String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS Category " +
                        "( " +
                            "ReportIDExtent TEXT NOT NULL, " +
                            "TestIDExtent TEXT NOT NULL, " +
                            "TestName TEXT NOT NULL, " +
                            "CategoryName TEXT NOT NULL" +
                        ")";
    private final String INSERT_CATEGORY = "INSERT INTO Category " +
                        "( " +
                            "ReportIDExtent, " +
                            "TestIDExtent, " +
                            "TestName, " +
                            "CategoryName " +
                        ") " +
                    "VALUES (?, ?, ?, ?)";
    
    private final String DEFAULT_DB_NAME = "extent";
    private final String DEFAULT_DB_EXT = "db";
    
    private String filePath;
    
    @Override
    public void start(Report report) {
        this.report = report;
        
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to start database reporter. Extent database will not be created.", e);
            
            e.printStackTrace();
            return;
        }
        
        File dbFile = new File(filePath);
        if (!dbFile.exists() && !dbFile.getParentFile().exists()) {
            dbFile.getParentFile().mkdirs();
        }
        
        int index = dbFile.getName().lastIndexOf(".");
        if (index > 0 && !dbFile.getName().substring(index + 1).equals("db")) {
            filePath = dbFile.getParentFile().getPath() + "/" + DEFAULT_DB_NAME + "." + DEFAULT_DB_EXT;
        }
        else if (index == -1) {
            filePath = dbFile.getPath() + "/" + DEFAULT_DB_NAME + "." + DEFAULT_DB_EXT;
        }
        
        try {
            // jdbc:sqlite:folder/file-name.db
            connection = DriverManager.getConnection("jdbc:sqlite" + ":" + filePath);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        
        systemMap = new HashMap<String, String>();
        
        createReportTable();
        createSystemInfoTable();
        createTestTable();
        createNodeTable();
        createLogTable();

        insertCurrentReportRow();
        
        isReady = true;
    }
    
    @Override
    public void flush() {
        if (connection == null) {
            return;
        }
        
        // insert system info
        Map<String, String> info = report.getSystemInfo().getInfo();
        
        if (info.size() > 0) {
            for (Map.Entry<String, String> entry : info.entrySet()) {
                if (!systemMap.containsKey(entry.getKey())) {
                    try {
                        PreparedStatement stmt = connection.prepareStatement(INSERT_SYSTEM_INFO);
                       
                        int ix = 0;
                        
                        stmt.setString(++ix, report.getId().toString());
                        stmt.setString(++ix, entry.getKey());
                        stmt.setString(++ix, entry.getValue());
                        
                        stmt.executeUpdate();
                        stmt.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    systemMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        
        updateCurrentReportRow();
    }

    @Override
    public void addTest(Test test) {
        if (!isReady) {
            logger.log(Level.SEVERE, "Failed to add test " + test.getName() + " to Extent database");
            return;
        }
        
        this.test = test;

        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_TEST);
            
            int ix = 0;
            
            stmt.setString(++ix, report.getId().toString());
            stmt.setString(++ix, test.getId().toString());
            stmt.setString(++ix, test.getName());
            stmt.setString(++ix, test.getStatus().toString());
            stmt.setString(++ix, test.getDescription());
            stmt.setString(++ix, test.getInternalWarning());
            stmt.setDate(++ix, new java.sql.Date(test.getStartedTime().getTime()));
            stmt.setDate(++ix, new java.sql.Date(test.getEndedTime().getTime()));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.PASS));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.FAIL));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.FATAL));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.ERROR));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.WARNING));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.INFO));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.SKIP));
            stmt.setInt(++ix, test.getLogCounts().get(LogStatus.UNKNOWN));
            stmt.setInt(++ix, test.getNodeList().size());

            stmt.executeUpdate();            
            stmt.close();
            
            addLogs(test);
            addNodes(test, 0);
            
            if (test.getCategoryList().size() > 0) {
                addCategories(test);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
        if (connection == null) {
            return;
        }
        
        try {
            updateCurrentReportRow();
            
            connection.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertCurrentReportRow() {
        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_REPORT);
            
            int ix = 0;
            
            stmt.setString(++ix, report.getId().toString());
            stmt.setDate(++ix, new java.sql.Date(report.getStartedTime().getTime()));
            stmt.setDate(++ix, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(++ix, report.getStatus().toString());
            
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void updateCurrentReportRow() {
        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_REPORT.replace("%%REPORTID%%", report.getId().toString()));
            
            int ix = 0;
            
            stmt.setDate(++ix, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(++ix, report.getStatus().toString());
            
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addNodes(Test test, int level) {
        if (test.hasChildNodes) {
            for (Test node : test.getNodeList()) {
                addNode(node, ++level);
                --level;
                
                if (node.hasChildNodes) {
                    addNodes(node, ++level);
                    --level;
                }
            }
        }
    }
    
    private void addNode(Test node, int level) {
        
        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_NODE);

            int ix = 0;
            
            stmt.setString(++ix, node.getId().toString());
            stmt.setString(++ix, node.getName());
            stmt.setInt(++ix, level);
            stmt.setString(++ix, report.getId().toString());
            stmt.setString(++ix, test.getId().toString());
            stmt.setString(++ix, test.getName());
            stmt.setString(++ix, node.getStatus().toString());
            stmt.setString(++ix, node.getDescription());
            stmt.setDate(++ix, new java.sql.Date(node.getStartedTime().getTime()));
            stmt.setDate(++ix, new java.sql.Date(node.getEndedTime().getTime()));
            stmt.setInt(++ix, node.getNodeList().size());
            
            stmt.executeUpdate();
            stmt.close();
            
            addLogs(node);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addLogs(Test test) {
        try {
            PreparedStatement stmt = getPreparedStatement(INSERT_LOG);
            
            int ix = 0, id = 0;
            
            Iterator<Log> iter = test.logIterator();
            Log log;
            
            while (iter.hasNext()) {
            	log = iter.next();
            	
                stmt.setString(++ix, report.getId().toString());
                stmt.setString(++ix, test.getId().toString());
                stmt.setString(++ix, test.getName());
                stmt.setInt(++ix, id++);
                stmt.setString(++ix, log.getLogStatus().toString());
                stmt.setString(++ix, log.getStepName());
                stmt.setString(++ix, log.getDetails());
                stmt.setDate(++ix, new java.sql.Date(log.getTimestamp().getTime()));
                
                stmt.executeUpdate();
                
                ix = 0;
            }
            
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addCategories(Test test) {
        createCategoryTable();
        
        try {
            PreparedStatement stmt = getPreparedStatement(INSERT_CATEGORY);
            
            for (TestAttribute c : test.getCategoryList()) {
                stmt.setString(1, report.getId().toString());
                stmt.setString(2, test.getId().toString());
                stmt.setString(3, test.getName());
                stmt.setString(4, ((Category) c).getName());
                
                stmt.executeUpdate();
            }
            
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createReportTable() {
        updateDb(CREATE_REPORT_TABLE);
    }
    
    private void createSystemInfoTable() {
        updateDb(CREATE_SYSTEM_INFO_TABLE);
    }
    
    private void createTestTable() {        
        updateDb(CREATE_TEST_TABLE);
    }
    
    private void createNodeTable() {
        updateDb(CREATE_NODE_TABLE);
    }
    
    private void createLogTable() {
        updateDb(CREATE_LOG_TABLE);
    }
    
    private void createCategoryTable() {
        updateDb(CREATE_CATEGORY_TABLE);
    }
    
    private PreparedStatement getPreparedStatement(String query) {
        try {
            return connection.prepareStatement(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    private void updateDb(String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return;
        }        
    }

    @Override
    public void setTestRunnerLogs() { }
    
    public class Config { }
    
    public DBReporter(String filePath) { 
        this.filePath = filePath;
    }
}
