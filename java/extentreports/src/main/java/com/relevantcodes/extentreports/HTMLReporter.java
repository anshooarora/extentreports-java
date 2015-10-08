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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.relevantcodes.extentreports.model.CategoryList;
import com.relevantcodes.extentreports.model.SuiteTimeInfo;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.SystemInfoHtml;
import com.relevantcodes.extentreports.utils.DateTimeUtil;
import com.relevantcodes.extentreports.utils.FileReaderEx;
import com.relevantcodes.extentreports.utils.Resources;
import com.relevantcodes.extentreports.utils.Writer;

/**  
 * Concrete HTMLReporter class
 * 
 * @author Anshoo
 *
 */
class HTMLReporter extends LogSettings implements IReporter {
    private Report report;
    
    private String filePath;
    private DisplayOrder displayOrder;
    private NetworkMode networkMode;
    
    private CategoryList categoryList;
    private SuiteTimeInfo suiteTimeInfo;
    
    private volatile Document extentDoc;
    private volatile Element testCollection;
    
    private Boolean terminated = false;
    
    private final String offlineFolderParent = "extentreports";
    private final String packagePath = "com/relevantcodes/extentreports/source/";
    
    @Override
    public void start(Report report) {
        this.report = report;
        
        this.displayOrder = report.getDisplayOrder();
        this.networkMode = report.getNetworkMode();
        
        // if document is already created, prevent re-initialization
        if (extentDoc != null) {
            return;
        }
        
        File reportFile = new File(filePath);
        
        String sourceFile = packagePath + "ExtentTemplate.html";
        
        if (networkMode == NetworkMode.OFFLINE) {
            sourceFile = packagePath + "/ExtentTemplate.Offline.html";
            
            initOfflineMode(reportFile);
        }
         
        Boolean replace = report.getReplaceExisting();
        
        if (!reportFile.isFile()) {
            replace = true;
        }
        
        // create Jsoup document
        String extentSource = replace ? Resources.getText(sourceFile) : FileReaderEx.readAllText(filePath);
        extentDoc = Jsoup.parse(extentSource);
        
        // select test-collection -> all test list-items will be added to it
        testCollection = extentDoc.select(".test-collection").first();
        
        // set suite time-info
        suiteTimeInfo = report.getSuiteTimeInfo();
        suiteTimeInfo.setSuiteStartTimestamp(Calendar.getInstance().getTimeInMillis());
        suiteTimeInfo.setSuiteEndTimestamp(Calendar.getInstance().getTimeInMillis());
        
        // if a brand new report is created, update the started time 
        if (replace) {
            extentDoc
                .select(".suite-started-time")
                .first()
                    .text(DateTimeUtil.getFormattedDateTime(
                            suiteTimeInfo.getSuiteStartTimestamp(), 
                            getLogDateTimeFormat())
                    );
        }
        
        // list of categories added to tests
        categoryList = new CategoryList();
    }
    
    private void initOfflineMode(File file) {
        String cssPath = packagePath + "offline/css/";
        String jsPath = packagePath + "offline/js/";
        
        String[] css = { 
                "css.css", 
                "font-awesome.css.map", 
                "fontawesome-webfont.eot",
                "fontawesome-webfont.svg",
                "fontawesome-webfont.ttf",
                "fontawesome-webfont.woff",
                "fontawesome-webfont.woff2",
                "FontAwesome.otf"
        };
        String[] js = { 
                "scripts.js"
        };
        
        String[] folderNames = { "css", "js" };
        
        // create offline folders from folderName
        for (String name : folderNames) {
            new File(file.getParent() + File.separator + offlineFolderParent + File.separator + name).mkdirs();
        }
        
        // copy files to extent/dir
        for (String f : css) {
            Resources.moveResource(cssPath + f, file.getParent() + File.separator + offlineFolderParent + File.separator + "css" + File.separator + f);
        }
        for (String f : js) {
            Writer.getInstance().write(new File(file.getParent() + File.separator + offlineFolderParent + File.separator + "js" + File.separator + f), Resources.getText(jsPath + f));
        }
    }
    
