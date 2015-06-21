package com.relevantcodes.extentreports.source;

import java.util.HashMap;

import com.relevantcodes.extentreports.LogStatus;

public class Icon {
    private static HashMap<LogStatus, String> map = new HashMap<LogStatus, String>();
    
    public static void override(LogStatus status, String icon) {
        map.put(status, icon);
    }
    
    public static String getIcon(LogStatus status) {
        if (map.containsKey(status))
            return map.get(status);
        
        String s = status.toString().toLowerCase();
        
        if (s.equals("fail")) { return "times-circle-o"; }
        if (s.equals("error")) { return "exclamation-circle"; }
        if (s.equals("fatal")) { return "exclamation-circle"; }
        if (s.equals("pass")) { return "check-circle-o"; }
        if (s.equals("info")) { return "info-circle"; }
        if (s.equals("warning")) { return "warning"; }
        if (s.equals("skip")) { return "chevron-circle-right"; }

        return "question";
    }
}
