package com.aventstack.extentreports.config;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigStoreTest {
	private ConfigStore store = new ConfigStore();

	@Test
	public void duplicateConfig() {
		store.addConfig("config", "value1");
		Assert.assertEquals(store.getConfig("config"), "value1");
		store.addConfig("config", "value2");
		Assert.assertEquals(store.getConfig("config"), "value2");
	}

	@Test
	public void containsConfig() {
		store.addConfig("config", "value1");
		Assert.assertTrue(store.containsConfig("config"));
		Assert.assertFalse(store.containsConfig("config2"));
	}

	@Test
	public void removeConfig() {
		store.addConfig("config", "value1");
		Assert.assertTrue(store.containsConfig("config"));
		store.removeConfig("config");
		Assert.assertFalse(store.containsConfig("config"));
	}

	@Test
	public void configValueTest() {
		store.addConfig("c", "v");
		store.addConfig("k", "z");
		Assert.assertTrue(store.getConfig("c").equals("v"));
		Assert.assertTrue(store.getConfig("k").equals("z"));
	}

	@Test
	public void extendConfigWithStore() {
		ConfigStore store1 = new ConfigStore();
		store1.addConfig("config1", "value1");
		ConfigStore store2 = new ConfigStore();
		store2.addConfig("config2", "value2");
		store1.extendConfig(store2);
		Assert.assertTrue(store1.containsConfig("config1"));
		Assert.assertTrue(store1.containsConfig("config2"));
		Assert.assertTrue(store2.containsConfig("config2"));
		Assert.assertFalse(store2.containsConfig("config1"));
	}

	@Test
	public void extendConfigWithMap() {
		ConfigStore store1 = new ConfigStore();
		store1.addConfig("config1", "value1");
		ConfigStore store2 = new ConfigStore();
		store2.addConfig("config2", "value2");
		store1.extendConfig(store2.getStore());
		Assert.assertTrue(store1.containsConfig("config1"));
		Assert.assertTrue(store1.containsConfig("config2"));
		Assert.assertTrue(store2.containsConfig("config2"));
		Assert.assertFalse(store2.containsConfig("config1"));
	}

	@Test
	public void configEmpty() {
		ConfigStore store = new ConfigStore();
		Assert.assertTrue(store.isEmpty());
		store.addConfig("config1", "value1");
		Assert.assertFalse(store.isEmpty());
	}
}
