package com.aventstack.extentreports;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.aventstack.extentreports.append.RawEntityConverter;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Device;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.SystemEnvInfo;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContextManager;
import com.aventstack.extentreports.model.service.MediaService;
import com.aventstack.extentreports.model.service.TestService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractProcessor extends ReactiveSubject {
    private final List<Test> testList = getReport().getTestList();
    private final NamedAttributeContextManager<Author> authorCtx = getReport().getAuthorCtx();
    private final NamedAttributeContextManager<Category> categoryCtx = getReport().getCategoryCtx();
    private final NamedAttributeContextManager<Device> deviceCtx = getReport().getDeviceCtx();

    private String[] mediaResolverPath;
    private boolean usingNaturalConf = true;

    @Override
    protected void onTestCreated(Test test) {
        testList.add(test);
        super.onTestCreated(test);
    }

    @Override
    protected void onTestRemoved(Test test) {
        TestService.deleteTest(testList, test);
        super.onTestRemoved(test);
    }

    protected void onNodeCreated(Test node) {
        super.onTestCreated(node);
    }

    @Override
    protected void onLogCreated(Log log, Test test) {
        super.onLogCreated(log, test);
        if (log.hasException())
            getReport().getExceptionInfoCtx().addContext(log.getException(), test);
    }

    @Override
    protected void onMediaAdded(Media m, Test test) {
        tryResolvePath(m);
        super.onMediaAdded(m, test);
    }

    @Override
    protected void onMediaAdded(Media m, Log log) {
        tryResolvePath(m);
        super.onMediaAdded(m, log);
    }

    private void tryResolvePath(Media m) {
        MediaService.tryResolveMediaPath(m, mediaResolverPath);
    }

    protected void onAuthorAdded(Author x, Test test) {
        authorCtx.addContext(x, test);
        super.onAuthorAssigned(x, test);
    }

    protected void onCategoryAdded(Category x, Test test) {
        categoryCtx.addContext(x, test);
        super.onCategoryAssigned(x, test);
    }

    protected void onDeviceAdded(Device x, Test test) {
        deviceCtx.addContext(x, test);
        super.onDeviceAssigned(x, test);
    }

    @Override
    protected void onFlush() {
        authorCtx.getSet().forEach(x -> x.refresh());
        categoryCtx.getSet().forEach(x -> x.refresh());
        deviceCtx.getSet().forEach(x -> x.refresh());
        getReport().getStats().update(testList);
        getReport().setEndTime(Calendar.getInstance().getTime());
        if (!usingNaturalConf)
            applyConf();
        super.onFlush();
    }

    private void applyConf() {
        Date min = testList.stream()
                .map(t -> t.getStartTime())
                .min(Date::compareTo)
                .get();
        Date max = testList.stream()
                .map(t -> t.getEndTime())
                .max(Date::compareTo)
                .get();
        getReport().setStartTime(min);
        getReport().setEndTime(max);
    }

    protected void onReportLogAdded(String log) {
        getReport().getLogs().add(log);
    }

    protected void onSystemInfoAdded(SystemEnvInfo env) {
        getReport().getSystemEnvInfo().add(env);
    }

    protected void convertRawEntities(ExtentReports extent, File f) throws IOException {
        RawEntityConverter converter = new RawEntityConverter(extent);
        converter.convertAndApply(f);
    }
}
