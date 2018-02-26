package com.aventstack.extentreports.reporter.configuration;

import java.io.File;

/**
 * Common configuration for file reporters:
 * 
 * <ul>
 * 	<li>{@link com.aventstack.extentreports.reporter.ExtentHtmlReporter}</li>
 * </ul>
 */
public class BasicFileConfiguration extends BasicConfiguration {

    private String filePath;
    private String encoding;
    private String docTitle;
    private String css;
    private String js;
    private String url;
    
    private Theme theme;
    
    /**
     * Sets file-path of the report file
     * 
     * @param filePath Path where the results file will be saved
     */
    public void setFilePath(String filePath) {
        usedConfigs.put("filePath", filePath);
        this.filePath = filePath; 
    }
    public void setFilePath(File file) { setFilePath(file.getPath()); }   
    public String getFilePath() { return filePath; }

    /**
     * Sets the {@link Theme} of the report
     * 
     * @param theme {@link Theme}
     */
    public void setTheme(Theme theme) {
        usedConfigs.put("theme", String.valueOf(theme).toLowerCase());
        this.theme = theme; 
    }
    public Theme getTheme() { return theme; }
    
    /**
     * Sets file encoding, eg: UTF-8
     * 
     * @param encoding Encoding
     */
    public void setEncoding(String encoding) {
        usedConfigs.put("encoding", encoding);
        this.encoding = encoding; 
    }   
    public String getEncoding() { return encoding; }

    /**
     * Sets the document title denoted by the <code>title</code> tag
     * 
     * @param docTitle Title
     */
    public void setDocumentTitle(String docTitle) {
        usedConfigs.put("documentTitle", docTitle);
        this.docTitle = docTitle; 
    }
    public String getDocumentTitle() { return docTitle; }
    
    /**
     * Sets CSS to be used to customize the look and feel of your report
     * 
     * @param css CSS
     */
    public void setCSS(String css) { 
        usedConfigs.put("css", css);
        this.css = css; 
    }    
    public String getCSS() { return css; }
    
    /**
     * Adds custom JavaScript
     * 
     * @param js JavaScript
     */
    public void setJS(String js) { 
        usedConfigs.put("js", js);
        this.js = js; 
    }
    public String getJS() { return js; }
    
    /**
     * Informs the file reporters of the ExtentX url, so the server can be accessed directly from
     * the report itself 
     * 
     * @param url ExtentX url
     */
    public void setExtentXUrl(String url) {
        usedConfigs.put("extentx-url", url);
        this.url = url;
    }
    public String getExtentXUrl() { return url; }
}
