/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import com.relevantcodes.extentreports.model.MediaList;
import com.relevantcodes.extentreports.model.RunInfo;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.support.DateTimeHelper;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.RegexMatcher;
import com.relevantcodes.extentreports.support.Resources;
import com.relevantcodes.extentreports.support.Writer;

class ReportInstance extends LogSettings {
    private DisplayOrder displayOrder;
    private String filePath;
    private volatile int infoWrite = 0;
    private final Object lock = new Object();
    private MediaList mediaList;
    private String quickSummarySrc = "";
    private RunInfo runInfo;
    private volatile String src = null;    
    private volatile String testSrc = "";

    
    public void addTest(Test test) {
        test.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
        
        for (ScreenCapture s : test.screenCapture) {
            mediaList.screenCapture.add(s);
        }
        
        for (Screencast s : test.screencast) {
            mediaList.screencast.add(s);
        }
        
        String src = TestBuilder.getSource(test);
        addTest(src);
        
        src = TestBuilder.getQuickTestSummary(test);
        addQuickTestSummary(src);
    }
    
    private synchronized void addTest(String source) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            testSrc += source;
        }
        else {
            testSrc = source + testSrc;
        }
    }
    
    private synchronized void addQuickTestSummary(String source) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            quickSummarySrc += source;
        }
        else {
            quickSummarySrc = source + quickSummarySrc;
        }
    }
    
    public void initialize(String filePath, Boolean replace, DisplayOrder displayOrder) {
        this.displayOrder = displayOrder;
        this.filePath = filePath;
        
        synchronized (lock) {
            String sourceFile = "com/relevantcodes/extentreports/source/STANDARD.html";
                        
            if (!new File(filePath).isFile()) {
                replace = true;
            }
                    
            if (src != null) {
                return;
            }
            
            if (replace) {
                src = Resources.getText(sourceFile);
            } 
            else {
                src = FileReaderEx.readAllText(filePath);            
            }
            
            runInfo = new RunInfo();
            runInfo.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);

            mediaList = new MediaList();
        }
    }
    
    public void terminate(SystemInfo systemInfo) {
        if (testSrc == "")
            return;
        
        synchronized (lock) {
            runInfo.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
            
            updateSystemInfo(systemInfo.getInfo());
            updateSuiteExecutionTime();
            updateMediaInfo();
            
            systemInfo.clear();
            
            if (displayOrder == DisplayOrder.OLDEST_FIRST) {
                src = src.replace(ExtentFlag.getPlaceHolder("test"), testSrc + ExtentFlag.getPlaceHolder("test"));
            }
            else {
                src = src.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + testSrc);
            }
            
            src = src.replace(ExtentFlag.getPlaceHolder("quickTestSummary"), quickSummarySrc + ExtentFlag.getPlaceHolder("quickTestSummary"));
            
            Writer.getInstance().write(new File(filePath), src);
            
            // clear test and summary sources
            testSrc = "";
            quickSummarySrc = "";
        }
    }
    
    private void updateSuiteExecutionTime() {
        String[] flags = { ExtentFlag.getPlaceHolder("suiteStartTime"), ExtentFlag.getPlaceHolder("suiteEndTime") };
        String[] values = { runInfo.startedAt, runInfo.endedAt };
        
        synchronized (lock) {
            src = SourceBuilder.build(src, flags, values);
        }
    }
    
    private void updateSystemInfo(Map<String, String> info) {
        if (src.indexOf(ExtentFlag.getPlaceHolder("systemInfoApplied")) > 0) {
            return;
        }
        
        if (info.size() > 0) {
            String systemSrc = SourceBuilder.getSource(info) + ExtentFlag.getPlaceHolder("systemInfoApplied");
            
            String[] flags = new String[] { ExtentFlag.getPlaceHolder("systemInfoView") };
            String[] values = new String[] { systemSrc + ExtentFlag.getPlaceHolder("systemInfoView") };
            
            synchronized (lock) {
                src = SourceBuilder.build(src, flags, values);
            }
        }
    }
    
    private void updateMediaInfo() {
        String imageSrc = MediaViewBuilder.getSource(mediaList.screenCapture);
        
        String[] flags = new String[] { ExtentFlag.getPlaceHolder("imagesView") };
        String[] values = new String[] { imageSrc + ExtentFlag.getPlaceHolder("imagesView") };
        
        if (!(infoWrite >= 1 && values[0].indexOf("No media") >= 0)) {
            synchronized (lock) {
                // build sources by replacing the flag with the values
                src = SourceBuilder.build(src, flags, values);
                
                // clear the list so the same images are not written twice
                mediaList.screenCapture.clear();
            }
        }
        
        String scSrc = MediaViewBuilder.getSource(mediaList.screencast);
        
        flags = new String[] { ExtentFlag.getPlaceHolder("videosView") };
        values = new String[] { scSrc + ExtentFlag.getPlaceHolder("videosView") };
        
        if (!(infoWrite >= 1 && values[0].indexOf("No media") >= 0)) {
            synchronized (lock) {
                // build sources by replacing the flag with the values
                src = SourceBuilder.build(src, flags, values);
                
                // clear the list so the same images are not written twice
                mediaList.screencast.clear();
            }
        }
        
        infoWrite++;
    }
    
    public ReportInstance() { }
    
    public class ReportConfig {
        public ReportConfig insertJs(String js) {
            js = "<script type='text/javascript'>" + js + "</script>";
            ReportInstance.this.src = ReportInstance.this.src.replace(ExtentFlag.getPlaceHolder("customscript"), js + ExtentFlag.getPlaceHolder("customscript"));
            
            return this;
        }
        
        public ReportConfig insertCustomStyles(String styles) {
            styles = "<style type='text/css'>" + styles + "</style>";
            ReportInstance.this.src = ReportInstance.this.src.replace(ExtentFlag.getPlaceHolder("customcss"), styles + ExtentFlag.getPlaceHolder("customcss"));
            
            return this;
        }
        
        public void addCustomStylesheet(String cssFilePath) {
            String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
            
            if (cssFilePath.substring(0, 1).equals(new String(".")) || cssFilePath.substring(0, 1).equals(new String("/")))
                link = "<link href='" + cssFilePath + "' rel='stylesheet' type='text/css' />";        
                
            ReportInstance.this.src = ReportInstance.this.src
                                    .replace(ExtentFlag.getPlaceHolder("customcss"), link + ExtentFlag.getPlaceHolder("customcss"));
        }
        
        public ReportConfig reportHeadline(String headline) {
            Integer maxLength = 70;
            
            if (headline.length() > maxLength)
                headline = headline.substring(0, maxLength - 1);
            
            String html = ReportInstance.this.src;
            String pattern = ExtentFlag.getPlaceHolder("headline") + ".*" + ExtentFlag.getPlaceHolder("headline");
            headline = pattern.replace(".*", headline); 
            
            String oldHeadline = RegexMatcher.getNthMatch(html, pattern, 0);
            ReportInstance.this.src = html.replace(oldHeadline, headline);
            
            return this;
        }
        
        public ReportConfig reportName(String name) {
            Integer maxLength = 20;
            
            if (name.length() > maxLength)
                name = name.substring(0, maxLength - 1);
            
            String html = ReportInstance.this.src;
            String pattern = ExtentFlag.getPlaceHolder("logo") + ".*" + ExtentFlag.getPlaceHolder("logo");
            name = pattern.replace(".*", name); 
            
            String oldName = RegexMatcher.getNthMatch(html, pattern, 0);
            ReportInstance.this.src = html.replace(oldName, name);
            
            return this;
        }
        
        public ReportConfig documentTitle(String title) {
            String docTitle = "<title>.*</title>";
            String html = ReportInstance.this.src;
            
            ReportInstance.this.src = html.replace(RegexMatcher.getNthMatch(html, docTitle, 0), docTitle.replace(".*", title));
            
            return this;
        }

        public ReportConfig() { }
    }
}
