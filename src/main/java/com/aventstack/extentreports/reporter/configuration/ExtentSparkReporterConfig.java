package com.aventstack.extentreports.reporter.configuration;

import java.io.File;
import java.util.stream.Stream;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.util.ResourceHelper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Defines configuration settings for the Spark reporter
 */
@Getter
@Setter
@SuperBuilder
public class ExtentSparkReporterConfig extends InteractiveReporterConfig {
    private static final String REPORTER_NAME = "spark";
    private static final String SEP = "/";
    private static final String COMMONS = "commons" + SEP;
    private static final String CSS = "css" + SEP;
    private static final String JS = "js" + SEP;
    private static final String ICONS = "icons" + SEP;
    private static final String IMG = "img" + SEP;

    @Builder.Default
    private Boolean offlineMode = false;
    @Builder.Default
    private String resourceCDN = "github";

    /**
     * Creates the HTML report, saving all resources (css, js, fonts) in the
     * same location, so the report can be viewed without an internet connection
     * 
     * @param offlineMode
     *            Setting to enable an offline accessible report
     */
    public void enableOfflineMode(Boolean offlineMode) {
        this.offlineMode = offlineMode;
        if (offlineMode && reporter != null) {
            File f = Offline.getTargetDirectory(((ExtentSparkReporter) reporter).getFile());
            String resPackage = ExtentReports.class.getPackage().getName().replace(".", SEP);
            resPackage += SEP + "offline" + SEP;
            String[] resx = Offline.combineAll();
            ResourceHelper.saveOfflineResources(resPackage, resx, f.getAbsolutePath());
        }
    }

    private static class Offline {
        private static File getTargetDirectory(File f) {
            String dir = f.getAbsolutePath().replace("\\", SEP);
            if (!f.isDirectory())
                dir = new File(dir).getParent();
            dir += "/" + REPORTER_NAME;
            return new File(dir);
        }

        private static String[] combineAll() {
            return combine(getJSFiles(), getCSSFiles(), getIconFiles(), getImgFiles());
        }

        private static String[] combine(String[]... array) {
            String[] result = new String[]{};
            for (String[] arr : array)
                result = Stream.of(result, arr).flatMap(Stream::of).toArray(String[]::new);
            return result;
        }

        private static String[] getJSFiles() {
            final String commonsPath = COMMONS + JS;
            final String reporterPath = REPORTER_NAME + SEP + JS;
            final String[] files = {reporterPath + "spark-script.js", commonsPath + "jsontree.js"};
            return files;
        }

        private static String[] getCSSFiles() {
            final String reporterPath = REPORTER_NAME + SEP + CSS;
            final String[] files = {reporterPath + "spark-style.css"};
            return files;
        }

        private static String[] getIconFiles() {
            final String path = COMMONS + CSS + ICONS;
            final String iconDirPath = "fontawesome" + SEP;
            final String[] files = {path + "font-awesome.min.css", path + iconDirPath + "fontawesome-webfont.eot",
                    path + iconDirPath + "fontawesome-webfont.svg", path + iconDirPath + "fontawesome-webfont.ttf",
                    path + iconDirPath + "fontawesome-webfont.woff", path + iconDirPath + "fontawesome-webfont.woff2",
                    path + iconDirPath + "FontAwesome.otf"};
            return files;
        }

        private static String[] getImgFiles() {
            final String path = COMMONS + IMG;
            final String[] files = {path + "logo.png"};
            return files;
        }
    }
}