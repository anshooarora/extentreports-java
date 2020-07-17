package com.aventstack.extentreports.observer;

import com.aventstack.extentreports.observer.entity.ObservedEntity;
import com.aventstack.extentreports.observer.entity.TestEntity;

import io.reactivex.rxjava3.core.Observer;

public interface TestObserver<T extends ObservedEntity> extends ExtentObserver<T> {
    Observer<TestEntity> getTestObserver();
}
