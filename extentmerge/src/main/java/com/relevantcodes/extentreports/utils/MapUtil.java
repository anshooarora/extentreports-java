package com.relevantcodes.extentreports.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtil {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Integer> getSortedMap(Map<String, Integer> map) {
		List list = new LinkedList(map.entrySet());
		
	    Collections.sort(list, new Comparator() {
	    	public int compare(Object o1, Object o2) {
	    		return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
	    	}
	     });
	    
	    Map result = new LinkedHashMap();
	    
	    for (Iterator iter = list.iterator(); iter.hasNext();) {
	        Map.Entry entry = (Map.Entry) iter.next();
	        
	        result.put(entry.getKey(), entry.getValue());
	    }
	    
	    return result;
	}
	
}
