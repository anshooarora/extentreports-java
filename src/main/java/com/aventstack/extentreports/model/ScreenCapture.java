package com.aventstack.extentreports.model;

public class ScreenCapture extends Media { 

    private static final long serialVersionUID = -3413285738443448335L;

    public String getSource() {
        if (getBase64String() != null)
            return "<a href='" + getScreenCapturePath() + "' data-featherlight='image'><img src='" + getScreenCapturePath() + "' /></a>";

        return "<img data-featherlight='" + getScreenCapturePath() + "' width='10%' src='" + getScreenCapturePath() + "' data-src='" + getScreenCapturePath() + "'>";
    }
    
    public String getSourceWithIcon() {
        return "<a href='#' data-featherlight='" + getScreenCapturePath() + "'><i class='material-icons'>photo</i></a>";
    }
    
    private String getScreenCapturePath() {
        String path = getPath() != null ? getPath() : getBase64String();
        return path;
    }

}
