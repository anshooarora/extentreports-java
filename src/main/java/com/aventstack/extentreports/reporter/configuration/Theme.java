package com.aventstack.extentreports.reporter.configuration;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Available themes for the HTML reporter
 */
@Getter
public enum Theme {
    @SerializedName(value = "standard", alternate = {"STANDARD"})
    STANDARD("standard"), 
    @SerializedName(value = "dark", alternate = {"DARK"})
    DARK("dark");

    private final String name;

    Theme(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }
}