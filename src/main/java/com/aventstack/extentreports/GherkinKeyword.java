package com.aventstack.extentreports;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.utils.StringUtil;

public class GherkinKeyword {

/**
 * Allows {@link IGherkinFormatterModel} to be returned by using a name, from the below gherkin model classes:
 * 
 * <ul>
 *  <li>{@link Feature}</li>
 *  <li>{@link Background}</li>
 *  <li>{@link Scenario}</li>
 *  <li>{@link Given}</li>
 *  <li>{@link When}</li>
 *  <li>{@link Then}</li>
 *  <li>{@link And}</li>
 * </ul>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * extent.createTest(new GherkinKeyword("Feature"), "bddTest");
 * test.createNode(new GherkinKeyword("Scenario"), bddNode");
 * </pre>
 * 
 * @see {@link IGherkinFormatterModel}
 */
private static final Logger logger = Logger.getLogger(GherkinKeyword.class.getName());
    
    private Class<IGherkinFormatterModel> clazz = IGherkinFormatterModel.class;
    private IGherkinFormatterModel keywordClazz;
    
    
    public GherkinKeyword(String keyword) throws ClassNotFoundException {
        keyword = StringUtil.capitalize(keyword);
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
