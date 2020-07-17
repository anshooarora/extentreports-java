package com.aventstack.extentreports;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ScreenCapture;

/**
 * Media builder for base64 and binary screenshots
 *
 */
public class MediaEntityBuilder {
    private static final String BASE64_ENCODED = "data:image/png;base64,";
    private static ThreadLocal<Media> media = new ThreadLocal<>();

    private static class MediaBuilderInstance {
        static final MediaEntityBuilder INSTANCE = new MediaEntityBuilder();

        private MediaBuilderInstance() {
        }
    }

    private MediaEntityBuilder() {
    }

    private static synchronized MediaEntityBuilder getInstance() {
        return MediaBuilderInstance.INSTANCE;
    }

    public Media build() {
        return media.get();
    }

    public static MediaEntityBuilder createScreenCaptureFromPath(String path, String title) {
        if (path == null || path.isEmpty())
            throw new IllegalArgumentException("ScreenCapture path cannot be null or empty");
        media.set(ScreenCapture.builder().path(path).title(title).build());
        return getInstance();
    }

    public static MediaEntityBuilder createScreenCaptureFromPath(String path) {
        return createScreenCaptureFromPath(path, null);
    }

    public static MediaEntityBuilder createScreenCaptureFromBase64String(String base64, String title) {
        if (base64 == null || base64.trim().equals(""))
            throw new IllegalArgumentException("Base64 string cannot be null or empty");
        if (!base64.startsWith("data:"))
            base64 = BASE64_ENCODED + base64;
        media.set(ScreenCapture.builder().base64(base64).title(title).build());
        return getInstance();
    }

    public static MediaEntityBuilder createScreenCaptureFromBase64String(String base64) {
        return createScreenCaptureFromBase64String(base64, null);
    }
}