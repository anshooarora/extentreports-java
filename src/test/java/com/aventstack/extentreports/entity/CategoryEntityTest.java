package com.aventstack.extentreports.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.Category;

public class CategoryEntityTest {

	@Test
	public void tagName() {
		final String name = "TagName";
		Category tag = new Category(name);
		Assert.assertEquals(tag.getName(), name);
	}
	
}
