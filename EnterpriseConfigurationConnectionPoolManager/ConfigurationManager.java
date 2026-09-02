package EnterpriseConfigurationConnectionPoolManager;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationManager {
    private static volatile ConfigurationManager instance;

    private ConcurrentHashMap<String, String> configurations = new ConcurrentHashMap<>();

    private ConfigurationManager() {
        // Private constructor to prevent instantiation
    }


}
