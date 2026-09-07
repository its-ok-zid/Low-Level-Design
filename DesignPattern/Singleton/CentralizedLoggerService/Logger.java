package DesignPattern.Singleton.CentralizedLoggerService;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger {
    private static volatile Logger instance;
    private final ConcurrentLinkedQueue<LogRecord> logHistory = new ConcurrentLinkedQueue<>();

    private Logger() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to obtain the singleton instance.");
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(LogLevel logLevel, String message) {
        if (logLevel == null || message == null) return;
        LogRecord logRecord = new LogRecord(logLevel, message);
        logHistory.add(logRecord);
        System.out.println(logRecord);
    }

    public int getCount() {
        return logHistory.size();
    }

    public void displayLogHistory() {
        System.out.println("\n========== Complete Log History ==========");
        logHistory.forEach(System.out::println);
        System.out.println("==========================================\n");
    }
}