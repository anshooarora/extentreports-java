/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.source;

public class ImageHtml {
    public static String getSource(String imgPath) {
        return "<img class='report-img' data-featherlight='file:///" + imgPath + "' src='file:///" + imgPath + "' />";
    }
}
