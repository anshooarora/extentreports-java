/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.view;

public class ObjectEmbedHtml {
    public static String getColumn() {
        return "<div class='col l2 m4 s6'>" +
                    "<div class='card-panel'>" +
                        "<span class='panel-name linked'></span>" +
                        "<span class='panel-object'></span>" +
                    "</div>" +
                "</div>";
    }
    
    public static String getFullWidth() {
        return "<div class='col s12'>" +
                "<div class='card-panel'>" +
                    "<span class='panel-lead'></span>" +
                "</div>" +
            "</div>";
    }
}
