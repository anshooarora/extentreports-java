/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.relevantcodes.extentmerge.model.Customizer;
import com.relevantcodes.extentmerge.model.Report;

public class App {
	private static ConsoleArgs consoleArgs;
	
    public static void main(String[] args) throws ParseException {
    	Logger.log(getCopyright());
    	
    	consoleArgs = new App().new ConsoleArgs();
    	
    	try {
    		new JCommander(consoleArgs, args);
    	}
    	catch (Exception e) {
    		boolean showUsage = false;
    		
    		if (consoleArgs.outFile == null || consoleArgs.outFile.equals("")) {
    			showUsage = true;
    			Logger.error("Output file is a required field.\n");
    		}
    		
    		if (showUsage) {
    			new JCommander(consoleArgs).usage();
    			return;
    		}
    		
    		e.printStackTrace();
    	}
    	
    	if (consoleArgs.db == null && consoleArgs.html == null && consoleArgs.dir == null) {
			Logger.error("You must provide one of the following input sources: -dir -html -db\n");
			return;
		}
    	
    	if (consoleArgs.getStartMillis() == 0 || consoleArgs.getEndMillis() == 0) {
    		return;
    	}
 
    	DataAggregator aggregator = new DataAggregator(consoleArgs.dir, consoleArgs.html, consoleArgs.db);
    	List<Report> reportList = aggregator.getAggregatedData();
    	
    	if (reportList != null && reportList.size() > 0) {
    		ExtentMerge extent = new ExtentMerge(reportList);
    		
    		Customizer customizer = new Customizer();
    		customizer.setInlineCss(consoleArgs.css);
    		customizer.setInlineScript(consoleArgs.js);
    		customizer.setScriptFile(consoleArgs.jsFile);
    		customizer.setStylesheet(consoleArgs.cssFile);
    		
    		extent.customize(customizer);
    		extent.createReport();
    	}
    }
    
    public static ConsoleArgs getConsoleArgs() {
    	return consoleArgs;
    }
    
    private static String getCopyright() {
    	String copy = "-----------------------------------------------------\n";
    	copy += "ExtentMerge 0.1-alpha. Extent Trends.\n";
    	copy+= "http://extentreports.relevantcodes.com/\n";
    	copy+= "The MIT License (MIT)\n";
    	copy+= "Copyright (c) 2015 Anshoo Arora (Relevant Codes)\n";
    	copy+= "-----------------------------------------------------\n";
    	
    	return copy;
    }
    
    public class ConsoleArgs {
    	public long getStartMillis() {
    		if (from != null) {
    			SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateTimeFormat());
    			
    			try {
					return sdf.parse(from).getTime();
				} catch (ParseException e) {
					Logger.error("-from must be in the following format: " + LogSettings.getLogDateTimeFormat());					
				}
    			
    			return 0;
    		}
    		
    		return -1;
    	}
    	
    	public long getEndMillis() {
    		if (to != null) {
    			SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateTimeFormat());
    			
    			try {
					return sdf.parse(to).getTime();
				} catch (ParseException e) {
					Logger.error("-to must be in the following format: " + LogSettings.getLogDateTimeFormat());
					return 0;
				}
    		}
    		
    		return Calendar.getInstance().getTimeInMillis();
    	}
    	
    	@Parameter(names = "-html", description = "Extent generated HTML file")
        private List<String> html;
    	
    	@Parameter(names = "-dir", description = "Directory where Extent HTML files are placed")
        private List<String> dir;
    	
    	@Parameter(names = "-db", description = "Extent generated Sqlite database")
        private List<String> db;

    	@Parameter(names = "-from", description = "Date to begin collecting data (Format: 'yyyy-MM-dd hh:mm:ss')")
        private String from;
    	
    	@Parameter(names = "-to", description = "Date to end collecting data (Format: 'yyyy-MM-dd hh:mm:ss')")
        private String to;
    	
    	@Parameter(names = "-out", description = "Output file path with .html extension", required = true)
        private String outFile;
    	
    	@Parameter(names = "-css", description = "Inline css", required = false)
    	private String css;
    	
    	@Parameter(names = "-css-file", description = "Path to custom CSS file", required = false)
    	private String cssFile;
    	
    	@Parameter(names = "-js", description = "Inline js", required = false)
    	private String js;
    	
    	@Parameter(names = "-js-file", description = "Path to custom JavaScript file", required = false)
    	private String jsFile;
    	
    	@Parameter(names = { "-h", "--help" }, description = "Help", help = true)
        private String help;
    	
    	@Parameter(names = { "-v", "--version" }, description = "Version")
        private String version = "0.1";
    }
}
