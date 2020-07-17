package com.aventstack.extentreports.markuputils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LabelMarkupTest {

	@Test
	public void labelWithNullText() {
		Markup m = MarkupHelper.createLabel(null, ExtentColor.TRANSPARENT);
		Assert.assertEquals(m.getMarkup(), "");
	}

	@Test
	public void labelWithEmptyText() {
		Markup m = MarkupHelper.createLabel("", ExtentColor.TRANSPARENT);
		Assert.assertEquals(m.getMarkup(), "");
	}

	@Test
	public void labelWithText() {
		String text = "Extent";
		Markup m = MarkupHelper.createLabel(text, ExtentColor.TRANSPARENT);
		Assert.assertTrue(m.getMarkup().contains(text));
	}
}
