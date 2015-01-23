package com.relevantcodes.cubereports;

public class CubeReports {
	//region Private Variables
	
		private final static CubeReports instance = new CubeReports();
		private static Class<?> clazz;
		private BaseLogger htmlLogger;
		private String filePath;
		
		//region Public Methods
		
		public static CubeReports get(Class<?> clazz) {
			CubeReports.clazz = clazz;
			return instance;
		}
		
		public void startTest(String testName) {
			htmlLogger.startTest(testName);
		}
		
		public void endTest() {
			htmlLogger.endTest("");
		}
		
		public void log(LogStatus logStatus, String stepName, String details, String screenCapturePath) {
			htmlLogger.log(logStatus, "[" + clazz.getName() + "] " + stepName, details, screenCapturePath);
		}
		
		public void log(LogStatus logStatus, String stepName, String details) {
			log(logStatus, stepName, details, "");
		}
		
		public void setLogLevel(LogLevel logLevel) {
			htmlLogger.setLogLevel(logLevel);
		}
		
		public void updateSummary(String summary) {
			htmlLogger.updateSummary(summary);
		}
		
		public void useCustomCSS(String cssFilePath) {
			htmlLogger.customCSS(cssFilePath);
		}
		
		public void init(String filePath, Boolean replaceExisting) {
			this.filePath = filePath;
			
			htmlLogger = new Logger(filePath, replaceExisting);
			updateSystemSpecs();
		}
		
		
		// region Private Methods
		
		private void updateSystemSpecs() {
			LogInsight.updateSystemSpecs(filePath);
		}
		
		
		//region Constructor(s)
		private CubeReports() {}
}