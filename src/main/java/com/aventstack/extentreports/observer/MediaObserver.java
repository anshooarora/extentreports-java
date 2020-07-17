package com.aventstack.extentreports.observer;

import com.aventstack.extentreports.observer.entity.MediaEntity;
import com.aventstack.extentreports.observer.entity.ObservedEntity;

import io.reactivex.rxjava3.core.Observer;

public interface MediaObserver<T extends ObservedEntity> extends ExtentObserver<T> {
    Observer<MediaEntity> getMediaObserver();
}
