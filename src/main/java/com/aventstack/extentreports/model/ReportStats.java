package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.ScenarioOutline;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportStats implements Serializable {
    private static final long serialVersionUID = 5424613250832948474L;

    private AnalysisStrategy analysisStrategy = AnalysisStrategy.TEST;
    private final Map<Status, Long> parent = new ConcurrentHashMap<>();
    private final Map<Status, Long> child = new ConcurrentHashMap<>();
    private final Map<Status, Long> grandchild = new ConcurrentHashMap<>();
    private final Map<Status, Long> log = new ConcurrentHashMap<>();
    private final Map<Status, Float> parentPercentage = new ConcurrentHashMap<>();
    private final Map<Status, Float> childPercentage = new ConcurrentHashMap<>();
    private final Map<Status, Float> grandchildPercentage = new ConcurrentHashMap<>();
    private final Map<Status, Float> logPercentage = new ConcurrentHashMap<>();

    public final void update(final List<Test> testList) {
        reset();
        if (testList == null || testList.isEmpty())
            return;

        update(testList, parent, parentPercentage);

        // level 1, for BDD, this would also include Scenario and excludes
        // ScenarioOutline
        List<Test> children = testList.stream()
                .flatMap(x -> x.getChildren().stream())
                .filter(x -> x.getBddType() != ScenarioOutline.class)
                .collect(Collectors.toList());
        List<Test> scenarios = testList.stream()
                .flatMap(x -> x.getChildren().stream())
                .flatMap(x -> x.getChildren().stream())
                .filter(x -> x.getBddType() == Scenario.class)
                .collect(Collectors.toList());
        children.addAll(scenarios);
        update(children, child, childPercentage);

        // level 2, for BDD, this only includes Steps
        List<Test> grandChildren = children.stream()
                .flatMap(x -> x.getChildren().stream())
                .filter(x -> x.getBddType() != Scenario.class)
                .collect(Collectors.toList());
        update(grandChildren, grandchild, grandchildPercentage);

        List<Log> logs = testList.stream().flatMap(x -> x.getLogs().stream()).collect(Collectors.toList());
        logs.addAll(children.stream().flatMap(x -> x.getLogs().stream()).collect(Collectors.toList()));
        logs.addAll(grandChildren.stream().flatMap(x -> x.getLogs().stream()).collect(Collectors.toList()));
        update(logs, log, logPercentage);
    }

    private final void update(final List<? extends RunResult> list, final Map<Status, Long> countMap,
            final Map<Status, Float> percentageMap) {
        if (list == null)
            return;
        Map<Status, Long> map = list.stream().collect(
                Collectors.groupingBy(RunResult::getStatus, Collectors.counting()));
        Arrays.asList(Status.values()).forEach(x -> map.putIfAbsent(x, 0L));
        countMap.putAll(map);
        if (list.isEmpty()) {
            percentageMap.putAll(
                    map.entrySet().stream()
                            .collect(Collectors.toMap(e -> e.getKey(), e -> Float.valueOf(e.getValue()))));
            return;
        }
        Map<Status, Float> pctMap = map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                        e -> Float.valueOf(e.getValue() * 100 / list.size())));
        percentageMap.putAll(pctMap);
    }

    public final void reset() {
        List<RunResult> list = new ArrayList<>();
        update(list, parent, parentPercentage);
        update(list, child, childPercentage);
        update(list, grandchild, grandchildPercentage);
        update(list, log, logPercentage);
    }

    public final long sumStat(final Map<Status, Long> stat) {
        return stat.values().stream().mapToLong(Long::longValue).sum();
    }
}
