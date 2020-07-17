package com.aventstack.extentreports.gherkin.model;

import java.io.Serializable;

public class But implements IGherkinFormatterModel, Serializable {
    private static final long serialVersionUID = 3420514631996827220L;
    private static final String VALUE = "But";

    public static String getGherkinName() {
        return VALUE;
    }

    @Override
    public String toString() {
        return getGherkinName();
    }
}
