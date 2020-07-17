package com.aventstack.extentreports.model.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ScreenCapture;

public class MediaService {
    public static void tryResolveMediaPath(Media media, String[] knownPath) {
        if (knownPath == null || (media instanceof ScreenCapture && ((ScreenCapture) media).getBase64() != null))
            return;
        String loc = media.getPath();
        File f = new File(loc);
        if (!new File(loc).exists()) {
            for (String p : knownPath) {
                Path path = Paths.get(p, loc);
                if (path.toFile().exists()) {
                    media.setResolvedPath(path.toFile().getAbsolutePath());
                    break;
                }
                path = Paths.get(p, f.getName());
                if (path.toFile().exists()) {
                    media.setResolvedPath(path.toFile().getAbsolutePath());
                    break;
                }
            }
        }
    }

    public static boolean isBase64(Media m) {
        return m instanceof ScreenCapture && ((ScreenCapture) m).getBase64() != null;
    }

    public static String getBase64(Media m) {
        if (isBase64(m))
            return ((ScreenCapture) m).getBase64();
        return null;
    }
}
