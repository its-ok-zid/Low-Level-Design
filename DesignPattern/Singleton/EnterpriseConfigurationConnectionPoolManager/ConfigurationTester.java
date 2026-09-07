package DesignPattern.Singleton.EnterpriseConfigurationConnectionPoolManager;

public class ConfigurationTester {
    public static void main(String[] args) throws InterruptedException {
        ConfigurationManager configManager1 = ConfigurationManager.getInstance();
        ConfigurationManager configManager2 = ConfigurationManager.getInstance();

        // 1. Reference Identity Verification
        System.out.println("Are both instances identical? " + (configManager1 == configManager2));
        System.out.println("Instance 1 HashCode: " + configManager1.hashCode());
        System.out.println("Instance 2 HashCode: " + configManager2.hashCode());

        // 2. Multithreaded Access Simulation
        Thread t1 = new Thread(() -> {
            ConfigurationManager cm = ConfigurationManager.getInstance();
            cm.setConfiguration("db.url", "jdbc:mysql://localhost:3306/mydb");
            cm.setConfiguration("app.env", "production");
        });

        Thread t2 = new Thread(() -> {
            ConfigurationManager cm = ConfigurationManager.getInstance();
            cm.setConfiguration("api.timeoutMs", "5000");
            cm.setConfiguration("db.maxConnections", "20");
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // 3. Verify shared memory across instances
        configManager2.displayConfiguration();
    }
}