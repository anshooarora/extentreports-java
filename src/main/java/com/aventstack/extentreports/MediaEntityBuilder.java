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
		
		ScreenCapture sc = new ScreenCapture();
		sc.setMediaType(MediaType.IMG);
		sc.setPath(path);
		
		if (title != null)
			sc.setName(title);
		
		media = new ThreadLocal<Media>();
		media.set(sc);
		
		return getInstance();
	}
	
	public static MediaEntityBuilder createScreenCaptureFromPath(String path) throws IOException {
		return createScreenCaptureFromPath(path, null);
	}
	
}
