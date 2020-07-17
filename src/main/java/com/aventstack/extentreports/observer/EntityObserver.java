package com.aventstack.extentreports.observer;

import com.aventstack.extentreports.observer.entity.ObservedEntity;

public interface EntityObserver<T extends ObservedEntity>
        extends
            AttributesObserver<T>,
            LogObserver<T>,
            MediaObserver<T>,
            ReportObserver<T>,
            TestObserver<T> {
}
