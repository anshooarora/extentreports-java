package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class Given implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = 939197985263690070L;
    private static final String VALUE = "Given";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
