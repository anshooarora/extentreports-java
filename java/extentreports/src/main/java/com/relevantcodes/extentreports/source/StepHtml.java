/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.source;

public class StepHtml {
    public static String getSource(int cols) {
        if (cols == 3) {
            return "<table><tr>" +
                        "<td></td>" +
                        "<td class='status'><i class='fa'></i></td>" +
                        "<td class='step-details'></td>" +
                    "</tr></table>";
            
        }
        
        return "<table><tr>" +
                    "<td></td>" +
                    "<td class='status'><i class='fa'></i></td>" +
                    "<td class='step-name'></td>" +
                    "<td class='step-details'></td>" +
                "</tr></table>";
    }
}
