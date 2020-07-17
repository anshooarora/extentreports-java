package com.aventstack.extentreports.observer;

import com.aventstack.extentreports.observer.entity.ObservedEntity;
import com.aventstack.extentreports.observer.entity.ReportEntity;

import io.reactivex.rxjava3.core.Observer;

public interface ReportObserver<T extends ObservedEntity> extends ExtentObserver<T> {
    Observer<ReportEntity> getReportObserver();
}
