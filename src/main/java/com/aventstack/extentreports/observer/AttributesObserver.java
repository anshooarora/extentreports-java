package com.aventstack.extentreports.observer;

import com.aventstack.extentreports.observer.entity.AttributeEntity;
import com.aventstack.extentreports.observer.entity.ObservedEntity;

import io.reactivex.rxjava3.core.Observer;

public interface AttributesObserver<T extends ObservedEntity> extends ExtentObserver<T> {
    Observer<AttributeEntity> getAttributesObserver();
}
