package com.aventstack.extentreports;

import java.io.IOException;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.MediaType;
import com.aventstack.extentreports.model.ScreenCapture;

public class MediaEntityBuilder {

	private static ThreadLocal<Media> media;
	
	private static class MediaBuilderInstance {
        static final MediaEntityBuilder INSTANCE = new MediaEntityBuilder();
        
        private MediaBuilderInstance() { }
    }

    private MediaEntityBuilder() { }
	
    private static MediaEntityBuilder getInstance() {
    	return MediaBuilderInstance.INSTANCE;
    }
    
	public MediaEntityModelProvider build() {
		return new MediaEntityModelProvider(media.get());
	}
	
	public static MediaEntityBuilder createScreenCaptureFromPath(String path, String title) throws IOException {
        if (path == null || path.isEmpty())
            throw new IOException("ScreenCapture path cannot be null or empty.");
        
        return createScreenCapture(path, title, false);
    }
    
    public static MediaEntityBuilder createScreenCaptureFromPath(String path) throws IOException {
        return createScreenCaptureFromPath(path, null);
    }
    
    public static MediaEntityBuilder createScreenCaptureFromBase64String(String base64String) throws IOException {
        if (base64String == null || base64String.trim().equals(""))
            throw new IOException("Base64 string cannot be null or empty.");
        
        return createScreenCapture(base64String, null, true);
    }
    
    private static MediaEntityBuilder createScreenCapture(String pathOrBase64String, String title, boolean isBase64String) {
        ScreenCapture sc = new ScreenCapture();
        sc.setMediaType(MediaType.IMG);
        if (isBase64String)
            sc.setBase64String(pathOrBase64String);
        else
            sc.setPath(pathOrBase64String);
        
        if (title != null)
            sc.setName(title);
        
        media = new ThreadLocal<Media>();
        media.set(sc);
        
        return getInstance();
    }
	
}
