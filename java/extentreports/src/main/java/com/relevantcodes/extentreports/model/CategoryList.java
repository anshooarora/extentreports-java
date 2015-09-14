/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

public class CategoryList {
    private ArrayList<String> categoryList;
    
    public void setCategoryList(ArrayList<String> categoryList) {
    	this.categoryList = categoryList;
    }
    
    public void setCategory(String category) {
    	categoryList.add(category);
    }
    
    public ArrayList<String> getCategoryList() {
    	return categoryList;
    }
    
    public CategoryList() {
        categoryList = new ArrayList<String>();
    }
}
