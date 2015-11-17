/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.utils.Resources;
import com.relevantcodes.extentreports.utils.Writer;
import com.relevantcodes.extentreports.view.Icon;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**  
 * Concrete HTMLReporter class
 * 
 * @author Anshoo
 *
 */
public class HTMLReporter extends LogSettings implements IReporter {
	private static final Logger logger = Logger.getLogger(HTMLReporter.class.getName());
	
	private Report report;
	
    private Map<String, Object> templateMap;
    
    private String templateName = "Extent.ftl";
    
    // path of the html file
    private String filePath;

    // folder where offline artifacts are stored
    private final String offlineFolderParent = "extentreports";

    @Override
    public void start(Report report) {
    	this.report = report;
        
        // prevent re-initialization
        if (templateMap != null) {
            return;
        }
        
        templateMap = new HashMap<String, Object>();
        templateMap.put("report", this);
        templateMap.put("Icon", new Icon());
        
        File reportFile = new File(filePath);

        if (report.getNetworkMode() == NetworkMode.OFFLINE) {
            templateName = "Extent.Offline.ftl";
            
            initOfflineMode(reportFile);
        }
    }
    
    private void initOfflineMode(File file) {
    	String s = File.separator;
    	
    	String resourcePackagePath = HTMLReporter.class.getPackage().getName().replace(".", s);
    	resourcePackagePath += s + "resources" + s;
    	
        String cssPath = resourcePackagePath + "offline" + s + "css" + s;
        String fontsPath = cssPath + "fonts" + s;
        String jsPath = resourcePackagePath + "offline" + s + "js" + s;
        
        String[] css = { 
                "css.css"
        };
        String[] fonts = {
        		"font-awesome.css.map", 
                "fontawesome-webfont.eot",
                "fontawesome-webfont.svg",
                "fontawesome-webfont.ttf",
                "fontawesome-webfont.woff",
                "fontawesome-webfont.woff2",
                "FontAwesome.otf",
                "Material-Design-Icons.eot",
                "Material-Design-Icons.svg",
                "Material-Design-Icons.ttf",
                "Material-Design-Icons.woff",
                "Material-Design-Icons.woff2"
        };
        String[] js = { 
                "scripts.js"
        };
        
        String[] folderNames = { "css" + s + "fonts", "js" };
        
        // create offline folders from folderName
        for (String name : folderNames) {
            new File(file.getParent() + s + offlineFolderParent + s + name).mkdirs();
        }
        
        String destPath = file.getParent() + s + offlineFolderParent + s;
        
        // copy files to extent/dir
        for (String f : css) {
            Resources.moveResource(cssPath + f, destPath + "css" + s + f);
        }
        for (String f : fonts) {
        	Resources.moveResource(fontsPath + f, destPath + "css" + s + "fonts" + s + f);
        }
        for (String f : js) {
            Writer.getInstance().write(new File(destPath + "js" + s + f), Resources.getText(jsPath + f));
        }
    }
    
    @Override
    public synchronized void flush() {       
        String extentSource = null;
        
        try {
            Template template = getConfig().getTemplate(templateName);
            
            StringWriter out = new StringWriter();
            
            try {
				template.process(templateMap, out);
				extentSource = out.toString();
			} 
            catch (TemplateException e) {
				e.printStackTrace();
			}
            
            out.close();
        }
        catch (IOException e) {
        	logger.log(Level.SEVERE, "Template not found", e);
        }

        Writer.getInstance().write(new File(filePath), extentSource);
    }
    
    private Configuration getConfig() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        cfg.setClassForTemplateLoading(HTMLReporter.class, "view");
        cfg.setDefaultEncoding("UTF-8");

        return cfg;
    }
    
    @Override
    public void stop() {
        
    }
    
    @Override
    public void setTestRunnerLogs() {

    }
    
    // adds tests as HTML source
    @Override
    public synchronized void addTest() {

    }
    
    public Map<String, String> getConfigurationMap() {
    	return report.getConfigurationMap();
    }
    
    public Map<String, List<Test>> getCategoryTestMap() {
    	return report.getCategoryTestMap();
    }
    
    public SystemInfo getSystemInfo() {
        return report.getSystemInfo();
    }
    
    public Map<String, String> getSystemInfoMap() {
    	return report.getSystemInfoMap();
    }
    
    public List<ExtentTest> getTestList() {
        return report.getTestList();
    }
    
    public Date getStartedTime() {
        return report.getStartedTime();
    }
    
    public String getRunDuration() {
    	return report.getRunDuration();
    }
    
    public List<String> getTestRunnerLogList() {
        return report.getTestRunnerLogList();
    }
    
    public HTMLReporter(String filePath) { 
        this.filePath = filePath;
    }
    
    public class Config {
        public Config insertJs(String js) {
            return this;
        }

        public Config insertCustomStyles(String styles) {
            return this;
        }
        
        public Config addCustomStylesheet(String cssFilePath) {
            return this;
        }
        
        public Config reportHeadline(String headline) {
            return this;
        }

        public Config reportName(String name) {
            return this;
        }

        public Config documentTitle(String title) {
            return this;
        }

        public Config() { }
    }
}
