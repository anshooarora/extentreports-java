package com.relevantcodes.extentmerge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Extractor {
	private List<String> fileList;
	private List<String> testSource;
	private List<String> quickTestSummarySource;
	
	public String getTestSource() {
		return getMergedSrc(testSource);
	}
	
	public String getQuickTestSummarySource() {
		return getMergedSrc(quickTestSummarySource);
	}
	
	private String getMergedSrc(List<String> listSource) {
		String merged = "";
		
		for (String src : listSource) {
			merged += src;
		}
		
		return merged;
	}
	
	private void extract() throws IOException {
		testSource = new ArrayList<String>();
		quickTestSummarySource = new ArrayList<String>();
		
		for (String filePath : fileList) {
			File input = new File(filePath);
			Document doc = Jsoup.parse(input, "UTF-8");
			Element testList = doc.getElementsByClass("test-list").first();
			Element quickTestSummaryList = doc.select("#tests-quick-view tbody").first();
			
			testSource.add(testList.html());
			quickTestSummarySource.add(quickTestSummaryList.html());
		}
	}
	
	public Extractor(String[] files) throws IOException {
		fileList = new ArrayList<String>();
		
		for (String file : files) {
			fileList.add(file);
		}
		
		extract();
	}
}
