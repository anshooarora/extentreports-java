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
import com.relevantcodes.extentreports.model.MediaList;
import com.relevantcodes.extentreports.model.RunInfo;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.SystemInfoHtml;
import com.relevantcodes.extentreports.support.DateTimeHelper;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.Resources;
import com.relevantcodes.extentreports.support.Writer;

public class ReportInstance {
    private Boolean terminated = false;
    private MediaList mediaList;
    private CategoryList categoryList;
    private RunInfo runInfo;
    private DisplayOrder displayOrder;
    private volatile Element testListElement;
    private volatile Element quickTestSummaryListElement;
    private volatile Document extentDoc;
    private String filePath;
    private final String offlineFolderParent = "extentreports";
    
    public synchronized void initialize(String filePath, boolean replace, DisplayOrder displayOrder, NetworkMode networkMode) {
        this.displayOrder = displayOrder;
        this.filePath = filePath;
        
        if (extentDoc != null) {
            return;
        }
        
        File reportFile = new File(filePath);

        if (!reportFile.getParentFile().exists()) {
            reportFile.getParentFile().mkdirs();
        }
        
        String sourceFile = "com/relevantcodes/extentreports/source/STANDARD.html";
        
        if (networkMode == NetworkMode.OFFLINE) {
            sourceFile = "com/relevantcodes/extentreports/source/STANDARD.offline.html";
            
            initOfflineMode(reportFile);
        }        
                        
        if (!reportFile.isFile()) {
            replace = true;
        }
        
        String extentSource = replace ? Resources.getText(sourceFile) : FileReaderEx.readAllText(filePath);
        extentDoc = Jsoup.parse(extentSource);
        
        testListElement = extentDoc.select(".test-list").first();
        quickTestSummaryListElement = extentDoc.select(".tests-quick-view table > tbody").first();
        
        runInfo = new RunInfo();
        runInfo.setSuiteStartTimestamp(
                DateTimeHelper.getFormattedDateTime(
                        Calendar.getInstance().getTime(), 
                        LogSettings.logDateTimeFormat
                )
        );
        
        categoryList = new CategoryList();
        mediaList = new MediaList();
    }
    
    public void addTestRunnerLog(String log) {
        extentDoc.select("#testrunner-logs-view .card-panel").first().append("<p>" + log + "</p>");
    }
    
    public void terminate(List<ExtentTest> testList, SystemInfo systemInfo) {
        if (testList != null) {
            for (ExtentTest t : testList) {
                if (!t.getTest().hasEnded) {
                    t.getTest().setInternalWarning(t.getTest().getInternalWarning() + "Test did not end safely because endTest() was not called. There may be errors which are not reported correctly.");
                    t.getTest().hasEnded = true;
                }
            }
        }
        
        writeAllResources(testList, systemInfo);
        
        extentDoc.remove();

        terminated = true;
    }
    
    private void initOfflineMode(File file) {
        String cssPath = "com/relevantcodes/extentreports/source/offline/css/";
        String jsPath = "com/relevantcodes/extentreports/source/offline/js/";
        
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
            new File(file.getParent() + "\\" + offlineFolderParent + "\\" + name).mkdirs();
        }
        
