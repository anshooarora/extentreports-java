package com.relevantcodes.extentreports.mergetool;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class Extractor {
	private String[] files;
	private Queue<String> testSourceList;
	private Queue<String> quickTestSummarySourceList;
	private Queue<String> imagesViewSourceList;
	private Queue<String> systemViewSourceList;
	
	public String getTestSource() {
		return getMergedSource(testSourceList, "test");
	}
	
	public String getQuickTestSummarySource() {
		return getMergedSource(quickTestSummarySourceList, "quickTestSummary");
	}
	
	public String getImagesViewSource() {
		return getMergedSource(imagesViewSourceList, "imagesView");
	}
	
	public String getSystemViewSource() {
		return getMergedSource(systemViewSourceList, "systemInfoView");
	}
	
	private String getMergedSource(Queue<String> queue, String placeHolder) {
		String s = "";
		
		for (String src : queue) {
			s += src;
		}
		
		if (s != "") {
			return s.replace(Extent.getPlaceHolder(placeHolder), "");
		}
		
		return s;
	}
	
	private void extractAndAssignComponents() throws IOException {
		for (String file : files) {
			File input = new File(file);
			
			if (input.exists()) {
				Document doc = Jsoup.parse(input, "UTF-8");
				Element divTests = doc.getElementsByClass("test-list").first();
				
				if (divTests != null) {
					Element divQuickTestSummary = doc.select("#tests-quick-view tbody").first();
					Element divImagesView = doc.select(".images-view > .row").first();
					Element divSystemView = doc.select(".system-view > .row").first();
					
					testSourceList.add(divTests.html());
					quickTestSummarySourceList.add(divQuickTestSummary.html());
					imagesViewSourceList.add(divImagesView.html());
					systemViewSourceList.add(divSystemView.html());					
				}
			}
			else {
				System.out.println("" + file + "" + " not found");
			}
		}
	}
	
	public Extractor(String[] files) throws IOException {
		this.files = files;
		
		testSourceList = new LinkedList<String>();
		quickTestSummarySourceList = new LinkedList<String>();
		imagesViewSourceList = new LinkedList<String>();
		systemViewSourceList = new LinkedList<String>();
		
		extractAndAssignComponents();
	}
}
