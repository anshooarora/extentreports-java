package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenCapture extends Media implements Serializable {
    private static final long serialVersionUID = -3047762572007885369L;

    private String base64;

    @Builder
    public ScreenCapture(String path, String title, String resolvedPath, String base64) {
        super(path, title, resolvedPath);
        this.base64 = base64;
    }
}
