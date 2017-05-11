package com.aventstack.extentreports;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;

import freemarker.template.utility.StringUtil;

/**
 * Allows {@link IGherkinFormatterModel} to be returned by using a name, from the below gherkin model classes:
 * 
 * <ul>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
 * 	<li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
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
 * @see IGherkinFormatterModel
 */
public class GherkinKeyword {

    private static final Logger logger = Logger.getLogger(GherkinKeyword.class.getName());
    
    private Class<IGherkinFormatterModel> clazz = IGherkinFormatterModel.class;
    private IGherkinFormatterModel keywordClazz;
    
    public GherkinKeyword(String keyword) throws ClassNotFoundException {
        keyword = StringUtil.capitalize(keyword.trim());
        String refPath = clazz.getPackage().getName();
        
        try {
            Class<?> c = Class.forName(refPath + "." + keyword);
            keywordClazz = (IGherkinFormatterModel) c.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }
    
    IGherkinFormatterModel getKeyword() {
        return keywordClazz;
    }
}
