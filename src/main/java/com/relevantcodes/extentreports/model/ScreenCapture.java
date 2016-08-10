package com.relevantcodes.extentreports.model;

public class ScreenCapture extends Media { 
    public String getSource() {
        return "<img data-featherlight='" + getPath() + "' width='25%' src='" + getPath() + "'>";
    }
}
