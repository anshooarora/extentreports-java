package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class Scenario implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = 7401124129196617280L;
    private static final String VALUE = "Scenario";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
