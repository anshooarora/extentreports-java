package com.relevantcodes.extentreports;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.gherkin.model.IGherkinFormatterModel;

public class GherkinKeyword {

    private static final Logger logger = Logger.getLogger(GherkinKeyword.class.getName());
    
    private Class<IGherkinFormatterModel> clazz = IGherkinFormatterModel.class;
    private IGherkinFormatterModel keywordClazz;
    
    public GherkinKeyword(String keyword) throws ClassNotFoundException {
        String refPath = clazz.getPackage().getName();
        Class<?> c = Class.forName(refPath + "." + keyword);
        
        try {
            keywordClazz = (IGherkinFormatterModel) c.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }
    
    IGherkinFormatterModel getKeyword() {
        return keywordClazz;
    }
}
