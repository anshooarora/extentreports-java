package com.aventstack.extentreports;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.gherkin.GherkinDialect;
import com.aventstack.extentreports.gherkin.GherkinDialectProvider;
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
        GherkinDialect dialect =  null;
        String apiKeyword = StringUtil.capitalize(keyword.trim());
        String refPath = clazz.getPackage().getName();
        
        try {
            dialect = new GherkinDialectProvider().getDialect();
            if (!dialect.getLanguage().equals("en")) {
                apiKeyword = null;
                Map<String, List<String>> keywords = dialect.getKeywords();
                
                for (Entry<String, List<String>> key : keywords.entrySet()) {
                    boolean b = key.getValue().stream().anyMatch(x -> x.trim().equalsIgnoreCase(keyword));
                    if (b) {
                        apiKeyword = StringUtil.capitalize(key.getKey());
                        break;
                    }
                }
            }
            
            Class<?> c = Class.forName(refPath + "." + apiKeyword);
            keywordClazz = (IGherkinFormatterModel) c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }
    
    IGherkinFormatterModel getKeyword() {
        return keywordClazz;
    }
}
