/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.view;

public class ScreenshotHtml {
    public static String getSource(String imgPath) {
    	return "<img class='report-img' data-featherlight='file:///" + imgPath + "' src='file:///" + imgPath + "' />";
    }

    /**
     * Creates the image tag with base64 string as image source
     * @param imgSrc Base64 String
     * @return Image tag with base64 string as source
     */
    public static String getBase64Source(String imgSrc) {
        return "<img class='report-img' data-featherlight='<img src=\"" + imgSrc + "\" />' " +
                "src='" + imgSrc + "' />";
    }
}
