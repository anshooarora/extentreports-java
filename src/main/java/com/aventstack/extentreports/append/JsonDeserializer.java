package com.aventstack.extentreports.append;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonDeserializer {
    private File f;

    public JsonDeserializer(File f) {
        this.f = f;
    }

    public List<Test> deserialize() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Media.class, new ScreenCaptureTypeAdapter())
                .create();
        String json = new String(Files.readAllBytes(f.toPath()));
        Type t = new TypeToken<ArrayList<Test>>() {
        }.getType();
        List<Test> tests = gson.fromJson(json, t);
        return tests;
    }
}