package com.aventstack.extentreports.observer;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.observer.entity.ReportEntity;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ReportEntityObserverTest {
    private Disposable disp;
    private ReportEntity entity;

    @Test
    public void disposableNonNull() {
        ExtentReports extent = new ExtentReports();
        Assert.assertNull(disp);
        Assert.assertNull(entity);
        extent.attachReporter(new TestReporter());
        Assert.assertNotNull(disp);
        Assert.assertNull(entity);
        extent.flush();
        Assert.assertNotNull(disp);
        Assert.assertNotNull(entity);
    }

    @SuppressWarnings("rawtypes")
    private class TestReporter implements ReportObserver {
        @Override
        public Observer<ReportEntity> getReportObserver() {
            return new Observer<ReportEntity>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disp = d;
                }

                @Override
                public void onNext(ReportEntity value) {
                    entity = value;
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }
            };
        }
    }
}
