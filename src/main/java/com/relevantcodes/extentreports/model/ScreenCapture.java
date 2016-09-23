package com.relevantcodes.extentreports.model;

public class ScreenCapture extends Media { 

    private static final long serialVersionUID = -8333840002805760739L;

    public String getSource() {
        return "<img data-featherlight='" + getPath() + "' width='25%' src='" + getPath() + "'>";
    }
}
