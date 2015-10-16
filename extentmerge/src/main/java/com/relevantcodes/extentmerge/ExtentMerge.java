package com.relevantcodes.extentmerge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.relevantcodes.extentmerge.model.Customizer;
import com.relevantcodes.extentmerge.model.MergedDataMaster;
import com.relevantcodes.extentmerge.model.Report;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class ExtentMerge {
	private List<Report> reportList;
	private Customizer customizer;
	
	public ExtentMerge(List<Report> reportList) {
		this.reportList = reportList;
	}
	
	public void customize(Customizer customizer) {
		this.customizer = customizer;
	}
	
	public void createReport(String filePath) {
		MergedDataMaster mergedData = new MergedDataMaster(reportList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mergedData", mergedData);
		map.put("reportList", reportList);
		map.put("logs", Logger.getLogs());
		map.put("topPassed", TestTrends.getTopPassed());
		map.put("topFailed", TestTrends.getTopFailed());
		map.put("customizer", customizer);
		
		try {
			Template template = getConfig().getTemplate("Template.ftl");
			
			Writer out = new FileWriter(new File(filePath));
			template.process(map, out);
			out.close();
		} 
		catch (TemplateNotFoundException e) {
			Logger.fatal("Unable to find template.");
			e.printStackTrace();
		} 
		catch (MalformedTemplateNameException e) {
			Logger.fatal(e.getMessage());
			e.printStackTrace();
		} 
		catch (ParseException e) {
			Logger.fatal("Unable to parse template. Please make sure there are no errors.");
			e.printStackTrace();
		} 
		catch (IOException e) {
			Logger.fatal("Unable to locate template.");
			e.printStackTrace();
		}
		catch (TemplateException e) {
			Logger.fatal(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Configuration getConfig() {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

		cfg.setClassForTemplateLoading(ExtentMerge.class, "view");
		cfg.setDefaultEncoding("UTF-8");

		return cfg;
	}
}
