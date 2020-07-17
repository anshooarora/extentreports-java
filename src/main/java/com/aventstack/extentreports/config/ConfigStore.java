package com.aventstack.extentreports.config;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigStore {
    private Map<String, Object> store = new HashMap<String, Object>();

    public void addConfig(String key, Object value) {
        store.put(key, value);
    }

    public boolean containsConfig(String k) {
        return store.containsKey(k);
    }

    public void removeConfig(String k) {
        store.remove(k);
    }

    public Object getConfig(String k) {
        return store.get(k);
    }

    public void extendConfig(Map<String, Object> map) {
        map.forEach((key, value) -> this.addConfig(key, value));
    }

    public void extendConfig(ConfigStore configMap) {
        this.extendConfig(configMap.getStore());
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }
}
