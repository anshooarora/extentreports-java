package com.aventstack.extentreports.reporter.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class InteractiveReporterConfig extends FileReporterConfig {
    @Builder.Default
    private Protocol protocol = Protocol.HTTP;
    @Builder.Default
    private Theme theme = Theme.STANDARD;
    @Builder.Default
    private boolean timelineEnabled = true;
    @Builder.Default
    private String css = "";
    @Builder.Default
    private String js = "";
}
