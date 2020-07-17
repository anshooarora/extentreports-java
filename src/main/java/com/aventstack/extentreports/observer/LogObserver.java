package com.aventstack.extentreports.observer;

import com.aventstack.extentreports.observer.entity.LogEntity;
import com.aventstack.extentreports.observer.entity.ObservedEntity;

import io.reactivex.rxjava3.core.Observer;

public interface LogObserver<T extends ObservedEntity> extends ExtentObserver<T> {
    Observer<LogEntity> getLogObserver();
}
