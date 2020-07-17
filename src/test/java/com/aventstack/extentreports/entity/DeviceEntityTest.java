package com.aventstack.extentreports.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.Device;

public class DeviceEntityTest {

	@Test
	public void deviceName() {
		final String name = "DeviceName";
		Device author = new Device(name);
		Assert.assertEquals(author.getName(), name);
	}
	
}
