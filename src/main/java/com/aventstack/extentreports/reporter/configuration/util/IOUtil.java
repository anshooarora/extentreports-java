package com.aventstack.extentreports.reporter.configuration.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class IOUtil {
    private static final String SEP = "/";

    private IOUtil() {
    }

    public static void createDirectory(String path) {
        new File(path).mkdirs();
    }

    public static void createDirectory(String[] path) {
        Arrays.asList(path).forEach(IOUtil::createDirectory);
    }

    public static void moveResource(String resourcePath, String copyPath) {
        if (copyPath != null)
            copyPath = copyPath.replace("\\", SEP);
        if (resourcePath != null)
            resourcePath = resourcePath.replace("\\", SEP);
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream in = classLoader.getResourceAsStream(resourcePath);
            FileOutputStream out = new FileOutputStream(copyPath);
            byte[] b = new byte[1024];
            int noOfBytes = 0;
            while ((noOfBytes = in.read(b)) != -1)
                out.write(b, 0, noOfBytes);
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveBinary(String resourcePath, String copyPath) {
        URI uri = new File(resourcePath).toURI();
        Path path = Paths.get(uri);
        try {
            Files.copy(path, new File(copyPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
