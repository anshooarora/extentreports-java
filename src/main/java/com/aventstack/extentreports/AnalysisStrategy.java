package com.aventstack.extentreports;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.model.ReportStats;

/**
 * Enumeration for various kinds of analysis strategies used by
 * {@link ReportStats}
 */
public enum AnalysisStrategy {
    /**
     * This is the efault strategy used by BDD tests. If any created test uses a
     * {@link IGherkinFormatterModel} type, the strategy would default to BDD
     * and {@link AnalysisStrategy} would be ignored.
     */
    BDD,

    /**
     * This strategy is useful when all tests in a suite are grouped together by
     * their containing Class. This strategy would account for the number of
     * Classes that passed or failed, and repeat the process for tests.
     */
    CLASS,

    /**
     * This strategy is useful if there are 3 levels in the hierarchy: Suite,
     * Class and Test.
     */
    SUITE,

    /**
     * TEST is the default strategy and only tracks the leaf nodes to create
     * {@link ReportStats}
     */
    TEST
}
