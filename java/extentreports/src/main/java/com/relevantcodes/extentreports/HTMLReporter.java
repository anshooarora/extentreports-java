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
    // report instance    
    private Report report;
    
    // path of the html file
    private String filePath;
    
    // display-order (default = OLDEST_FIRST)
    private DisplayOrder displayOrder;
    
    // network mode (default = ONLINE)
    private NetworkMode networkMode;
    
    // master list of categories added to tests
    private CategoryList categoryList;
    
    // suite started, ended times
    private SuiteTimeInfo suiteTimeInfo;
    
    // master extent.html Document
    private volatile Document extentDoc;
    
    // collection of tests added using endTest/addTest
    private volatile Element testCollection;
    
    // marks the report session as terminated
    // @see close()
    private Boolean terminated = false;
    
    // denotes if the report uses source from template
    // default = false, source comes from user file (replaceExisting = false)
    // if user initialies Extent as:
    //        new ExtentReports("filePath", true);
    //        this flag becomes true
    private boolean usesTemplateSource = false;
    
    // folder where offline artifacts are stored
    private final String offlineFolderParent = "extentreports";
    
    // package path containing source files
    private final String packagePath = "com/relevantcodes/extentreports/view/";
    
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
            sourceFile = packagePath + "ExtentTemplate.Offline.html";
            
            initOfflineMode(reportFile);
        }
         
        Boolean replace = report.getReplaceExisting();
        this.usesTemplateSource = replace;
        
        if (!reportFile.isFile()) {
            replace = true;
        }
        
        String extentSource = replace 
                ? Resources.getText(sourceFile) 
                : FileReaderEx.readAllText(filePath);
        
        // create Jsoup document from source
        extentDoc = Jsoup.parse(extentSource);
        
        // select test-collection -> all test list-items will be added to it
        testCollection = extentDoc.select(".test-collection").first();
        
        // set suite time-info
        suiteTimeInfo = report.getSuiteTimeInfo();
        
        // if a brand new report is created, update the started time 
        if (replace) {
            Elements startedTime = extentDoc.select(".suite-started-time");
            
            for (Element el : startedTime) {
                el.text(DateTimeUtil.getFormattedDateTime(
                        suiteTimeInfo.getSuiteStartTimestamp(), 
                        getLogDateTimeFormat())
                );
            }
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
        
        suiteTimeInfo.setSuiteEndTimestamp(Calendar.getInstance().getTimeInMillis());
        
        updateSuiteExecutionTime();
        
        SystemInfo systemInfo = report.getSystemInfo();
        
        if (systemInfo != null && systemInfo.getInfo() != null && systemInfo.getInfo().size() > 0) {
            updateSystemInfo(systemInfo.getInfo());
        }
        
        List<ExtentTest> testList = report.getTestList();
        
        if (testList == null) {
            return;
        }
        
        updateCategoryLists();

        String extentSource = extentDoc.outerHtml();//.replace("    ", "").replace("\t",  "");
        
        Writer.getInstance().write(new File(filePath), Parser.unescapeEntities(extentSource, true));
    }
    
    private synchronized void updateSuiteExecutionTime() {
        if (usesTemplateSource) {
            Elements startedTime = extentDoc.select(".suite-started-time");
        
            for (Element el : startedTime) {
                el.text(DateTimeUtil.getFormattedDateTime(
                        suiteTimeInfo.getSuiteStartTimestamp(), 
                        getLogDateTimeFormat())
                );
            }
        }
        
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
            Element tbody = extentDoc.select(".system-view tbody").first();
            Boolean entryAdded;
            
            for (Map.Entry<String, String> entry : info.entrySet()) {
                entryAdded = false;
                
                Elements panelNames = tbody.select("tr");
                
                if (panelNames.size() > 0) {
                    for (Element panelName : panelNames) {
                         if (panelName.child(0).text().equals(entry.getKey())) {
                        	 panelName.child(1).text(entry.getValue());

                        	 entryAdded = true;
                             break;
                         }
                    }
                }
                
                if (!entryAdded) {
                	Element tr = Jsoup.parseBodyFragment("").appendElement("tr");
                    tr.appendElement("td").text(entry.getKey());
                    tr.appendElement("td").text(entry.getValue());
                    
                    tbody.appendChild(tr);                    
                }
            }
        }
    }
    
    private synchronized void updateCategoryLists() {
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
        
        Element catTbody = extentDoc.select(".category-summary-view tbody").first();
        
        for (String cat : categoryList.getCategoryList()) {
            options.first().nextSibling().after("<option value='-1'>" + cat + "</option>");
            catTbody.appendElement("tr").appendElement("td").text(cat);
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
        
        addTest(TestBuilder.getTestAsHTMLElement(test));
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
