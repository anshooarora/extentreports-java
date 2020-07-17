package com.aventstack.extentreports.gherkin;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

/**
 * <p>
 * Modified version of GherkinDialectProvider.java from cucumber/gherkin. Source
 * url:
 * https://github.com/cucumber/cucumber/blob/master/gherkin/java/src/main/java/gherkin/GherkinDialectProvider.java.
 * 
 * <p>
 * Gherkin source is licensed under the MIT License
 *
 */
@SuppressWarnings("unchecked")
public class GherkinDialectManager {
    private static final Logger LOG = Logger.getLogger(GherkinDialectManager.class.getName());
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String GHERKIN_LANGUAGES_JSON_URL = "https://github.com/cucumber/cucumber/blob/master/gherkin/gherkin-languages.json";
    private static final String GHERKIN_LANGUAGES_PATH = "gherkin-languages.json";

    private static GherkinDialect currentDialect;
    private static Map<String, Map<String, List<String>>> dialects;
    private static String language;

    static {
        Gson gson = new Gson();
        try {
            Reader d = new InputStreamReader(GherkinDialectManager.class.getResourceAsStream(GHERKIN_LANGUAGES_PATH),
                    "UTF-8");
            dialects = gson.fromJson(d, Map.class);
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.SEVERE, "Unable to parse Gherkin languages file. Either the file is missing or corrupted.", e);
        }
    }

    public static GherkinDialect getDialect() {
        return currentDialect;
    }

    public static String getDefaultLanguage() {
        return DEFAULT_LANGUAGE;
    }

    public static String getLanguage() {
        if (language == null || language.isEmpty())
            language = DEFAULT_LANGUAGE;
        return language;
    }

    /**
     * Sets/changes the default language
     * 
     * @param lang
     *            A valid dialect from
     *            https://github.com/cucumber/cucumber/blob/master/gherkin/gherkin-languages.json
     * 
     * @throws UnsupportedEncodingException
     *             Thrown if the language is one of the supported language from
     *             https://github.com/cucumber/cucumber/blob/master/gherkin/gherkin-languages.json
     */
    public static void setLanguage(String lang) throws UnsupportedEncodingException {
        language = lang;
        Map<String, List<String>> map = dialects.get(GherkinDialectManager.language);
        if (map == null)
            throw new UnsupportedEncodingException("Invalid language [" + language
                    + "]. See list of supported languages: " + GHERKIN_LANGUAGES_JSON_URL);
        currentDialect = new GherkinDialect(language, map);
    }
}