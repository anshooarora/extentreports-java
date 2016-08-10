package com.relevantcodes.extentreports.viewdefs;

import java.util.HashMap;

import com.relevantcodes.extentreports.Status;

public class Icon {

    private static HashMap<Status, String> map = new HashMap<>();
    
    public void override(Status status, String icon) {
        map.put(status, icon);
    }
    
    public String getIcon(Status status) {
        if (map.containsKey(status))
            return map.get(status);

        String s = status.toString().toLowerCase();

        switch (s) {
            case "fail":
                return "cancel";
            case "fatal":
                return "cancel";
            case "error":
                return "error";
            case "warning":
                return "warning";
            case "skip":
                return "redo";
            case "pass":
                return "check_circle";
            case "info":
                return "info_outline";
            default:
                return "help";
        }
    }

}
