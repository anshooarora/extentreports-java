package com.aventstack.extentreports.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExceptionInfo extends NamedAttribute implements Serializable, BaseEntity {
    private static final long serialVersionUID = -8152865623044194249L;
    private Throwable exception;
    private String stackTrace;
    
    @Builder
    public ExceptionInfo(Throwable exception, String name, String stackTrace) {
        super(name);
        this.exception = exception;
        this.stackTrace = stackTrace;
    }
}
