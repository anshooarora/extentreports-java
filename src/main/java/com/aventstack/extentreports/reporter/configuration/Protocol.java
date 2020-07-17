package com.aventstack.extentreports.reporter.configuration;

import com.google.gson.annotations.SerializedName;

/**
 * Protocol used to download CDN css/js resources for HTML reports
 */
public enum Protocol {
    @SerializedName(value = "http", alternate = {"HTTP"})
    HTTP, 
    @SerializedName(value = "https", alternate = {"HTTPS"})
    HTTPS
}