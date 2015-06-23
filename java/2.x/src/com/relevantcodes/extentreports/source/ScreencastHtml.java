package com.relevantcodes.extentreports.source;

public class ScreencastHtml {
    public static String getSource(String screencastPath) {
        return "<video id='video' width='50%' controls>" +
        			"<source src='file://" + screencastPath +"'>" +
        			"Your browser does not support the video tag." + 
        		"</video>";
    }
}
