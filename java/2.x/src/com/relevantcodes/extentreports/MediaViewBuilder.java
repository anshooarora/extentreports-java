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
        String source = "";
        String[] flags = { 
        		ExtentFlag.getPlaceHolder("objectViewValue"), 
        		ExtentFlag.getPlaceHolder("objectViewNull") 
        };
        String[] values;
        
        if (mediaList == null || mediaList.size() == 0) {
        	values = new String[] { 
        			"No media was embedded for the tests in this report.", 
        			ExtentFlag.getPlaceHolder("objectViewNull" + type) 
        	};
        	
        	source = SourceBuilder.build(ObjectEmbedHtml.getFullWidth(), flags, values);
            
            return source;
        }
        
        flags = new String[] {
        		ExtentFlag.getPlaceHolder("objectViewParam"), 
        		ExtentFlag.getPlaceHolder("objectViewValue")
        };
        
        for (Object sc : mediaList) {
            source += ObjectEmbedHtml.getColumn();
            
            if (sc instanceof ScreenCapture) {
            	values = new String[] { 
            			((ScreenCapture) sc).testName,
            			((ScreenCapture) sc).src
            	};
            	
            	source = SourceBuilder.build(source, flags, values);
            }
            
            if (sc instanceof Screencast) {
            	values = new String[] { 
            			((Screencast) sc).testName,
            			((Screencast) sc).src
            	};
            	
            	source = SourceBuilder.build(source, flags, values);
            }
        }
        
        return source;
    }
}
