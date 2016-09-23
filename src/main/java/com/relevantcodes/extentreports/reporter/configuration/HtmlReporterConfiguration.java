package com.relevantcodes.extentreports.reporter.configuration;

public class HtmlReporterConfiguration extends BasicFileConfiguration implements IReporterConfiguration {

    boolean chartVisibileOnOpen;
    
    Protocol protocol;
    ChartLocation chartLocation;
    
    public void setProtocol(Protocol protocol) {
        usedConfigs.put("protocol", String.valueOf(protocol).toLowerCase());
        this.protocol = protocol; 
    }
    public Protocol getProtocol() {return protocol; }

    public void setTestViewChartLocation(ChartLocation chartLocation) {
        usedConfigs.put("chartLocation", String.valueOf(chartLocation).toLowerCase());
        this.chartLocation = chartLocation; 
    }
    public ChartLocation getTestViewChartLocation() {return chartLocation; }

    public void setChartVisibilityOnOpen(boolean chartVisibilityOnOpen) { 
        usedConfigs.put("chartVisibilityOnOpen", String.valueOf(chartVisibilityOnOpen).toLowerCase());
        this.chartVisibileOnOpen = chartVisibilityOnOpen; 
    }   
    public boolean getChartVisibilityOnOpen() { return chartVisibileOnOpen; }

}
