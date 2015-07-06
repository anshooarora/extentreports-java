package com.relevantcodes.extentreports.model;

public abstract class TestAttribute {
	protected String name;
	
	public String getName() {
		return name;
	}
	
	protected TestAttribute(String name) {
		this.name = name;
	}
}
