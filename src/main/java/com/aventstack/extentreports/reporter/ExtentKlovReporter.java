package com.aventstack.extentreports.reporter;

import java.io.IOException;

import com.aventstack.extentreports.observer.ReportObserver;
import com.aventstack.extentreports.observer.entity.ReportEntity;
import com.aventstack.extentreports.reporter.klov.KlovReporterClient;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtentKlovReporter implements ReportObserver<ReportEntity> {
    private final KlovReporterClient client;
    private Disposable disposable;

    /**
     * Initializes the KlovReporter with project and report names
     * 
     * @param url
     *            Url string
     * @param projectName
     *            Name of the project
     * @param reportName
     *            Name of the report
     */
    public ExtentKlovReporter(String url, String projectName, String reportName) {
        client = new KlovReporterClient(url, projectName, reportName);
    }

    /**
     * Initializes the KlovReporter with the project name
     * 
     * @param projectName
     *            Name of the project
     */
    public ExtentKlovReporter(String url, String projectName) {
        this(url, projectName, null);
    }

    @Override
    public Observer<ReportEntity> getReportObserver() {
        return new Observer<ReportEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ReportEntity value) {
                try {
                    client.flush(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };
    }
}
