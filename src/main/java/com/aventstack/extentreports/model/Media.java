package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Media implements Serializable, BaseEntity {
    private static final long serialVersionUID = 5428859443090457608L;

    private String path;
    private String title;
    private String resolvedPath;
}
