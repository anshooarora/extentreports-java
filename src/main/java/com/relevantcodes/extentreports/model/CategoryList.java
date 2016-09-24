/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CategoryList implements Serializable {

    private static final long serialVersionUID = -5196084944683521927L;
    
    private ArrayList<String> categoryList;
    
    public void setCategoryList(ArrayList<String> categoryList) {
    	this.categoryList = categoryList;
    }
    
    public void setCategory(String category) {
    	categoryList.add(category);
    	Collections.sort(categoryList);
    }
    
    public ArrayList<String> getCategoryList() {
    	return categoryList;
    }
    
    public CategoryList() {
        categoryList = new ArrayList<String>();
    }
}
