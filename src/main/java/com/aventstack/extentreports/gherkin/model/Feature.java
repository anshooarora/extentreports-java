package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class Feature implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = -4719215211721789414L;
    private static final String VALUE = "Feature";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
