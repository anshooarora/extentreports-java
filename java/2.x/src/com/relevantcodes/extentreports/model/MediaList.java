/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

public class MediaList {
    public ArrayList<ScreenCapture> screenCapture;
    public ArrayList<Screencast> screencast;
    
    public MediaList() {
        screenCapture = new ArrayList<ScreenCapture>();
        screencast = new ArrayList<Screencast>();
    }
}
