package com.relevantcodes.extentreports.source;

public class ScreencastHtml {
    public static String getSource(String screencastPath) {
        return "<video id='video' src='file://" + screencastPath +"' width='50%' controls />";
    }
}
