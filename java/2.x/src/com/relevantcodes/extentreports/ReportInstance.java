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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.relevantcodes.extentreports.model.CategoryList;
import com.relevantcodes.extentreports.model.MediaList;
import com.relevantcodes.extentreports.model.RunInfo;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.CategoryHtml;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.support.DateTimeHelper;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.RegexMatcher;
import com.relevantcodes.extentreports.support.Resources;
import com.relevantcodes.extentreports.support.Writer;

class ReportInstance {
    private Boolean terminated = false;
    private CategoryList categoryList;
    private DisplayOrder displayOrder;
    private String filePath;
    private volatile int infoWrite = 0;
    private final Object lock = new Object();
    private MediaList mediaList;
    private String quickSummarySource = "";
    private RunInfo runInfo;
    private volatile String extentSource = null;    
    private volatile String testSource = "";
    
    public void addTest(Test test) {
    	if (test.endedTime == null) {
    		test.endedTime = Calendar.getInstance().getTime();
    	}
        
        for (ScreenCapture s : test.screenCapture) {
            mediaList.screenCapture.add(s);
        }
        
        for (Screencast s : test.screencast) {
            mediaList.screencast.add(s);
        }
        
        test.prepareFinalize();
        
        addTest(TestBuilder.getSource(test));
        addQuickTestSummary(TestBuilder.getQuickTestSummary(test));
        addCategories(test);
        updateCategoryView(test);
    }
    
