/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.view;

public class ScreencastHtml {
    public static String getSource(String screencastPath) {
        return "<video id='video' width='50%' controls>" +
                    "<source src='file:///" + screencastPath +"'>" +
                    "Your browser does not support the video tag." + 
                "</video>";
    }
}
