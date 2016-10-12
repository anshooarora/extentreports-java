package com.aventstack.extentreports.mediastorage;

import java.io.IOException;

import com.aventstack.extentreports.model.Media;

public interface MediaStorage {
    void init(String v) throws IOException;
    void storeMedia(Media m) throws IOException;
}
