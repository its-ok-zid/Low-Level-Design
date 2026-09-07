package DesignPattern.Singleton.EnterpriseConfigurationConnectionPoolManager;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private final ConcurrentHashMap<String, String> configurations = new ConcurrentHashMap<>();

    private ConfigurationManager() {
        // Prevents reflection-based instantiation
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to obtain the singleton instance.");
        }
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void setConfiguration(String key, String value) {
        if (key != null && value != null) {
            configurations.put(key, value);
        }
    }

    public String getConfiguration(String key) {
        return configurations.get(key);
    }

    public String getConfiguration(String key, String defaultValue) {
        return configurations.getOrDefault(key, defaultValue);
    }

    public void displayConfiguration() {
        System.out.println("========== Current Configurations ==========");
        configurations.forEach((key, value) -> System.out.println(key + " -> " + value));
        System.out.println("============================================\n");
    }
}