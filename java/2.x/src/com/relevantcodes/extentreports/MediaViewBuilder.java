/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.ArrayList;

import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.ObjectEmbedHtml;

class MediaViewBuilder {
    public static String getSource(ArrayList<?> mediaList, String type) {
        String src = "";
        
        if (mediaList == null || mediaList.size() == 0) {
            src = ObjectEmbedHtml.getFullWidth()
            		.replace(ExtentFlag.getPlaceHolder("objectViewValue"), "No media was embed for the tests in this report.")
            		.replace(ExtentFlag.getPlaceHolder("objectViewNull"), ExtentFlag.getPlaceHolder("objectViewNull" + type));
            
            return src;
        }
        
        for (Object sc : mediaList) {
            src += ObjectEmbedHtml.getColumn();
            
            if (sc instanceof ScreenCapture) {
                src = src.replace(ExtentFlag.getPlaceHolder("objectViewParam"), ((ScreenCapture) sc).testName)
                    .replace(ExtentFlag.getPlaceHolder("objectViewValue"), ((ScreenCapture) sc).src);
            }
            
            if (sc instanceof Screencast) {
                src = src.replace(ExtentFlag.getPlaceHolder("objectViewParam"), ((Screencast) sc).testName)
                    .replace(ExtentFlag.getPlaceHolder("objectViewValue"), ((Screencast) sc).src);
            }
        }
        
        return src;
    }
}
