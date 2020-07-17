package com.aventstack.extentreports.markuputils;

import java.io.Serializable;

@FunctionalInterface
public interface Markup extends Serializable {
    String getMarkup();
}