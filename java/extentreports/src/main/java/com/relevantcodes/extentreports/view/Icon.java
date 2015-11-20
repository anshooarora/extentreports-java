/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.view;

import java.util.HashMap;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class Icon {
    private NetworkMode networkMode;
	private static HashMap<LogStatus, String> map = new HashMap<LogStatus, String>();
    
    public void override(LogStatus status, String icon) {
        map.put(status, icon);
    }
    
    public String getIcon(LogStatus status) {
        if (map.containsKey(status))
            return map.get(status);

        if (status == null) {
        	status = LogStatus.UNKNOWN;
        }
        
        String s = status.toString().toLowerCase();
        
        if (networkMode == NetworkMode.OFFLINE) {
	        if (s.equals("fail")) { return "fa fa-times-circle-o"; }
	        if (s.equals("error")) { return "fa fa-exclamation-circle"; }
	        if (s.equals("fatal")) { return "fa fa-exclamation-circle"; }
	        if (s.equals("pass")) { return "fa fa-check-circle-o"; }
	        if (s.equals("info")) { return "fa fa-info-circle"; }
	        if (s.equals("warning")) { return "fa fa-warning"; }
	        if (s.equals("skip")) { return "fa fa-chevron-circle-right"; }
	        
	        return "question";
        }
        else {
        	if (s.equals("fail")) { return "mdi-navigation-cancel"; }
        	if (s.equals("fatal")) { return "mdi-navigation-cancel"; }
        	if (s.equals("error")) { return "mdi-alert-error"; }
        	if (s.equals("warning")) { return "mdi-alert-warning"; }
	        if (s.equals("pass")) { return "mdi-action-check-circle"; }
	        if (s.equals("info")) { return "mdi-action-info-outline"; }
	        if (s.equals("skip")) { return "mdi-av-skip-next"; }
	        
	        return "mdi-action-help";
        }
    }
    
    public Icon(NetworkMode networkMode) {
    	this.networkMode = networkMode;
    }
    
    public Icon() { }
}
