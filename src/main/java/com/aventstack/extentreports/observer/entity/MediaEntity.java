package com.aventstack.extentreports.observer.entity;

import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MediaEntity implements ObservedEntity {
    private Media media;
    private Test test;
    private Log log;
}
