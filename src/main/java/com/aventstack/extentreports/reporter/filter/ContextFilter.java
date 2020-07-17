package com.aventstack.extentreports.reporter.filter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aventstack.extentreports.Status;

import lombok.Getter;

@Getter
public class ContextFilter {
    private static final Builder BUILDER = new Builder();
    private Set<Status> status;
    private Set<String> author;
    private Set<String> category;
    private Set<String> device;

    public ContextFilter(Set<Status> status, Set<String> author, Set<String> category, Set<String> device) {
        this.status = status;
        this.author = author;
        this.category = category;
        this.device = device;
    }

    public static Builder builder() {
        return BUILDER;
    }

    public static class Builder {
        private Set<Status> statusSet;
        private Set<String> authorSet;
        private Set<String> categorySet;
        private Set<String> deviceSet;

        public Builder status(Set<Status> status) {
            this.statusSet = status;
            return this;
        }

        public Builder status(Status[] status) {
            return status(Arrays.stream(status).collect(Collectors.toSet()));
        }

        public Builder status(Status status) {
            return status(Stream.of(status).collect(Collectors.toSet()));
        }

        public Builder author(Set<String> author) {
            this.authorSet = author;
            return this;
        }

        public Builder author(String[] author) {
            return author(Arrays.stream(author).collect(Collectors.toSet()));
        }

        public Builder author(String author) {
            return author(Stream.of(author).collect(Collectors.toSet()));
        }

        public Builder category(Set<String> category) {
            this.categorySet = category;
            return this;
        }

        public Builder category(String[] category) {
            return category(Arrays.stream(category).collect(Collectors.toSet()));
        }

        public Builder category(String category) {
            return category(Stream.of(category).collect(Collectors.toSet()));
        }

        public Builder device(Set<String> device) {
            this.deviceSet = device;
            return this;
        }

        public Builder device(String[] device) {
            return author(Arrays.stream(device).collect(Collectors.toSet()));
        }

        public Builder device(String device) {
            return author(Stream.of(device).collect(Collectors.toSet()));
        }

        public ContextFilter build() {
            return new ContextFilter(statusSet, authorSet, categorySet, deviceSet);
        }
    }
}
