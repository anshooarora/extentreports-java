package com.aventstack.extentreports.reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExceptionTestContextImpl;
import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.SessionStatusStats;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.SystemAttributeContext;
import com.aventstack.extentreports.TestAttributeTestContextProvider;
import com.aventstack.extentreports.configuration.Config;
import com.aventstack.extentreports.configuration.ConfigLoader;
import com.aventstack.extentreports.configuration.ConfigMap;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Test;

public abstract class AbstractReporter implements ExtentReporter {

    private static final Logger logger = Logger.getLogger(AbstractReporter.class.getName());
    
    protected Date startTime;
    protected Date endTime;
    
    protected List<Status> levels;
    protected List<Test> testList;
    protected List<String> testRunnerLogs;
    
    protected Map<String, Object> configMap;
    protected Map<String, Object> templateMap;
    
    protected String filePath;

    protected ConfigMap configContext;
    protected ExceptionTestContextImpl exceptionContext;
    protected TestAttributeTestContextProvider<Category> categoryContext;
    protected SystemAttributeContext systemAttributeContext;
    protected SessionStatusStats sc;
    
    public AbstractReporter() {
        setStartTime(Calendar.getInstance().getTime());
    }
    
    public void loadXMLConfig(String filePath) { 
        loadXMLConfig(new File(filePath));
    }
    
    public void loadXMLConfig(File file) { 
        ConfigMap config = new ConfigLoader(file).getConfigurationHash();
        config.getConfigList().forEach(configContext::setConfig);
    }
    
    public void loadConfig(Properties properties) { 
        properties.entrySet().forEach(o -> {
            Config c = new Config();
            c.setKey(o.getKey().toString());
            c.setValue(o.getValue());
            
            configContext.setConfig(c);
        });
    }
    
    public void loadConfig(InputStream stream) {
        Properties properties = new Properties();
        
        try {
            properties.load(stream);
            loadConfig(properties);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Default Properties file not found", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to load properties file", e);
        }
    }
    
    public void loadConfig(String filePath) { 
        try {
            InputStream is = new FileInputStream(filePath);
            loadConfig(is);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Default Properties file not found", e);
        }
    }
    
    @Override
    public void setStatusCount(SessionStatusStats sc) { 
        this.sc = sc;
    }
    
    public SessionStatusStats getStatusCount() {
        if (sc != null) {
            sc.refresh(testList);
            return sc;
        }

        sc = new SessionStatusStats();
        sc.refresh(testList);
        return sc;
    }
    
    @Override
    public void setSystemAttributeContext(SystemAttributeContext systemAttributeContext) {
        this.systemAttributeContext = systemAttributeContext;
    }
    
    public SystemAttributeContext getSystemAttributeContext() { 
        return systemAttributeContext; 
    }
    
    @Override
    public void setCategoryContextInfo(TestAttributeTestContextProvider<Category> categoryContext) {
        this.categoryContext = categoryContext;
    }
    
    public TestAttributeTestContextProvider<Category> getCategoryContextInfo() { 
        return categoryContext; 
    }

    @Override
    public void setTestRunnerLogs(List<String> testRunnerLogs) {
        this.testRunnerLogs = testRunnerLogs;
    }
    
    public List<String> getTestRunnerLogs() { return testRunnerLogs; }    

    @Override
    public void setExceptionContextInfo(ExceptionTestContextImpl exceptionContext) {
        this.exceptionContext = exceptionContext;
    }
    
    public ExceptionTestContextImpl getExceptionContextInfo() { 
        return exceptionContext; 
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Date getEndTime() { 
        return endTime; 
    }

    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getStartTime() { 
        return startTime; 
    }
    
    public long getRunDuration() { 
        return getEndTime().getTime() - getStartTime().getTime(); 
    }

}
