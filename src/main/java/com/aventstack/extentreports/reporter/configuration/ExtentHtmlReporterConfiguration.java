package com.aventstack.extentreports.reporter.configuration;

import com.aventstack.extentreports.ResourceCDN;

/**
 * Defines configuration settings for the HTML reporter
 */
public class ExtentHtmlReporterConfiguration extends BasicFileConfiguration implements IReporterConfiguration {

    private boolean chartVisibileOnOpen;
    
    private Protocol protocol;
    private ChartLocation chartLocation;
    private ResourceCDN resourceCDN;    
    /**
     * Sets the protocol of accessing CSS/JS resources from CDN
     * 
     * <p>
     * Default protocol value: HTTPS
     * </p>
     * 
     * @param protocol Protocol, HTTPS or HTTP
     */
    public void setProtocol(Protocol protocol) {
        usedConfigs.put("protocol", String.valueOf(protocol).toLowerCase());
        this.protocol = protocol; 
    }
    public Protocol getProtocol() {return protocol; }

    /**
     * For {@link com.aventstack.extentreports.reporter.ExtentHtmlReporter}, sets the location of charts 
     * 
     * @param chartLocation Location
     */
    public void setTestViewChartLocation(ChartLocation chartLocation) {
        usedConfigs.put("chartLocation", String.valueOf(chartLocation).toLowerCase());
        this.chartLocation = chartLocation; 
    }
    public ChartLocation getTestViewChartLocation() {return chartLocation; }

    /**
     * For {@link com.aventstack.extentreports.reporter.ExtentHtmlReporter}, sets the configuration to allow viewing or hiding charts on report open
     * 
     * @param chartVisibilityOnOpen true to view charts on report open, false to hide
     */
    public void setChartVisibilityOnOpen(boolean chartVisibilityOnOpen) { 
        usedConfigs.put("chartVisibilityOnOpen", String.valueOf(chartVisibilityOnOpen).toLowerCase());
        this.chartVisibileOnOpen = chartVisibilityOnOpen; 
    }   
    public boolean getChartVisibilityOnOpen() { return chartVisibileOnOpen; }
    
    public void setResourceCDN(ResourceCDN cdn) {
        usedConfigs.put("cdn", String.valueOf(cdn).toLowerCase());
        resourceCDN = cdn;
    }
    public ResourceCDN getResourceCDN() {
        return resourceCDN;
    }
    
}
