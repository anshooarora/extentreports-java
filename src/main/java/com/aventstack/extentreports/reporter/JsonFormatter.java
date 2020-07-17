package com.aventstack.extentreports.reporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.observer.ReportObserver;
import com.aventstack.extentreports.observer.entity.ReportEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class JsonFormatter extends AbstractFileReporter implements ReportObserver<ReportEntity> {
    private static final String FILE_NAME = "extent.json";

    public JsonFormatter(File file) {
        super(file);
    }

    public JsonFormatter(String filePath) {
        super(new File(filePath));
    }

    @Override
    public Observer<ReportEntity> getReportObserver() {
        return new Observer<ReportEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ReportEntity value) {
                flush(value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void flush(ReportEntity value) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        final String filePath = getFile().isDirectory()
                ? getFile().getAbsolutePath()
                        + PATH_SEP + FILE_NAME
                : getFile().getAbsolutePath();
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            List<Test> list = value.getReport().getTestList();
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
