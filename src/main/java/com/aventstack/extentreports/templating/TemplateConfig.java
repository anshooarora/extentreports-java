package com.aventstack.extentreports.templating;

import freemarker.template.Configuration;

public class TemplateConfig {
    public Configuration getFreemarkerConfig(Class<?> clazz, String basePackagePath, String encoding) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(clazz, basePackagePath);
        cfg.setDefaultEncoding(encoding);
        cfg.setAPIBuiltinEnabled(true);
        return cfg;
    }

    public Configuration getFreemarkerConfig(Class<?> clazz, String encoding) {
        return getFreemarkerConfig(clazz, "/", encoding);
    }
}