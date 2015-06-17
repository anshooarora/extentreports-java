package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

public class MediaList {
	public ArrayList<ScreenCapture> screenCapture;
	public ArrayList<Screencast> screencast;
	
	public MediaList() {
		screenCapture = new ArrayList<ScreenCapture>();
		screencast = new ArrayList<Screencast>();
	}
}
