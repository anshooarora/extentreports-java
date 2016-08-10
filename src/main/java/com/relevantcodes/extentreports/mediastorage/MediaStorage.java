package com.relevantcodes.extentreports.mediastorage;

import java.io.IOException;

import com.relevantcodes.extentreports.model.Media;

public interface MediaStorage {
    void init(String v) throws IOException;
    void storeMedia(Media m) throws IOException;
}
