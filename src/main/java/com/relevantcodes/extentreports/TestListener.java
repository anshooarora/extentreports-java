package com.relevantcodes.extentreports;

import java.io.IOException;

import com.relevantcodes.extentreports.model.Author;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Test;

public interface TestListener {
    void onTestStarted(Test test);
    void onNodeStarted(Test node);
    void onLogAdded(Test test, Log log);
    void onCategoryAssigned(Test test, Category category);
    void onAuthorAssigned(Test test, Author author);
    void onScreenCaptureAdded(Test test, ScreenCapture screenCapture) throws IOException;
}
