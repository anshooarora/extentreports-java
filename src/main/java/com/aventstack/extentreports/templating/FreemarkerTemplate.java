package com.aventstack.extentreports.templating;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.aventstack.extentreports.io.BufferedWriterWriter;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerTemplate {
    private final TemplateConfig templateConfig = new TemplateConfig();
    private final Configuration freemarkerConfig;

    public FreemarkerTemplate(Configuration freemarkerConfiguration) {
        this.freemarkerConfig = freemarkerConfiguration;
    }

    public FreemarkerTemplate(Class<?> clazz, String encoding) {
        freemarkerConfig = templateConfig.getFreemarkerConfig(clazz, encoding);
    }

    public FreemarkerTemplate(Class<?> clazz, String basePackagePath, String encoding) {
        freemarkerConfig = templateConfig.getFreemarkerConfig(clazz, basePackagePath, encoding);
    }

    public Template createTemplate(String templatePath)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        return freemarkerConfig.getTemplate(templatePath);
    }

    public String getSource(Template template, Map<String, Object> templateMap) throws TemplateException, IOException {
        String source = processTemplate(template, templateMap);
        return source;
    }

    public void writeTemplate(Template template, Map<String, Object> templateMap, File outputFile)
            throws TemplateException, IOException {
        String source = getSource(template, templateMap);
        BufferedWriterWriter.getInstance().write(outputFile, source);
    }

    private String processTemplate(Template template, Map<String, Object> templateMap)
            throws TemplateException, IOException {
        StringWriter out = new StringWriter();
        template.process(templateMap, out);
        String source = out.toString();
        out.close();
        return source;
    }
}