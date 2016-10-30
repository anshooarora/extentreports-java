package com.aventstack.extentreports;

import com.aventstack.extentreports.model.Media;

public class MediaEntityModelProvider {

	private Media m;
	
	public MediaEntityModelProvider(Media m) {
		this.m = m;
	}
	
	public Media getMedia() {
		return m;
	}
	
}
