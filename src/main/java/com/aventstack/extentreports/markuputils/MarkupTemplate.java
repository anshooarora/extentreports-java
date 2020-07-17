package com.aventstack.extentreports.markuputils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.templating.FreemarkerTemplate;

class MarkupTemplate {
    protected static final FreemarkerTemplate ft = new FreemarkerTemplate(ExtentReports.class, "markup/", "UTF-8");
}
