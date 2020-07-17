package com.aventstack.extentreports.reporter.klov.entity;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ScreenCapture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KlovMedia {
    private String test;
    private String log;
    private String project;
    private String report;
    private Integer sequence;
    private String testName;
    private String path;
    private String mediaType;
    private String ext;
    private String base64String;

    public KlovMedia(Media m) {
        transform((ScreenCapture) m);
    }

    private void transform(ScreenCapture sc) {
        
    }
}
