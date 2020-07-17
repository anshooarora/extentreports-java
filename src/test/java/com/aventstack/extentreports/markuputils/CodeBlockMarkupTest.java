package com.aventstack.extentreports.markuputils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CodeBlockMarkupTest {

	@Test
	public void nullCodeBlockContent() {
		Markup m = MarkupHelper.createCodeBlock(null);
		Assert.assertEquals(m.getMarkup(), "");
	}

	@Test
	public void xmlCodeBlock() {
		String xml = "<tag>value</tag>";
		Markup m = MarkupHelper.createCodeBlock(xml);
		Assert.assertTrue(m.getMarkup().contains(xml));
	}

	@Test
	public void xmlCodeBlockWithLang() {
		String xml = "<tag>value</tag>";
		Markup m = MarkupHelper.createCodeBlock(xml, CodeLanguage.XML);
		Assert.assertTrue(m.getMarkup().contains(xml));
	}

	@Test
	public void jsonCodeBlock() {
		String json = "{ 'key': 'value' }";
		Markup m = MarkupHelper.createCodeBlock(json);
		Assert.assertTrue(m.getMarkup().contains(json));
	}

	@Test(priority = 1)
	public void jsonCodeBlockWithLang() {
		String json = "{ 'key': 'value' }";
		Markup m = MarkupHelper.createCodeBlock(json, CodeLanguage.JSON);
		Assert.assertTrue(m.getMarkup().contains(json));
		Assert.assertTrue(m.getMarkup().contains("jsonTreeCreate"));
		Assert.assertTrue(m.getMarkup().contains("<script>"));
		Assert.assertTrue(m.getMarkup().contains("</script>"));
	}

	@Test(priority = 2)
	public void jsonCodeBlockWithLangMultiple() {
		String json = "{ 'key': 'value' }";
		Markup m = MarkupHelper.createCodeBlock(json, CodeLanguage.JSON);
		Assert.assertTrue(m.getMarkup().contains(json));
		Assert.assertTrue(m.getMarkup().contains("jsonTreeCreate"));
		Assert.assertTrue(m.getMarkup().contains("<script>"));
		Assert.assertTrue(m.getMarkup().contains("</script>"));
		m = MarkupHelper.createCodeBlock(json, CodeLanguage.JSON);
		Assert.assertTrue(m.getMarkup().contains(json));
		Assert.assertTrue(m.getMarkup().contains("jsonTreeCreate"));
		Assert.assertTrue(m.getMarkup().contains("<script>"));
		Assert.assertTrue(m.getMarkup().contains("</script>"));
	}

}
