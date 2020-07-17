package com.aventstack.extentreports;

import java.util.List;

import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Device;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.observer.AttributesObserver;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.observer.LogObserver;
import com.aventstack.extentreports.observer.MediaObserver;
import com.aventstack.extentreports.observer.ReportObserver;
import com.aventstack.extentreports.observer.TestObserver;
import com.aventstack.extentreports.observer.entity.AttributeEntity;
import com.aventstack.extentreports.observer.entity.LogEntity;
import com.aventstack.extentreports.observer.entity.MediaEntity;
import com.aventstack.extentreports.observer.entity.ReportEntity;
import com.aventstack.extentreports.observer.entity.TestEntity;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;

@Getter
abstract class ReactiveSubject {
    private final Report report = Report.builder().build();
    private final PublishSubject<ReportEntity> reportSubject = PublishSubject.create();
    private final PublishSubject<TestEntity> testSubject = PublishSubject.create();
    private final PublishSubject<LogEntity> logSubject = PublishSubject.create();
    private final PublishSubject<MediaEntity> mediaSubject = PublishSubject.create();
    private final PublishSubject<AttributeEntity> attribSubject = PublishSubject.create();

    protected ReactiveSubject() {
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected final void attachReporter(List<ExtentObserver> observerList) {
        for (ExtentObserver o : observerList) {
            if (o instanceof ReportObserver)
                reportSubject.subscribe(((ReportObserver) o).getReportObserver());
            if (o instanceof TestObserver)
                testSubject.subscribe(((TestObserver) o).getTestObserver());
            if (o instanceof LogObserver)
                logSubject.subscribe(((LogObserver) o).getLogObserver());
            if (o instanceof MediaObserver)
                mediaSubject.subscribe(((MediaObserver) o).getMediaObserver());
            if (o instanceof AttributesObserver)
                attribSubject.subscribe(((AttributesObserver) o).getAttributesObserver());
        }
    }

    protected void onTestCreated(Test test) {
        testSubject.onNext(TestEntity.builder().test(test).build());
    }

    protected void onTestRemoved(Test test) {
        testSubject.onNext(TestEntity.builder().test(test).removed(true).build());
    }

    protected void onLogCreated(Log log, Test test) {
        logSubject.onNext(LogEntity.builder().log(log).test(test).build());
    }

    protected void onAuthorAssigned(Author x, Test test) {
        attribSubject.onNext(AttributeEntity.builder().author(x).test(test).build());
    }

    protected void onCategoryAssigned(Category x, Test test) {
        attribSubject.onNext(AttributeEntity.builder().category(x).test(test).build());
    }

    protected void onDeviceAssigned(Device x, Test test) {
        attribSubject.onNext(AttributeEntity.builder().device(x).test(test).build());
    }

    protected void onMediaAdded(Media m, Test test) {
        mediaSubject.onNext(MediaEntity.builder().media(m).test(test).build());
    }

    protected void onMediaAdded(Media m, Log log) {
        mediaSubject.onNext(MediaEntity.builder().media(m).log(log).build());
    }

    protected void onFlush() {
        reportSubject.onNext(ReportEntity.builder().report(report).build());
    }
}
