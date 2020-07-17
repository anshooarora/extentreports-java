package com.aventstack.extentreports.view;

import java.util.Arrays;

public enum Ico {
    INFO("Info", "info"), PASS("Pass", "check"), SKIP("Skip", "long-arrow-right"), WARNING("Warning",
            "warning"), FAIL("Fail", "times"), RETRY("Retry", "reset");

    private final String name;
    private final String ico;

    Ico(String name, String ico) {
        this.name = name;
        this.ico = ico;
    }

    public String getName() {
        return name;
    }

    public String getIco() {
        return ico;
    }

    public static Ico ico(String name) {
        return Arrays.stream(values())
                .filter(x -> x.name.equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return ico;
    }
}
