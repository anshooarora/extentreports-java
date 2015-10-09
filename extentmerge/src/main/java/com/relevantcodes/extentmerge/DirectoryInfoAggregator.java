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
        
        List<String> files = new ArrayList<String>();
    
        for (String dirPath : directories) {
            List<String> list = getFilesRecursively(dirPath);
            
            if (list.size() > 0) {
                files.addAll(list);
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
    
    List<String> files = new ArrayList<String>();
    
    private List<String> getFilesRecursively(String directoryPath) {
        File dir = new File(directoryPath);

        if (dir.exists()) {
            File[] listing = dir.listFiles();
            
            if (listing != null && listing.length > 0) {
                for (File file : listing) {
                    if (file.isDirectory()) {
                        getFilesRecursively(file.getPath());
                    }
                    else {
                        files.add(file.getPath());
                    }
                }
            }
        }
        
        return files;
    }
    
}