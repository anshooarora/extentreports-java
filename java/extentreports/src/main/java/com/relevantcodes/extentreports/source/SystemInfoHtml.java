/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.source;

public class SystemInfoHtml {
    public static String getColumn() {
        return "<div class='col l2 m4 s12'>" +
                    "<div class='card'>" +
                        "<span class='panel-name'><!--%%SYSTEMINFOPARAM%%--></span>" +
                        "<span class='panel-lead'><!--%%SYSTEMINFOVALUE%%--></span>" +
                    "</div>" +
                "</div>";
    }
}
