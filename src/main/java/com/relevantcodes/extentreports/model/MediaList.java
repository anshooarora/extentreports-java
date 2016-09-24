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

public class MediaList implements Serializable {
    
    private static final long serialVersionUID = -6657968172160749394L;
    
    private ArrayList<ScreenCapture> screenCaptureList;
    private ArrayList<Screencast> screencastList;
    
    public ArrayList<ScreenCapture> getScreenCaptureList() {
        return screenCaptureList;
    }
    
    public void setScreenCaptureList(ArrayList<ScreenCapture> screenCaptureList) {
        this.screenCaptureList = screenCaptureList;
    }
    
    public void setScreenCapture(ScreenCapture sc) {
        screenCaptureList.add(sc);
    }
    
    public ArrayList<Screencast> getScreencastList() {
        return screencastList;
    }
    
    public void setScreencastList(ArrayList<Screencast> screencastList) {
        this.screencastList = screencastList;
    }
    
    public void setScreencast(Screencast sc) {
        screencastList.add(sc);
    }

    public MediaList() {
        screenCaptureList = new ArrayList<ScreenCapture>();
        screencastList = new ArrayList<Screencast>();
    }
}
