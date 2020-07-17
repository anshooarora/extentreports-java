package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class Then implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = 2493591502473169772L;
    private static final String VALUE = "Then";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
