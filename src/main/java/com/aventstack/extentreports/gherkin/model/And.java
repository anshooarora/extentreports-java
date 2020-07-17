package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class And implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = 8543289653944756660L;
    private static final String VALUE = "And";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
