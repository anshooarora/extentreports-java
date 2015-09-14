/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

public abstract class TestAttribute {
    protected String name;
    
    public String getName() {
        return name;
    }
    
    protected TestAttribute(String name) {
        this.name = name;
    }
}
