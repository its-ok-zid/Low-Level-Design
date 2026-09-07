package DesignPattern.Singleton.CentralizedLoggerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogRecord {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final LocalDateTime timestamp;
    private final String threadName;
    private final LogLevel logLevel;
    private final String message;

    public LogRecord(LogLevel logLevel, String message) {
        this.timestamp = LocalDateTime.now();
        this.threadName = Thread.currentThread().getName();
        this.logLevel = logLevel;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getThreadName() {
        return threadName;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("[%s] [%s] [%s] %s",
                timestamp.format(FORMATTER),
                threadName,
                logLevel,
                message);
    }
}