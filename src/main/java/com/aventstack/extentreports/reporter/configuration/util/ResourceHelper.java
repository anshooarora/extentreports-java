package com.aventstack.extentreports.reporter.configuration.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ResourceHelper {
    private ResourceHelper() {

    }

    public static void saveOfflineResources(String baseDir, String[] resx, String toPath) {
        IOUtil.createDirectory(toPath);
        for (String f : resx) {
            Path path = Paths.get(baseDir, f);
            String fromPath = path.toString();
            String toPathComplete = Paths.get(toPath, new File(f).getName()).toString();
            IOUtil.moveResource(fromPath, toPathComplete);
        }
    }

    public static void saveOfflineResources(String[] resx, String toPath) {
        Arrays.stream(resx).forEach(x -> IOUtil.moveResource(x, toPath));
    }
}