package com.aventstack.extentreports.config.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;

import org.testng.reporters.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

public class JsonConfigLoader<T> implements ConfigLoadable<T> {
    private File f;
    private String json;
    private T instance;
    private InstanceCreator<T> creator;

    public JsonConfigLoader(T instance, File f) throws FileNotFoundException {
        if (f == null)
            throw new IllegalArgumentException("File cannot be null");
        if (!f.exists())
            throw new FileNotFoundException("File " + f.getAbsolutePath() + " could not be found");
        init(instance);
        this.f = f;
    }

    public JsonConfigLoader(T instance, String json) {
        if (json == null || json.isEmpty())
            throw new IllegalArgumentException("Json input cannot be null or empty");
        init(instance);
        this.json = json;
    }

    private void init(T instance) {
        this.instance = instance;
        creator = new InstanceCreator<T>() {
            @Override
            public T createInstance(Type type) {
                return instance;
            }
        };
    }

    @SuppressWarnings("unchecked")
    public void apply() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(instance.getClass(), creator)
                .create();
        try {
            String json = f != null ? Files.readFile(f) : this.json;
            instance = (T) gson.fromJson(json, instance.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
