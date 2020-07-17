package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.context.NamedAttributeContextManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString(includeFieldNames = true)
public class Report implements Serializable, BaseEntity {
    private static final long serialVersionUID = -8667496631392333349L;

    @Builder.Default
    private Date startTime = Calendar.getInstance().getTime();
    @Builder.Default
    private Date endTime = Calendar.getInstance().getTime();
    @Builder.Default
    private Status status = Status.PASS;
    private final transient ReportStats stats = new ReportStats();
    private final List<Test> testList = Collections.synchronizedList(new ArrayList<>());
    private final transient NamedAttributeContextManager<Author> authorCtx = new NamedAttributeContextManager<>();
    private final transient NamedAttributeContextManager<Category> categoryCtx = new NamedAttributeContextManager<>();
    private final transient NamedAttributeContextManager<Device> deviceCtx = new NamedAttributeContextManager<>();
    private final transient NamedAttributeContextManager<ExceptionInfo> exceptionInfoCtx = new NamedAttributeContextManager<>();
    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());
    private final List<SystemEnvInfo> systemEnvInfo = new ArrayList<>();

    public final boolean isBDD() {
        return !testList.isEmpty() && testList.stream().allMatch(Test::isBDD);
    }

    public final boolean anyTestHasStatus(Status status) {
        return testList.stream()
                .anyMatch(x -> x.getStatus() == status);
    }

    public final long timeTaken() {
        return endTime.getTime() - startTime.getTime();
    }

    public final Status getStatus() {
        List<Status> list = testList
                .stream()
                .map(x -> x.getStatus())
                .collect(Collectors.toList());
        return Status.max(list);
    }
}
