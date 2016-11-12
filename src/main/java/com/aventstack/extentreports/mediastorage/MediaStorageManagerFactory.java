package com.aventstack.extentreports.mediastorage;

public class MediaStorageManagerFactory {
    public MediaStorage getManager(String location) {
        switch (location.trim().toLowerCase()) {
            case "http":
                return new HttpMediaManager();
            default:
                return null;
        }
    }
}
