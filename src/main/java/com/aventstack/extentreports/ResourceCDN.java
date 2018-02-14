package com.aventstack.extentreports;

/**
 * Allows selecting a CDN/resource loader for your HTML Reporter
 * 
 * Note: Some hosts do not allow loading resources via HTTPS protocol:
 * 
 * <ul>
 *  <li>ExtentReports</li>
 * </ul>
 * 
 */
public enum ResourceCDN {
    GITHUB,
    EXTENTREPORTS
}
