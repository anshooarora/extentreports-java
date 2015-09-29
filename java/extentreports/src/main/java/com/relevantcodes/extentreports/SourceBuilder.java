/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

class SourceBuilder {
    public static String build(String source, String[] placeholders, String[] values) {
        for (int ix = 0; ix < placeholders.length; ix++) {            
            source = source.replace(placeholders[ix], values[ix]);
        }
        
        return source;
    }
}
