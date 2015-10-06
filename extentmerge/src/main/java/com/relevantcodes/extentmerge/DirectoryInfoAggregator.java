/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class DirectoryInfoAggregator implements IAggregator {
	private List<String> directories;
	
	public DirectoryInfoAggregator(List<String> directories) {
		this.directories = directories;
	}
	
	public List<Report> getAggregatedData() {
		if (directories == null) {
			return null;
		}
		
		File file;
		List<String> files = new ArrayList<String>();
	
		for (String dirPath : directories) {		
			file = new File(dirPath);
			
			if (file.exists()) {
				File[] listing = file.listFiles();
				
				if (listing != null) {
					for (File f : listing) {
						files.add(f.getPath());
					}
				}
			}
		}
		
		List<Report> reportList = new ArrayList<Report>();

		FileInfoAggregator fileAggregator = new FileInfoAggregator(files);
		List<Report> list = fileAggregator.getAggregatedData();
		
		if (list != null) {
			reportList.addAll(list);
		}
		
		DatabaseInfoAggregator dbAggregator = new DatabaseInfoAggregator(files);
		list = dbAggregator.getAggregatedData();
		
		if (list != null) {
			reportList.addAll(list);
		}
		
		return reportList;
	}
}