package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class ScenarioOutline implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = -2058398543903906031L;
    private static final String VALUE = "Scenario Outline";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
