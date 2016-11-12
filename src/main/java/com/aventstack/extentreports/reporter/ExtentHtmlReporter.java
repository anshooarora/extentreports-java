package com.aventstack.extentreports.reporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.InvalidFileException;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.configuration.Config;
import com.aventstack.extentreports.configuration.ConfigMap;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.configuration.ExtentHtmlReporterConfiguration;
import com.aventstack.extentreports.reporter.converters.ExtentHtmlReporterConverter;
import com.aventstack.extentreports.utils.Writer;
import com.aventstack.extentreports.viewdefs.Icon;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * The ExtentHtmlReporter creates a rich standalone HTML file. It allows several configuration options
 * via the <code>config()</code> method.
 */
public class ExtentHtmlReporter extends BasicFileReporter implements ReportAppendable {
    
    private static final Logger logger = Logger.getLogger(ExtentHtmlReporter.class.getName());

    private static final String TEMPLATE_LOCATION = "view/html-report";
    private static final String TEMPLATE_NAME = "index.ftl";
    private static final String DEFAULT_CONFIG_FILE = "html-config.properties";

    private static String ENCODING = "UTF-8";
    
    private Boolean appendExisting = false;
    
    private List<Test> parsedTestCollection;
    private ExtentHtmlReporterConfiguration userConfig;
    
    ExtentHtmlReporter() {
        loadDefaultConfig();
    }
    
    public ExtentHtmlReporter(String filePath) {
        this();
        this.filePath = filePath;
        config().setFilePath(filePath);
    }
    
    public ExtentHtmlReporter(File file) {
    	this(file.getAbsolutePath());
    }
    
    private void loadDefaultConfig() {
        configContext = new ConfigMap();
        userConfig = new ExtentHtmlReporterConfiguration();
        
        ClassLoader loader = getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream(DEFAULT_CONFIG_FILE);
        loadConfig(is);
    }
    
    public ExtentHtmlReporterConfiguration config() {
        return userConfig;
    }
    
    @Override
    public void start() {
        if (templateMap != null) {
            return;
        }
        
        templateMap = new HashMap<String, Object>();
        templateMap.put("report", this);
        templateMap.put("Icon", new Icon());
        
        BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_23);
        BeansWrapper beansWrapper = builder.build();
        
        try {
            TemplateHashModel fieldTypeModel = (TemplateHashModel)beansWrapper.getEnumModels().get(Status.class.getName());
            templateMap.put("Status", fieldTypeModel);
        } 
        catch (TemplateModelException e) {
            logger.log(Level.SEVERE, "", e);
        }
        
        if (appendExisting && filePath != null)
        	parseReportBuildTestCollection();
    }

    private void parseReportBuildTestCollection() {
    	File f = new File(filePath);
    	if (!f.exists())
    		return;
    	
    	ExtentHtmlReporterConverter converter = new ExtentHtmlReporterConverter(filePath);
    	parsedTestCollection = converter.parseAndGetModelCollection();
    }
    
    @Override
    public synchronized void flush() {
        if (testList == null || testList.size() == 0)
            return;
        
        try {
            loadUserConfig();
        } catch (InvalidFileException e) {
            logger.log(Level.SEVERE, "", e);
            return;
        }
        
        if (parsedTestCollection != null && parsedTestCollection.size() > 0)
        	for (int ix = 0; ix < parsedTestCollection.size(); ix++)
        		testList.add(ix, parsedTestCollection.get(ix));
        
        parsedTestCollection = null;
        
        setEndTime(Calendar.getInstance().getTime());
        
        String extentSource = null;
        
        try {
            Template template = getConfig().getTemplate(TEMPLATE_NAME);
            
            StringWriter out = new StringWriter();
            
            template.process(templateMap, out);
            extentSource = out.toString();
            
            out.close();
        }
        catch (IOException | TemplateException e) {
            logger.log(Level.SEVERE, "Template not found", e);
        }

        Writer.getInstance().write(new File(filePath), extentSource);        
    }
    
    private void loadUserConfig() throws InvalidFileException {
        String filePath = userConfig.getConfigMap().get("filePath");
        
        if (filePath == null && this.filePath == null)
            throw new InvalidFileException("No file specified.");
        
        userConfig.setFilePath(this.filePath);
        
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
    
    private Configuration getConfig() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        cfg.setClassForTemplateLoading(ExtentReports.class, TEMPLATE_LOCATION);
        cfg.setDefaultEncoding(ENCODING);
        
        return cfg;
    }
    
    @Override
    public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) throws IOException { }

    @Override
    public void setTestList(List<Test> reportTestList) {
        testList = reportTestList;
    }
    
    public List<Test> getTestList() {
        if (testList == null)
            testList = new ArrayList<>();
        
        return testList;
    }
    
    public boolean containsStatus(Status status) {
        boolean b = statusCollection == null || statusCollection.isEmpty() ? false : statusCollection.contains(status);
        return b;
    }
    
    public ConfigMap getConfigContext() { 
        return configContext; 
    }

	@Override
	public void setAppendExisting(Boolean b) {
		this.appendExisting = b;
	}
    
}
