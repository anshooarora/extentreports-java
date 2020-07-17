package com.aventstack.extentreports.reporter.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(builderMethodName = "with")
public abstract class FileReporterConfig extends AbstractConfiguration {
    @Builder.Default
    private String encoding = "UTF-8";
    @Builder.Default
    private String documentTitle = "";
}
