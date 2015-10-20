/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.util.List;

import com.relevantcodes.extentmerge.model.Report;

public interface IAggregator {
    List<Report> getAggregatedData();
}