    private synchronized void addTest(String source) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            testSource += source;
        }
        else {
            testSource = source + testSource;
        }
    }
    
    private synchronized void addQuickTestSummary(String source) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            quickSummarySource += source;
        }
        else {
            quickSummarySource = source + quickSummarySource;
        }
    }
    
    private void addCategories(Test test) {
        for (TestAttribute attr : test.categoryList) {
            if (!categoryList.categories.contains(attr.getName())) {
                categoryList.categories.add(attr.getName());
            }
        }
    }
    
 // #24: Create summary section for each category 
    private synchronized void updateCategoryView(Test test) {
    	if (test.isChildNode) {
    		return;
    	}
    	
        String s = "", testSource = "";
        String addedFlag = "";
        String[] sourceKeys = { ExtentFlag.getPlaceHolder("categoryViewName"), ExtentFlag.getPlaceHolder("categoryViewNameL"), ExtentFlag.getPlaceHolder("categoryViewTestDetails") };
        String[] testKeys = { ExtentFlag.getPlaceHolder("categoryViewTestRunTime"), ExtentFlag.getPlaceHolder("categoryViewTestName"), ExtentFlag.getPlaceHolder("categoryViewTestStatus") };
        String[] testValues = { DateTimeHelper.getFormattedDateTime(test.startedTime, LogSettings.logDateTimeFormat), test.name, test.status.toString().toLowerCase()};
        
        // new categories
        for (TestAttribute attr : test.categoryList) {
            addedFlag = ExtentFlag.getPlaceHolder("categoryViewTestDetails" + attr.getName());
            
            if (extentSource.indexOf(addedFlag) == -1) {
                String[] sourceValues = { attr.getName(), attr.getName().trim().toLowerCase().replace(" ", ""), addedFlag };

                s += SourceBuilder.build(CategoryHtml.getCategoryViewSource(), sourceKeys, sourceValues);
                testSource = SourceBuilder.build(CategoryHtml.getCategoryViewTestSource(), testKeys, testValues);    
                s = SourceBuilder.build(s, new String[] { addedFlag }, new String[] { testSource + addedFlag });
            }
            else {
                testSource = SourceBuilder.build(CategoryHtml.getCategoryViewTestSource(), testKeys, testValues);
                extentSource = SourceBuilder.build(extentSource, new String[] { addedFlag }, new String[] { testSource + addedFlag });
            }
        }
        
        extentSource = extentSource.replace(ExtentFlag.getPlaceHolder("extentCategoryDetails"), s + ExtentFlag.getPlaceHolder("extentCategoryDetails"));
    }
    
    public void initialize(String filePath, Boolean replace, DisplayOrder displayOrder) {
        this.displayOrder = displayOrder;
        this.filePath = filePath;
        
        if (extentSource != null) {
            return;
        }
        
        String sourceFile = "com/relevantcodes/extentreports/source/STANDARD.html";
                        
        if (!new File(filePath).isFile()) {
            replace = true;
        }

        if (replace) {
            synchronized (lock) {
                extentSource = Resources.getText(sourceFile);
            }
        } 
        else {
            synchronized (lock) {
                extentSource = FileReaderEx.readAllText(filePath);            
            }
        }
        
        runInfo = new RunInfo();
        runInfo.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
        
        categoryList = new CategoryList();
        mediaList = new MediaList();
    }
    
    public void terminate(List<ExtentTest> testList) {
    	if (testList != null) {
	        for (ExtentTest t : testList) {
	            if (!t.getTest().hasEnded) {
	                t.getTest().internalWarning += "Test did not end safely because endTest() was not called. There may be errors which are not reported correctly.";
	                addTest(t.getTest());
	            }
	        }
    	}
    	
        writeAllResources(null, null);
        
        extentSource = "";
        categoryList = null;
        runInfo = null;
        
        terminated = true;
    }
            
    public synchronized void writeAllResources(List<ExtentTest> testList, SystemInfo systemInfo) {
        if (terminated) {
            try {
                throw new IOException("Stream closed");
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            
            return;
        }
        
        if (systemInfo != null && systemInfo.getInfo() != null)
            updateSystemInfo(systemInfo.getInfo());
        
        if (testSource == "")
            return;
        
        runInfo.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
        
        updateCategoryList();
        updateSuiteExecutionTime();
        updateMediaInfo();
        
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            extentSource = extentSource.replace(ExtentFlag.getPlaceHolder("test"), testSource + ExtentFlag.getPlaceHolder("test"))
                .replace(ExtentFlag.getPlaceHolder("quickTestSummary"), quickSummarySource + ExtentFlag.getPlaceHolder("quickTestSummary"));
        }
        else {
            extentSource = extentSource.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + testSource)
                .replace(ExtentFlag.getPlaceHolder("quickTestSummary"), ExtentFlag.getPlaceHolder("quickTestSummary") + quickSummarySource);
        }
            
        Writer.getInstance().write(new File(filePath), extentSource);
        
        // clear test and summary sources
        testSource = "";
        quickSummarySource = "";
    }
    
    // #12: Ability to add categories and category-filters to tests
    private void updateCategoryList() {
        String catsAdded = "";
        String c = "";
        Iterator<String> iter = categoryList.categories.iterator();
        
        while (iter.hasNext()) {
            c = iter.next();
        
            if (extentSource.indexOf(ExtentFlag.getPlaceHolder("categoryAdded" + c)) > 0) {
                iter.remove();
            }
            else {
                  catsAdded += ExtentFlag.getPlaceHolder("categoryAdded" + c);              
            }
        }
        
        String source = CategorySourceBuilder.buildOptions(categoryList.categories);
        
        if (source != "") {
            synchronized (lock) {
                extentSource = extentSource
                        .replace(ExtentFlag.getPlaceHolder("categoryListOptions"), source + ExtentFlag.getPlaceHolder("categoryListOptions"))
                        .replace(ExtentFlag.getPlaceHolder("categoryAdded"), catsAdded + ExtentFlag.getPlaceHolder("categoryAdded"));
            }
        }
    }
    
    private void updateSuiteExecutionTime() {
        String[] keys = { ExtentFlag.getPlaceHolder("suiteStartTime"), ExtentFlag.getPlaceHolder("suiteEndTime") };
        String[] values = { runInfo.startedAt, runInfo.endedAt };
        
        synchronized (lock) {
            extentSource = SourceBuilder.buildRegex(extentSource, keys, values);
        }
    }
    
    private void updateSystemInfo(Map<String, String> info) {
        if (info == null)
            return;
        
        if (extentSource.indexOf(ExtentFlag.getPlaceHolder("systemInfoApplied")) > 0)
            return;
        
        if (info.size() > 0) {
            String systemSrc = SourceBuilder.getSource(info) + ExtentFlag.getPlaceHolder("systemInfoApplied");
            
            String[] keys = new String[] { ExtentFlag.getPlaceHolder("systemInfoView") };
            String[] values = new String[] { systemSrc + ExtentFlag.getPlaceHolder("systemInfoView") };
            
            synchronized (lock) {
                extentSource = SourceBuilder.buildRegex(extentSource, keys, values);
            }
        }
    }
    
    private void updateMediaInfo() {
        String imageSrc = MediaViewBuilder.getSource(mediaList.screenCapture, "img");
        
        String[] keys = new String[] { ExtentFlag.getPlaceHolder("imagesView") };
        String[] values = new String[] { imageSrc + ExtentFlag.getPlaceHolder("imagesView") };
        
        if (!(infoWrite >= 1 && values[0].indexOf("No media") >= 0)) {
            synchronized (lock) {
                // build sources by replacing the flag with the values
                extentSource = SourceBuilder.buildRegex(extentSource, keys, values);
                
                if (mediaList.screenCapture.size() > 0) {
                    try {
                        String match = RegexMatcher.getNthMatch(extentSource, ExtentFlag.getPlaceHolder("objectViewNullImg") + ".*" + ExtentFlag.getPlaceHolder("objectViewNullImg"), 0);
                        extentSource = extentSource.replace(match, "");
                    }
                    catch (Exception e) { }
                }
            }
            
            // clear the list so the same images are not written twice
            mediaList.screenCapture.clear();
        }
        
        String scSrc = MediaViewBuilder.getSource(mediaList.screencast, "vid");
        
        keys = new String[] { ExtentFlag.getPlaceHolder("videosView") };
        values = new String[] { scSrc + ExtentFlag.getPlaceHolder("videosView") };
        
        if (!(infoWrite >= 1 && values[0].indexOf("No media") >= 0)) {
            synchronized (lock) {
                // build sources by replacing the flag with the values
                extentSource = SourceBuilder.buildRegex(extentSource, keys, values);
                
                if (mediaList.screencast.size() > 0) {
                    try {
                        String match = RegexMatcher.getNthMatch(extentSource, ExtentFlag.getPlaceHolder("objectViewNullVid") + ".*" + ExtentFlag.getPlaceHolder("objectViewNullVid"), 0);
                        extentSource = extentSource.replace(match, "");
                    }
                    catch (Exception e) { }
                }
            }
            
            // clear the list so the same images are not written twice
            mediaList.screencast.clear();
        }
        
        infoWrite++;
    }
    
    private void updateSource(String source) {
        synchronized (lock) {
            extentSource = source;
        }
    }
    
    public ReportInstance() { }
    
    /**
     * Report Configuration
     * 
     * @author Anshoo
     *
     */
    public class ReportConfig {
        private String extentSource;

        private void updateSource() {
            extentSource = ReportInstance.this.extentSource;
        }
        
        /**
         * Inject javascript into the report
         * 
         * @param js - Javascript
         * @return {@link ReportConfig}
         */
        public ReportConfig insertJs(String js) {
            js = "<script type='text/javascript'>" + js + "</script>";
            
            updateSource();
            ReportInstance.this.updateSource(extentSource.replace(ExtentFlag.getPlaceHolder("customscript"), js + ExtentFlag.getPlaceHolder("customscript")));

            return this;
        }
        
        /**
         * Inject custom css into the report
         * 
         * @param styles CSS styles
         * @return {@link ReportConfig}
         */
        public ReportConfig insertCustomStyles(String styles) {
            styles = "<style type='text/css'>" + styles + "</style>";
            
            updateSource();
            ReportInstance.this.updateSource(extentSource.replace(ExtentFlag.getPlaceHolder("customcss"), styles + ExtentFlag.getPlaceHolder("customcss")));
            
            return this;
        }
        
        /**
         * Add a CSS stylesheet
         * 
         * @param cssFilePath Path of the .css file
         * @return {@link ReportConfig}
         */
        public ReportConfig addCustomStylesheet(String cssFilePath) {
            String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
            
            if (cssFilePath.substring(0, 1).equals(new String(".")) || cssFilePath.substring(0, 1).equals(new String("/")))
                link = "<link href='" + cssFilePath + "' rel='stylesheet' type='text/css' />";        
            
            updateSource();
            ReportInstance.this.updateSource(extentSource.replace(ExtentFlag.getPlaceHolder("customcss"), link + ExtentFlag.getPlaceHolder("customcss")));
            
            return this;
        }
        
        /**
         * Report headline
         * 
         * @param headline A short report summary or headline
         * @return {@link ReportConfig}
         */
        public ReportConfig reportHeadline(String headline) {
            Integer maxLength = 70;
            
            if (headline.matches((".*\\<[^>]+>.*"))) {
            	maxLength = 9999;
            }
            
            if (headline.length() > maxLength) {
                headline = headline.substring(0, maxLength - 1);
            }
            
            updateSource();
            
            String html = extentSource;
            String pattern = ExtentFlag.getPlaceHolder("headline") + ".*" + ExtentFlag.getPlaceHolder("headline");
            headline = pattern.replace(".*", headline); 
            
            String oldHeadline = RegexMatcher.getNthMatch(html, pattern, 0);
            ReportInstance.this.updateSource(html.replace(oldHeadline, headline));
            
            return this;
        }
        
        /**
         * Report name or title
         * 
         * @param name Name of the report
         * @return {@link ReportConfig}
         */
        public ReportConfig reportName(String name) {
            Integer maxLength = 20;
            
            if (name.matches((".*\\<[^>]+>.*"))) {
            	maxLength = 9999;
            }
            
            if (name.length() > maxLength) {
                name = name.substring(0, maxLength - 1);
            }
            
            updateSource();
            String html = extentSource;
            String pattern = ExtentFlag.getPlaceHolder("logo") + ".*" + ExtentFlag.getPlaceHolder("logo");
            name = pattern.replace(".*", name); 
            
            String oldName = RegexMatcher.getNthMatch(html, pattern, 0);
            ReportInstance.this.updateSource(html.replace(oldName, name));
            
            return this;
        }
        
        /**
         * Document Title
         * 
         * @param title Title
         * @return {@link ReportConfig}
         */
        public ReportConfig documentTitle(String title) {
            updateSource();
            
            String docTitle = "<title>.*</title>";
            String html = extentSource;
            
            ReportInstance.this.updateSource(html.replace(RegexMatcher.getNthMatch(html, docTitle, 0), docTitle.replace(".*", title)));
            
            return this;
        }

        public ReportConfig() { }
    }
}
