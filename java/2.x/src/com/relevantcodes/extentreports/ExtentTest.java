package com.relevantcodes.extentreports;

import java.util.Calendar;

import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.ImageHtml;
import com.relevantcodes.extentreports.source.ScreencastHtml;
import com.relevantcodes.extentreports.support.DateTimeHelper;

public class ExtentTest {
    private Test test;
    private LogStatus runStatus = LogStatus.UNKNOWN;
    
    public ExtentTest(String testName, String description) {
        test = new Test();
        
        test.name = testName;
        test.description = description;
        test.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
    }
    
    public void log(LogStatus logStatus, String stepName, String details) {
        Log evt = new Log();
        
        evt.timestamp = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logTimeFormat);
        evt.logStatus = logStatus;
        evt.stepName = stepName;
        evt.details = details;
                
        test.log.add(evt);
        
        trackLastRunStatus(logStatus);
    }
    
    public void log(LogStatus logStatus, String details) {
        log(logStatus, "", details);
    }
    
    public String addScreenCapture(String imgPath) {
        String screenCaptureHtml;
        
        if (isPathRelative(imgPath)) {
            screenCaptureHtml = ImageHtml.getSource(imgPath).replace("file:///", "");
        }
        else {
            screenCaptureHtml = ImageHtml.getSource(imgPath);
        }
        
        ScreenCapture img = new ScreenCapture();
        img.src = screenCaptureHtml;
        img.testName = test.name;
        
        test.screenCapture.add(img);

        return screenCaptureHtml;
    }
    
    public String addScreencast(String screencastPath) {
        String screencastHtml;
        
        if (isPathRelative(screencastPath)) {
            screencastHtml = ScreencastHtml.getSource(screencastPath).replace("file:///", "");
        }
        else {
            screencastHtml = ScreencastHtml.getSource(screencastPath);
        }
        
        Screencast sc = new Screencast();
        sc.src = screencastHtml;
        sc.testName = test.name;
        
        test.screencast.add(sc);
        
        return screencastPath;
    }
    
    private Boolean isPathRelative(String path) {
        if (path.indexOf("http") == 0 || path.indexOf(".") == 0 || path.indexOf("/") == 0) {
            return true;            
        }
        
        return false;
    }
    
    private void trackLastRunStatus(LogStatus logStatus) {
        if (runStatus == LogStatus.UNKNOWN) {
            if (logStatus == LogStatus.INFO) {
                runStatus = LogStatus.PASS;
            }
            else {
                runStatus = logStatus;
            }
            
            return;
        }
        
        if (runStatus == LogStatus.FATAL) return;
        
        if (logStatus == LogStatus.FATAL) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.FAIL) return;
        
        if (logStatus == LogStatus.FAIL) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.ERROR) return;
        
        if (logStatus == LogStatus.ERROR) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.WARNING) return;
        
        if (logStatus == LogStatus.WARNING) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.PASS || runStatus == LogStatus.INFO) {
            runStatus = LogStatus.PASS;
            return;
        }
        
        runStatus = LogStatus.SKIP;        
    }
    
    public Test getTest() {
        test.status = runStatus;
        
        return test;
    }
}