        // copy files to extent/dir
        for (String f : css) {
            Resources.moveResource(cssPath + f, file.getParent() + "\\" + offlineFolderParent + "\\css\\" + f);
        }
        for (String f : js) {
            Writer.getInstance().write(new File(file.getParent() + "\\" + offlineFolderParent + "\\js\\" + f), Resources.getText(jsPath + f));
        }
    }
    
    public synchronized void writeAllResources(List<ExtentTest> testList, SystemInfo systemInfo) {
        if (terminated) {
            try {
                throw new IOException("Unable to write source: Stream closed.");
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            
            return;
        }
        
        if (systemInfo != null && systemInfo.getInfo() != null && systemInfo.getInfo().size() > 0) {
            updateSystemInfo(systemInfo.getInfo());
        }
        
        if (testList == null) {
            return;
        }
        
        runInfo.setSuiteEndTimestamp(
                DateTimeHelper.getFormattedDateTime(
                        Calendar.getInstance().getTime(), 
                        LogSettings.logDateTimeFormat
                )
        );
        
        updateCategoryList();
        updateSuiteExecutionTime();
        updateMediaInfo();

        // .replace("\n", "").replace("\r", "").replace("    ", "").replace("\t",  "")
        Writer.getInstance().write(new File(filePath), Parser.unescapeEntities(extentDoc.outerHtml(), true));
    }
    
    private synchronized void updateMediaInfo() {
        extentDoc
            .select("#media-view")
                .first()
                    .append(MediaViewBuilder.getSource(mediaList.getScreenCaptureList()).outerHtml())
                    .append(MediaViewBuilder.getSource(mediaList.getScreencastList()).outerHtml());
    }
    
    private synchronized void updateCategoryView(Test test) {
        if (test.isChildNode) {
            return;
        }

        CategoryBuilder.buildCategoryViewLink(extentDoc, test);
    }
    
    private synchronized void updateCategoryList() {
        String c = "";
        Iterator<String> iter = categoryList.getCategoryList().iterator();
        
        Elements options = extentDoc.select(".category-toggle select option");
        
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
    
    private synchronized void updateSuiteExecutionTime() {
        extentDoc.select(".suite-started-time").first().text(runInfo.getSuiteStartTimestamp());
    }
    
    private synchronized void updateSystemInfo(Map<String, String> info) {
        if (info == null)
            return;
        
        if (info.size() > 0) {
            Element parentDiv = extentDoc.select(".system-view > .row").first();
            
            for (Map.Entry<String, String> entry : info.entrySet()) {
                Document divCol = Jsoup.parseBodyFragment(SystemInfoHtml.getColumn());
                
                divCol.select(".panel-name").first().text(entry.getKey());
                divCol.select(".panel-lead").first().text(entry.getValue());
                
                parentDiv.appendChild(divCol.select(".col").first());
            }
        }
    }
    
    // adds tests as HTML source
    public synchronized void addTest(Test test) {
        if (test.getEndedTime() == null) {
            test.setEndedTime(Calendar.getInstance().getTime());
        }
        
        for (ScreenCapture sc : test.getScreenCaptureList()) {
            mediaList.setScreenCapture(sc);
        }
        
        for (Screencast sc : test.getScreencastList()) {
            mediaList.setScreencast(sc);
        }
        
        test.prepareFinalize();
        
        addTest(TestBuilder.getParsedTest(test));
        addQuickTestSummary(TestBuilder.getQuickTestSummary(test));
        addCategories(test);
        updateCategoryView(test);
    }
    
    private synchronized void addTest(Element testElement) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            testListElement.appendChild(testElement);
            return;
        }
        
        testListElement.prependChild(testElement);
    }
    
    private synchronized void addQuickTestSummary(Element testElement) {
        if (displayOrder == DisplayOrder.OLDEST_FIRST) {
            quickTestSummaryListElement.appendChild(testElement);
            return;
        }

        quickTestSummaryListElement.prependChild(testElement);
    }
    
    private synchronized void addCategories(Test test) {
        for (TestAttribute attr : test.getCategoryList()) {
            if (!categoryList.getCategoryList().contains(attr.getName())) {
                categoryList.setCategory(attr.getName());
            }
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
        private Document getDoc() {
            return ReportInstance.this.extentDoc;
        }
        
        /**
         * Inject javascript into the report
         * 
         * @param js - Javascript
         * @return {@link ReportConfig}
         */
        public ReportConfig insertJs(String js) {
            js = "<script type='text/javascript'>" + js + "</script>";

            getDoc().select("body").first().append(js);
            
            return this;
        }
        
        /**
         * Inject custom css into the report
         * 
         * @param styles CSS styles
         * @return {@link ReportConfig}
         */
        public ReportConfig insertCustomStyles(String styles) {
            getDoc().select("style").last().append(styles);
            
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
            
            getDoc().select("style").last().after(link);
            
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
            
            getDoc().select(".report-headline").first().text(headline);
            
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
            
            getDoc().select(".report-name").first().text(name);
            
            return this;
        }
        
        /**
         * Document Title
         * 
         * @param title Title
         * @return {@link ReportConfig}
         */
        public ReportConfig documentTitle(String title) {
            getDoc().select("title").first().text(title);
            
            return this;
        }

        public ReportConfig() { }
    }
}
