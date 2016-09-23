package com.relevantcodes.extentreports.reporter;

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

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.InvalidFileException;
import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.configuration.Config;
import com.relevantcodes.extentreports.configuration.ConfigMap;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.reporter.configuration.HtmlReporterConfiguration;
import com.relevantcodes.extentreports.utils.Writer;
import com.relevantcodes.extentreports.viewdefs.Icon;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class ExtentHtmlReporter extends BasicFileReporter {
    
    private static final Logger logger = Logger.getLogger(ExtentHtmlReporter.class.getName());

    private static final String TEMPLATE_NAME = "extent.ftl";
    private static final String DEFAULT_CONFIG_FILE = "html-config.properties";
    
    private HtmlReporterConfiguration userConfig;
    
    ExtentHtmlReporter() {
        loadDefaultConfig();
    }
    
    private void loadDefaultConfig() {
        configContext = new ConfigMap();
        userConfig = new HtmlReporterConfiguration();
        
        ClassLoader loader = getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream(DEFAULT_CONFIG_FILE);
        loadConfig(is);
    }
    
    public ExtentHtmlReporter(File file) {
        this();
        
        this.filePath = file.getAbsolutePath();
    }
    
    public ExtentHtmlReporter(String filePath) {
        this();
        
        this.filePath = filePath;
    }
    
    public HtmlReporterConfiguration config() {
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
            templateMap.put("LogStatus", fieldTypeModel);
            templateMap.put("Status", fieldTypeModel);
        } 
        catch (TemplateModelException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    @Override
    public void stop() { }

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
            throw new InvalidFileException("No file specified");
        
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

        cfg.setClassForTemplateLoading(ExtentReports.class, "view");
        cfg.setDefaultEncoding("UTF-8");
        
        return cfg;
    }

    @Override
    public void onTestStarted(Test test) { }

    @Override
    public void setTestList(List<Test> reportTestList) {
        testList = reportTestList;
    }
    
    public List<Test> getTestList() {
        if (testList == null)
            testList = new ArrayList<>();
        
        return testList;
    }
    
    public ConfigMap getConfigContext() { 
        return configContext; 
    }
    
}
