package com.aventstack.extentreports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.aventstack.extentreports.view.Ico;

import lombok.Getter;

@Getter
public enum Status {
    INFO("Info", 10), PASS("Pass", 20), SKIP("Skip", 30), WARNING("Warning", 40), FAIL("Fail", 50);

    private final Integer level;
    private final String name;

    Status(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    private static void resolveHierarchy(List<Status> status) {
        status.sort((Status s1, Status s2) -> s1.getLevel().compareTo(s2.getLevel()));
    }

    public static List<Status> getResolvedHierarchy(List<Status> status) {
        List<Status> list = new ArrayList<>(status);
        resolveHierarchy(list);
        return list;
    }

    public static Status max(Collection<Status> status) {
        return status.stream().max(Comparator.comparing(Status::getLevel)).orElse(PASS);
    }

    public static Status max(Status s1, Status s2) {
        return s1.getLevel() > s2.getLevel() ? s1 : s2;
    }

    public static Status min(Collection<Status> status) {
        return status.stream().min(Comparator.comparing(Status::getLevel)).orElse(PASS);
    }

    public static String i(String status) {
        return Ico.ico(status).toString();
    }

    public static String i(Status status) {
        return Ico.ico(status.toString()).toString();
    }

    public String toLower() {
        return name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }
}
