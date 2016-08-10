package com.relevantcodes.extentreports.reporter.configuration;

import java.io.File;

public class BasicFileConfiguration extends BasicConfiguration {

    String filePath;
    String encoding;
    String docTitle;
    String css;
    String js;
    
    boolean autoCreateRelativePathMedia = false;
    
    Theme theme;
    
    public void setFilePath(String filePath) {
        usedConfigs.put("filePath", filePath);
        this.filePath = filePath; 
    }
    public void setFilePath(File file) { setFilePath(file.getPath()); }   
    public String getFilePath() { return filePath; }

    public void setTheme(Theme theme) {
        usedConfigs.put("theme", String.valueOf(theme).toLowerCase());
        this.theme = theme; 
    }
    public Theme getTheme() { return theme; }
    
    public void setEncoding(String encoding) {
        usedConfigs.put("encoding", encoding);
        this.encoding = encoding; 
    }   
    public String getEncoding() { return encoding; }

    public void setDocumentTitle(String docTitle) {
        usedConfigs.put("docTitle", docTitle);
        this.docTitle = docTitle; 
    }
    public String getDocumentTitle() { return docTitle; }
    
    public void setCSS(String css) { 
        usedConfigs.put("css", encoding);
        this.css = css; 
    }    
    public String getCSS() { return css; }
    
    public void setJS(String js) { 
        usedConfigs.put("js", js);
        this.js = js; 
    }
    public String getJS() { return js; }
    
}
