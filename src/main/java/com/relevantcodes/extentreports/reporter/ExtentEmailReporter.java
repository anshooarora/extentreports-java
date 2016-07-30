package com.relevantcodes.extentreports.reporter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.InvalidFileException;
import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.configuration.Config;
import com.relevantcodes.extentreports.configuration.ConfigMap;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.reporter.configuration.EmailReporterConfiguration;
import com.relevantcodes.extentreports.utils.Writer;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class ExtentEmailReporter extends BasicFileReporter {
    
    private static final Logger logger = Logger.getLogger(ExtentHtmlReporter.class.getName());
    
    private static final String TEMPLATE_NAME = "email.ftl";
    private static final String DEFAULT_CONFIG_FILE = "email-config.properties";
    
    private EmailReporterConfiguration userConfig;
    
    ExtentEmailReporter() {
        configContext = new ConfigMap();
        userConfig = new EmailReporterConfiguration();
        
        loadConfig(getClass().getClassLoader().getResource(DEFAULT_CONFIG_FILE).getPath());
    }
    
    public ExtentEmailReporter(File file) {
        this();
        
        this.filePath = file.getAbsolutePath();
        userConfig.setFilePath(filePath);
    }
    
    public ExtentEmailReporter(String filePath) {
        this();
        
        this.filePath = filePath;
        userConfig.setFilePath(filePath);
    }
    
    public EmailReporterConfiguration config() {
        return userConfig;
    }
    
    @Override
    public void start() {        
        // prevent re-initialization
        if (templateMap != null) {
            return;
        }
        
        templateMap = new HashMap<>();
        templateMap.put("report", this);
        
        BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_23);
        BeansWrapper beansWrapper = builder.build();
        
        try {
            TemplateHashModel fieldTypeModel = (TemplateHashModel)beansWrapper.getEnumModels().get(Status.class.getName());
            templateMap.put("LogStatus", fieldTypeModel);
        } 
        catch (TemplateModelException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }
    
    @Override
    public void stop() { }

    @Override
    public synchronized void flush() {
        try {
            loadDefaultConfig();
        } catch (InvalidFileException e) {
            logger.log(Level.SEVERE, "", e);
            return;
        }
        
        String extentSource = null;
        
        try {
            Template template = getConfig().getTemplate(TEMPLATE_NAME);
            
            StringWriter out = new StringWriter();
            
            try {
                template.process(templateMap, out);
                extentSource = out.toString();
            } 
            catch (TemplateException e) {
                logger.log(Level.SEVERE, "", e);
            }
            
            out.close();
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Template not found", e);
        }

        Writer.getInstance().write(new File(filePath), extentSource);        
    }
    
    protected void loadDefaultConfig() throws InvalidFileException {
        filePath = userConfig.getConfigMap().get("filePath");
        
        if (filePath.equals(null))
            throw new InvalidFileException("No file specified");
        
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
   
    public ConfigMap getConfigContext() { return null; }

    @Override
    public void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) { }
    
    @Override
    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }
    
    public List<Test> getTestList() { return testList; }

}
