package com.aventstack.extentreports.reporter.configuration;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ViewOrder<T extends ViewsConfigurable<T>> {
    private static final List<ViewName> DEFAULT_ORDER = Arrays.asList(new ViewName[]{
            ViewName.TEST,
            ViewName.EXCEPTION,
            ViewName.CATEGORY,
            ViewName.DEVICE,
            ViewName.AUTHOR,
            ViewName.LOG,
            ViewName.DASHBOARD
    });

    private List<ViewName> viewOrder = DEFAULT_ORDER;
    private ViewConfigurer<?> configurer;

    public ViewOrder(ViewConfigurer<T> configurer) {
        this.configurer = configurer;
    }

    @SuppressWarnings("unchecked")
    public ViewConfigurer<T> as(List<ViewName> order) {
        this.viewOrder = order;
        return (ViewConfigurer<T>) configurer;
    }

    public ViewConfigurer<T> as(ViewName[] viewOrder) {
        return as(Arrays.asList(viewOrder));
    }
}
