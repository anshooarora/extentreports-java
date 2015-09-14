/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.support.RegexMatcher;

class SourceBuilder {
    public static String buildRegex(String source, String[] placeholders, String[] values) {
        for (int ix = 0; ix < placeholders.length; ix++) {
            
            String matcher = placeholders[ix] + ".*" + placeholders[ix];
            String match = RegexMatcher.getNthMatch(source, matcher, 0);
            
            source = (match == null) 
                    ? source.replace(placeholders[ix], values[ix])
                            : source.replace(match, matcher.replace(".*", values[ix]));
        }
        
        return source;
    }
    
    public static String build(String source, String[] placeholders, String[] values) {
        for (int ix = 0; ix < placeholders.length; ix++) {            
            source = source.replace(placeholders[ix], values[ix]);
        }
        
        return source;
    }
}
