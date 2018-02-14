package com.aventstack.extentreports.model;

public class Screencast extends Media { 

    private static final long serialVersionUID = -3413285738443448335L;

    public String getSource() {
        return "<video id='video' width='50%' controls>" +
                "<source src='file:///" + getPath() +"'>" +
                "Your browser does not support the video tag." + 
            "</video>";
    }
    
    public String getSourceWithIcon() {
        return getSource();
    }
    
}
