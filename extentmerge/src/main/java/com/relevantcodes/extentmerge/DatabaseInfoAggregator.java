package com.relevantcodes.extentmerge;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.relevantcodes.extentmerge.App.ConsoleArgs;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.utils.DateTimeUtil;
import com.relevantcodes.extentreports.utils.ExtentUtils;

class DatabaseInfoAggregator implements IAggregator {
	private List<String> dbFileList;
	
	private Connection connection;
	
	private final String SQLITE_CLASS = "org.sqlite.JDBC";
    private final String SQLITE_JDBC = "jdbc:sqlite";
        
	public DatabaseInfoAggregator(List<String> dbFileList) {
		this.dbFileList = dbFileList;
	}
	
	public List<Report> getAggregatedData() {
		if (dbFileList == null) {
			return null;
		}
		
		try {
            Class.forName(SQLITE_CLASS);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        
		List<Report> reportList = new ArrayList<Report>();
		
		for (String filePath : dbFileList) {
			initConnection(filePath);
			
			if (connection != null) {
				Logger.info(filePath + " is a valid Extent database file.");
				
				reportList.addAll(getReport());
			}
		}
		
        return reportList;
	}
	
	private List<Report> getReport() {
		ConsoleArgs args = App.getConsoleArgs();

		String partialQuery = ""; 
		
		if (args.getStartMillis() != 0 && args.getEndMillis() != 0) {
			partialQuery = "Where " +
					"Report.StartMillis >= '" + args.getStartMillis() + "' " +
					"AND Report.EndMillis <= '" + args.getEndMillis() + "'";
		}
		else if (args.getStartMillis() != 0) {
			partialQuery = "Where " +
					"Report.StartMillis >= '" + args.getStartMillis()+ "' ";
		}
		else if (args.getEndMillis() != 0) {
			partialQuery = "Where " +
					"Report.EndMillis <= '" + args.getEndMillis() + "' ";
		}
		
		String queryTestInfo = "Select * " +
				"From Test " +
				"Where " +
					"Test.ReportIDExtent In " + 
						"( " + 
							"Select Report.ReportIDExtent " +
							"From Report " + 
							partialQuery +
						")";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(queryTestInfo);
			
			Map<String, Report> reportMap = new HashMap<String, Report>();
			
			Report report;

			while (rs.next()) {
				if (!reportMap.containsKey(rs.getString("ReportIDExtent"))) {
					reportMap.put(rs.getString("ReportIDExtent"), new Report());
				}
				
				report = reportMap.get(rs.getString("ReportIDExtent")); 
				report.setSourceType(SourceType.DB);
				
				String query = "Select " +
									"StartMillis, EndMillis " +
								"From " +
									"Report " +
								"Where " +
									"ReportIDExtent = '" + rs.getString("ReportIDExtent") + "'";
				
				ResultSet reportResultSet = connection.createStatement().executeQuery(query);
				
				report.setStartedTime(DateTimeUtil.getFormattedDateTime(Long.valueOf(reportResultSet.getString("StartMillis")), LogSettings.getLogDateTimeFormat()));
				report.setEndedTime(DateTimeUtil.getFormattedDateTime(Long.valueOf(reportResultSet.getString("EndMillis")), LogSettings.getLogDateTimeFormat()));
				
				report.setTest(getTest(rs));
				
				reportMap.put(rs.getString("ReportIDExtent"), report);
			}
			
			List<Report> reportList = new ArrayList<Report>();
			
			for (Map.Entry<String, Report> entry : reportMap.entrySet()) {
				reportList.add(entry.getValue());
			}
			
			return reportList;
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Test getTest(ResultSet rs) {
		Test extentTest = new Test();
		
		try {
			extentTest.setName(rs.getString("TestName"));
			extentTest.setDescription(rs.getString("Description"));
			
			extentTest.setStatus(
					ExtentUtils.toLogStatus(
							rs.getString("Status")
						)
			);
	
			extentTest.setStartedTime(new Date(Long.valueOf(rs.getString("StartMillis"))));
			extentTest.setEndedTime(new Date(Long.valueOf(rs.getString("EndMillis"))));

			List<String> categoryList = getCategoryList(rs);
			
			if (categoryList != null) {
				for (String category : getCategoryList(rs)) {
					extentTest.setCategory(new Category(category));
				}
			}
			
			extentTest.setLog(getLogList(rs, false));
			extentTest.setNodeList(getNodeList(rs));
			
			if (extentTest.getNodeList().size() > 0) {
				extentTest.hasChildNodes = true;
			}
			else {
				TestTrends.setTest(extentTest.getName(), extentTest.getStatus());
			}
			
			return extentTest;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private List<Test> getNodeList(ResultSet rs) {
		List<Test> extentNodeList;
		
		try {
			String reportID = rs.getString("ReportIDExtent");
			String testID = rs.getString("TestIDExtent");
			
			String query = "Select * " +
							"From Node " +
							"Where " +
								"ReportIDExtent = '" + reportID + "' " +
								"And TestIDExtent = '" + testID + "'";
			
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);			
			
			extentNodeList = new ArrayList<Test>();
			Test extentNode;
			
			while (rs.next()) {
				extentNode = new Test();
				
				extentNode.setName(rs.getString("NodeName"));
				
				extentNode.setStatus(
						ExtentUtils.toLogStatus(
								rs.getString("Status")
							)
				);

				extentNode.setStartedTime(new Date(Long.valueOf(rs.getString("StartMillis"))));
				extentNode.setEndedTime(new Date(Long.valueOf(rs.getString("EndMillis"))));
				extentNode.setLevelClass("node-" + rs.getString("NodeLevel") + "x");
				
				extentNode.setLog(getLogList(rs, true));
				
				TestTrends.setTest(extentNode.getName(), extentNode.getStatus());
				
				extentNodeList.add(extentNode);
			}
			
			return extentNodeList;
		}
		catch (SQLException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private List<Log> getLogList(ResultSet rs, boolean node) {
		List<Log> extentLogList;
		
		try {
			String reportID = rs.getString("ReportIDExtent");
			String testID = node ? rs.getString("NodeIDExtent") : rs.getString("TestIDExtent");
			
			String query = "Select * " +
							"From Log " +
							"Where " +
								"ReportIDExtent = '" + reportID + "' " +
								"And TestIDExtent = '" + testID + "'";
			
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);			
			
			extentLogList = new ArrayList<Log>();
			Log extentLog;
			
			while (rs.next()) {
				extentLog = new Log();
				
				extentLog.setTimestamp(new Date(Long.valueOf(rs.getString("Timestamp"))));
				
				String stepName = rs.getString("StepName");
				
				if (stepName != null && !stepName.equals("")) {
					extentLog.setStepName(stepName);
				}
				
				extentLog.setLogStatus(ExtentUtils.toLogStatus(rs.getString("Status")));
				extentLog.setDetails(rs.getString("Details"));
				
				extentLogList.add(extentLog);
			}
			
			return extentLogList;
		}
		catch (SQLException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private List<String> getCategoryList(ResultSet rs) {
		List<String> categoryList;
		
		try {
			String reportID = rs.getString("ReportIDExtent");
			String testID = rs.getString("TestIDExtent");
			
			String query = "Select * " +
							"From Category " +
							"Where " +
								"ReportIDExtent = '" + reportID + "' " +
								"And TestIDExtent = '" + testID + "'";
			
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			
			categoryList = new ArrayList<String>();
			
			while (rs.next()) {
				categoryList.add(rs.getString("CategoryName"));
			}
			
			return categoryList;
		}
		catch (SQLException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private void initConnection(String filePath) {
		File dbFile = new File(filePath).getParentFile();
		
		int index = filePath.lastIndexOf(".");
		
		if (!(dbFile.exists() && index > 0 && filePath.substring(index + 1).toLowerCase().contains("db"))) {
			connection = null;
			
			return;
		}
        
        try {
            connection = DriverManager.getConnection(SQLITE_JDBC + ":" + filePath);
            
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "Report",  new String[] {"TABLE"});

            if (!(rs.getString("TABLE_NAME").equalsIgnoreCase("Report") && rs.getString("TABLE_TYPE").equalsIgnoreCase("Table"))) {
            	connection = null;
            }
        }
        catch (SQLException e) {
        	connection = null;
            System.out.println(filePath + " is not a valid Extent database file.");
        }
	}
}