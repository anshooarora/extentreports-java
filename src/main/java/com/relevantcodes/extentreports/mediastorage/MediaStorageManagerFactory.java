package com.relevantcodes.extentreports.mediastorage;

public class MediaStorageManagerFactory {
    public MediaStorage getManager(String location) {
        switch (location.trim().toLowerCase()) {
            case "local":
                return new LocalMediaManager();
            case "http":
                return new HttpMediaManager();
            default:
                return null;
        }
    }
}
