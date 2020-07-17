package com.aventstack.extentreports.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.Author;

public class AuthorEntityTest {

	@Test
	public void authorName() {
		final String name = "Anshoo";
		Author author = new Author(name);
		Assert.assertEquals(author.getName(), name);
	}
	
}
