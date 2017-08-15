package com.aventstack.extentreports.gherkin;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * <p>
 * Modified version of GherkinDialectProvider.java from cucumber/gherkin. Source url:  
 * https://github.com/cucumber/cucumber/blob/master/gherkin/java/src/main/java/gherkin/GherkinDialectProvider.java.
 * 
 * <p>
 * Gherkin source is licensed under the MIT License
 *
 */
@SuppressWarnings("unchecked")
public class GherkinDialectProvider {

    private static Map<String, Map<String, List<String>>> DIALECTS;
    private Map<String, List<String>> map;
    private final String GHERKIN_LANGUAGES_JSON_URL = "https://github.com/cucumber/cucumber/blob/master/gherkin/java/src/main/resources/gherkin/gherkin-languages.json";
    private static String language;
    
    static {
        Gson gson = new Gson();
        try {
            Reader dialects = new InputStreamReader(GherkinDialectProvider.class.getResourceAsStream("gherkin-languages.json"), "UTF-8");
            DIALECTS = gson.fromJson(dialects, Map.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public GherkinDialectProvider(String language) throws UnsupportedEncodingException {
        if (GherkinDialectProvider.language == null)
            GherkinDialectProvider.language = language;
        
        map = DIALECTS.get(GherkinDialectProvider.language);
        
        if (map == null)
            throw new UnsupportedEncodingException("Invalid language [" + language + "]. See list of supported languages: " + GHERKIN_LANGUAGES_JSON_URL);
    }
    
    public GherkinDialectProvider() throws UnsupportedEncodingException {
        this("en");
    }
    
    public GherkinDialect getDialect() {
        return new GherkinDialect(language, map);
    }
    
}
