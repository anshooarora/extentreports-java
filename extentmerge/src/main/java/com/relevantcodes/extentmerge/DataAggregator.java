/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.util.ArrayList;
import java.util.List;

import com.relevantcodes.extentmerge.model.Report;

class DataAggregator implements IAggregator {
	private List<IAggregator> aggregatorList;
	
	public DataAggregator(List<String> directoryList, List<String> htmlFilePathList, List<String> databaseFilePathList) {		
		aggregatorList = new ArrayList<IAggregator>();
		
		aggregatorList.add(new FileInfoAggregator(htmlFilePathList));
		aggregatorList.add(new DirectoryInfoAggregator(directoryList));
		aggregatorList.add(new DatabaseInfoAggregator(databaseFilePathList));
	}
	
	public List<Report> getAggregatedData() {
		List<Report> reportList = new ArrayList<Report>();
		List<Report> list;
		
		for (IAggregator aggregator : aggregatorList) {
			list = aggregator.getAggregatedData();
			
			if (list != null) {
				reportList.addAll(list);
			}
		}
		
		return reportList;
	}	
}
