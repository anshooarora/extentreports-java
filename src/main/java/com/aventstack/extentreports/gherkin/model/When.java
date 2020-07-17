package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class When implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = 8337259741948416898L;
    private static final String VALUE = "When";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
