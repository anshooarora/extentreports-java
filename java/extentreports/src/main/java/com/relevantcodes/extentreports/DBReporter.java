package com.relevantcodes.extentreports;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;

class DBReporter extends LogSettings implements IReporter {
	private boolean isReady = false;
	
	private Report report;
    
	private Connection connection;
    
    private Test test;
    private Map<String, String> systemMap;
    
    private final String queryCreateReportTable = "CREATE TABLE IF NOT EXISTS Report " + 
                        "(" +
                            "ReportIDExtent VARCHAR(36) NOT NULL, " +
                            "StartMillis TIME NOT NULL, " +
                            "EndMillis TIME NOT NULL, " +
                            "ReportStatus TEXT NOT NULL, " +
                            "PRIMARY KEY (ReportIDExtent)" +
                        ")";
    private final String queryInsertReportRow = "INSERT INTO Report " +
                        "( " +
                            "ReportIDExtent, " +
                            "StartMillis, " +
                            "EndMillis, " +
                            "ReportStatus " +
                        ")" +
                        "VALUES (?, ?, ?, ?)";
    private final String queryUpdateReportRow = "UPDATE Report " +
                        "SET " +
                            "EndMillis = ?, " +
                            "ReportStatus = ? " +
                        "WHERE " +
                            "ReportIDExtent = '%%REPORTID%%'";
    
    private final String queryCreateSystemInfoTable = "CREATE TABLE IF NOT EXISTS SystemInfo " + 
                        "(" +
                            "ReportIDExtent VARCHAR(36) NOT NULL, " +
                            "Param TEXT NOT NULL, " +
                            "Value TEXT NOT NULL" +
                        ")";
    private final String queryInsertSystemInfoRow = "INSERT INTO SystemInfo " +
                        "( " +
                            "ReportIDExtent, " +
                            "Param, " +
                            "Value" +
                        ")" +
                        "VALUES (?, ?, ?)";

    private final String queryCreateTestTable = "CREATE TABLE IF NOT EXISTS Test " + 
	                    "(" +
	                        "ReportIDExtent VARCHAR(36) NOT NULL, " +
	                        "TestIDExtent VARCHAR(36) NOT NULL, " +
	                        "TestName TEXT NOT NULL, " +
	                        "Status VARCHAR(7) NOT NULL, " +
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
	                        "ChildNodesCount INTEGER, " +
	                        "PRIMARY KEY (TestIDExtent)" +
	                    ")";
    private final String queryInsertTest = "INSERT INTO Test " +
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
                
    private final String queryCreateNodeTable = "CREATE TABLE IF NOT EXISTS Node " + 
	                    "(" +
	                        "NodeIDExtent VARCHAR(36) NOT NULL, " +
	                        "NodeName TEXT NOT NULL, " +
	                        "NodeLevel INTEGER NOT NULL, " +
	                        "ParentTestIDExtent VARCHAR(36) NOT NULL, " +
	                        "ParentTestName TEXT NOT NULL, " +
	                        "Status VARCHAR(7) NOT NULL, " +
	                        "Description TEXT, " +
	                        "StartMillis TIME NOT NULL, " +
	                        "EndMillis TIME NOT NULL, " +
	                        "ChildNodesCount INTEGER" +
	                    ")";
    private final String queryInsertNode = "INSERT INTO Node " +
		                    "( " +
		                        "NodeIDExtent, " +
		                        "NodeName, " +
		                        "NodeLevel, " +
		                        "ParentTestIDExtent, " +
		                        "ParentTestName, " +
		                        "Status, " +
		                        "Description, " +
		                        "StartMillis, " +
		                        "EndMillis, " +
		                        "ChildNodesCount " +
		                    ") " +
		                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String queryCreateLogTable = "CREATE TABLE IF NOT EXISTS Log " + 
	                    "(" +
	                        "TestIDExtent VARCHAR(36) NOT NULL, " +
	                        "TestName TEXT NOT NULL, " +
	                        "LogID INTEGER NOT NULL, " +
	                        "Status VARCHAR(7) NOT NULL, " +
	                        "StepName TEXT, " +
	                        "Details TEXT, " +
	                        "Timestamp TIME NOT NULL" +
	                    ")";
    private final String queryInsertLogs = "INSERT INTO Log " +
		                    "(" +
		                        "TestIDExtent, " +
		                        "TestName, " +
		                        "LogID, " +
		                        "Status, " +
		                        "StepName, " +
		                        "Details, " +
		                        "Timestamp " +
		                    ") " +
		                "VALUES (?, ?, ?, ?, ?, ?, ?)";
                
    private final String queryCreateCategoryTable = "CREATE TABLE IF NOT EXISTS Category " +
		                "( " +
		                    "ReportIDExtent VARCHAR(36) NOT NULL, " +
		                    "TestIDExtent VARCHAR(36) NOT NULL, " +
		                    "TestName TEXT NOT NULL, " +
		                    "CategoryName TEXT NOT NULL" +
		                ")";
    private final String queryInsertCategory = "INSERT INTO Category " +
                        "( " +
                            "ReportIDExtent, " +
                            "TestIDExtent, " +
                            "TestName, " +
                            "CategoryName " +
                        ") " +
                    "VALUES (?, ?, ?, ?)";
    
    private final String sqliteClass = "org.sqlite.JDBC";
    private final String dbClass = "jdbc:sqlite";
    private final String dbName = "extent.db";
    
    @Override
    public void start(Report report) {
        this.report = report;
        
        try {
            Class.forName(sqliteClass);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
        File dbFile = new File(report.getFilePath()).getParentFile();
        
        try {
            connection = DriverManager.getConnection(dbClass + ":" + dbFile.getPath() + File.separator + dbName);
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
        // insert system info
        Map<String, String> info = report.getSystemInfo().getInfo();
        
        if (info.size() > 0) {
            for (Map.Entry<String, String> entry : info.entrySet()) {
                if (!systemMap.containsKey(entry.getKey())) {
                    try {
                        PreparedStatement stmt = connection.prepareStatement(queryInsertSystemInfoRow);
                       
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
    public void addTest() {
        if (!isReady) {
            System.out.println("Failed to add test " + test.getName() + " to Extent database");
            return;
        }
        
        this.test = report.getTest();

        try {
            PreparedStatement stmt = connection.prepareStatement(queryInsertTest);
            
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
            PreparedStatement stmt = connection.prepareStatement(queryInsertReportRow);
            
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
            PreparedStatement stmt = connection.prepareStatement(queryUpdateReportRow.replace("%%REPORTID%%", report.getId().toString()));
            
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
            PreparedStatement stmt = connection.prepareStatement(queryInsertNode);

            int ix = 0;
            
            stmt.setString(++ix, node.getId().toString());
            stmt.setString(++ix, node.getName());
            stmt.setInt(++ix, level);
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
            PreparedStatement stmt = getPreparedStatement(queryInsertLogs);
            
            int ix = 0, id = 0;
            
            for (Log log : test.getLog()) {
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
            PreparedStatement stmt = getPreparedStatement(queryInsertCategory);
            
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
        updateDb(queryCreateReportTable);
    }
    
    private void createSystemInfoTable() {
        updateDb(queryCreateSystemInfoTable);
    }
    
    private void createTestTable() {        
        updateDb(queryCreateTestTable);
    }
    
    private void createNodeTable() {
        updateDb(queryCreateNodeTable);
    }
    
    private void createLogTable() {
        updateDb(queryCreateLogTable);
    }
    
    private void createCategoryTable() {
        updateDb(queryCreateCategoryTable);
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
    
    public DBReporter() {
        
    }
}
