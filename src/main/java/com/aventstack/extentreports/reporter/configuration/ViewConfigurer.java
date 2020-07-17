package com.aventstack.extentreports.reporter.configuration;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ViewConfigurer<T extends ViewsConfigurable<T>> {
    private final T reporter;
    private final ViewOrder<T> viewOrder;

    public ViewConfigurer(T reporter) {
        this.reporter = reporter;
        viewOrder = new ViewOrder<T>(this);
    }

    public T apply() {
        return reporter;
    }
}
