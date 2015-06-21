package com.relevantcodes.extentreports.source;

public class ObjectEmbedHtml {
    public static String getColumn() {
        return "<div class='col l3 m6 s12'>" +
                    "<div class='card-panel'>" +
                        "<span class='panel-name'><!--%%OBJECTVIEWPARAM%%--></span>" +
                        "<span class='panel-object'><!--%%OBJECTVIEWVALUE%%--></span>" +
                    "</div>" +
                "</div>";
    }
    
    public static String getFullWidth() {
        return "<!--%%OBJECTVIEWNULL%%--><div class='col s12'>" +
                "<div class='card-panel'>" +
                    "<span class='panel-lead'><!--%%OBJECTVIEWVALUE%%--></span>" +
                "</div>" +
            "</div><!--%%OBJECTVIEWNULL%%-->";
    }
}
