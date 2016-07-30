package com.relevantcodes.extentreports.reporter;

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

import com.relevantcodes.extentreports.ExceptionTestContextImpl;
import com.relevantcodes.extentreports.ExtentReporter;
import com.relevantcodes.extentreports.SessionStatusStats;
import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.SystemAttributeContext;
import com.relevantcodes.extentreports.TestAttributeTestContextProvider;
import com.relevantcodes.extentreports.configuration.Config;
import com.relevantcodes.extentreports.configuration.ConfigLoader;
import com.relevantcodes.extentreports.configuration.ConfigMap;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Test;

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
    
    public void loadConfig(String filePath) { 
        Properties properties = new Properties();
        
        try {
            InputStream is = new FileInputStream(filePath);
            properties.load(is);
            
            loadConfig(properties);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Default Properties file not found", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to load properties file", e);
        }
    }
    
    @Override
    public void setStatusCount(SessionStatusStats sc) { 
        this.sc = sc;
    }
    
    public SessionStatusStats getStatusCount() {
        if (levels == null)
            return sc;
        
        return new SessionStatusStats(testList);
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