    @Override
    public synchronized void flush() {
        if (terminated) {
            try {
                throw new IOException("Unable to write source: Stream closed.");
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            
            return;
        }
        
        updateSuiteExecutionTime();
        
        SystemInfo systemInfo = report.getSystemInfo();
        
        if (systemInfo != null && systemInfo.getInfo() != null && systemInfo.getInfo().size() > 0) {
            updateSystemInfo(systemInfo.getInfo());
        }
        
        List<ExtentTest> testList = report.getTestList();
        
        if (testList == null) {
            return;
        }
        
        updateCategoryList();

        Writer.getInstance()
            .write(
                    new File(filePath), 
                    Parser.unescapeEntities(extentDoc.outerHtml().replace("\n", "").replace("\r", "").replace("    ", "").replace("\t",  ""), true)); //.replace("\n", "").replace("\r", "").replace("    ", "").replace("\t",  "")
    }
    
    private synchronized void updateSuiteExecutionTime() {
        suiteTimeInfo.setSuiteEndTimestamp(Calendar.getInstance().getTimeInMillis());

        extentDoc
            .select(".suite-ended-time")
            .first()
            .text(DateTimeUtil.getFormattedDateTime(suiteTimeInfo.getSuiteEndTimestamp(), getLogDateTimeFormat()));
        
        extentDoc
            .select(".suite-total-time-taken")
            .first()
            .text(suiteTimeInfo.getTimeDiff());
    }
    
    private synchronized void updateSystemInfo(Map<String, String> info) {
        if (info == null)
            return;
        
        if (info.size() > 0) {
            Element parentDiv = extentDoc.select(".system-view").first();
            Boolean added;
            
            for (Map.Entry<String, String> entry : info.entrySet()) {
                added = false;
                
                Elements panelNames = parentDiv.select(".panel-name");
                
                if (panelNames.size() > 0) {
                    for (Element panelName : panelNames) {
                         if (panelName.text().equals(entry.getKey())) {
                             parentDiv.select(".panel-lead").first().text(entry.getValue());
                             added = true;
                             break;
                         }
                    }
                }
                
                if (!added) {
                    Document divCol = Jsoup.parseBodyFragment(SystemInfoHtml.getColumn());
                    
                    divCol.select(".panel-name").first().text(entry.getKey());
                    divCol.select(".panel-lead").first().text(entry.getValue());
                    
                    parentDiv.appendChild(divCol.select(".col").first());                    
                }
            }
        }
    }
    
    private synchronized void updateCategoryList() {
        String c = "";
        Iterator<String> iter = categoryList.getCategoryList().iterator();
        
        Elements options = extentDoc.select(".category-toggle option");
        
        while (iter.hasNext()) {
            c = iter.next();
   
            for (Element option : options) {
                if (option.text().equals(c)) {
                    iter.remove();
                }
            }
        }
        
        for (String cat : categoryList.getCategoryList()) {
            options.first().nextSibling().after("<option value='-1'>" + cat + "</option>");
        }
    }
    
    @Override
    public void stop() {
        terminated = true;
    }
    
    @Override
    public void setTestRunnerLogs() {
        extentDoc.select("#testrunner-logs-view .card-panel").first().append("<p>" + report.getTestRunnerLogs() + "</p>");
    }

    // adds tests as HTML source
    @Override
    public synchronized void addTest() {
        Test test = report.getTest();
        
        addTest(TestBuilder.getHTMLTest(test));
        appendTestCategories(test);
    }
    
    private synchronized void addTest(Element test) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            testCollection.appendChild(test);
            return;
        }
        
        testCollection.prependChild(test);
    }
    
    private synchronized void appendTestCategories(Test test) {
        for (TestAttribute attr : test.getCategoryList()) {
            if (!categoryList.getCategoryList().contains(attr.getName())) {
                categoryList.setCategory(attr.getName());
            }
        }
        
        updateCategoryView(test);
    }
    
    private synchronized void updateCategoryView(Test test) {
        if (test.isChildNode) {
            return;
        }

        CategoryBuilder.buildCategoryViewLink(extentDoc, test);
    }
    
    public HTMLReporter(String filePath) { 
        this.filePath = filePath;
    }
    
    /**
     * Report Configuration
     * 
     * @author Anshoo
     *
     */
    public class Config {
        private Document getDoc() {
            return HTMLReporter.this.extentDoc;
        }
        
        /**
         * Inject javascript into the report
         * 
         * @param js - Javascript
         * @return {@link Config}
         */
        public Config insertJs(String js) {
            js = "<script type='text/javascript'>" + js + "</script>";

            getDoc().select("body").first().append(js);
            
            return this;
        }
        
        /**
         * Inject custom css into the report
         * 
         * @param styles CSS styles
         * @return {@link Config}
         */
        public Config insertCustomStyles(String styles) {
            getDoc().select("style").last().append(styles);
            
            return this;
        }
        
        /**
         * Add a CSS stylesheet
         * 
         * @param cssFilePath Path of the .css file
         * @return {@link Config}
         */
        public Config addCustomStylesheet(String cssFilePath) {
            String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
            
            if (cssFilePath.substring(0, 1).equals(new String(".")) || cssFilePath.substring(0, 1).equals(new String("/")))
                link = "<link href='" + cssFilePath + "' rel='stylesheet' type='text/css' />";        
            
            getDoc().select("style").last().after(link);
            
            return this;
        }
        
        /**
         * Report headline
         * 
         * @param headline A short report summary or headline
         * @return {@link Config}
         */
        public Config reportHeadline(String headline) {
            Integer maxLength = 70;
            
            if (headline.matches((".*\\<[^>]+>.*"))) {
                maxLength = 9999;
            }
            
            if (headline.length() > maxLength) {
                headline = headline.substring(0, maxLength - 1);
            }
            
            getDoc().select(".report-headline").first().text(headline);
            
            return this;
        }
        
        /**
         * Report name or title
         * 
         * @param name Name of the report
         * @return {@link Config}
         */
        public Config reportName(String name) {
            Integer maxLength = 20;
            
            if (name.matches((".*\\<[^>]+>.*"))) {
                maxLength = 9999;
            }
            
            if (name.length() > maxLength) {
                name = name.substring(0, maxLength - 1);
            }
            
            getDoc().select(".report-name").first().text(name);
            
            return this;
        }
        
        /**
         * Document Title
         * 
         * @param title Title
         * @return {@link Config}
         */
        public Config documentTitle(String title) {
            getDoc().select("title").first().text(title);
            
            return this;
        }

        public Config() { }
    }
}
