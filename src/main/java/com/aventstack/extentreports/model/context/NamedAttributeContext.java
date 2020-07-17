package com.aventstack.extentreports.model.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.NamedAttribute;
import com.aventstack.extentreports.model.Test;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NamedAttributeContext<T extends NamedAttribute> implements Serializable {
    private static final long serialVersionUID = -2671203343283101908L;

    private final List<Test> testList = Collections.synchronizedList(new ArrayList<>());
    private T attr;
    private Integer passed = 0;
    private Integer failed = 0;
    private Integer skipped = 0;
    private Integer others = 0;

    public NamedAttributeContext(T attribute, Test test) {
        this.attr = attribute;
        addTest(test);
    }

    public void addTest(Test test) {
        if (test == null)
            throw new IllegalArgumentException("Test cannot be null");
        testList.add(test);
        refresh(test);
    }

    private synchronized void refresh(Test test) {
        if (test.getStatus() == Status.PASS)
            passed++;
        else if (test.getStatus() == Status.FAIL)
            failed++;
        else if (test.getStatus() == Status.SKIP)
            skipped++;
        else
            others++;
    }

    public void refresh() {
        passed = failed = skipped = others = 0;
        testList.forEach(this::refresh);
    }

    public Integer size() {
        return passed + failed + skipped + others;
    }
}
