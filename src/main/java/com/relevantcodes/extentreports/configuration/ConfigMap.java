package com.relevantcodes.extentreports.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigMap {
    
    List<Config> configList;
    
    public ConfigMap() { 
        configList = new ArrayList<>();
    }
    
    public void setConfig(Config c) {
        if (containsKey(c.getKey()))
            removeKey(c.getKey());
        
        configList.add(c);
    }
    
    public List<Config> getConfigList() { return configList; }
    
    public boolean containsKey(String k) {
        return configList.stream().anyMatch(x -> x.getKey().equals(k));
    }
    
    void removeKey(String k) {
        configList.removeIf(x -> x.getKey().equals(k));
    }
    
    public Object getValue(String k) {
        Optional<Config> c = configList.stream()
                .filter(x -> x.getKey().equals(k))
                .findFirst();
        
        if (c.isPresent()) {
            return c.get().getValue();
        }
        
        return null;
    }
}
