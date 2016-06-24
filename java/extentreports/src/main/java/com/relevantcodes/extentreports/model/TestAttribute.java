/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.io.Serializable;

public abstract class TestAttribute implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8574995780295912718L;
	protected String name;
    
    public String getName() {
        return name;
    }
    
    protected TestAttribute(String name) {
        this.name = name;
    }
}
