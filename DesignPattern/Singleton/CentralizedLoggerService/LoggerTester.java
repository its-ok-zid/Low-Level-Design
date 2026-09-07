package DesignPattern.Singleton.CentralizedLoggerService;

public class LoggerTester {
    public static void main(String[] args) throws InterruptedException {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // 1. Reference Identity Verification
        System.out.println("Are both logger instances identical? " + (logger1 == logger2));
        System.out.println("Logger 1 HashCode: " + logger1.hashCode());
        System.out.println("Logger 2 HashCode: " + logger2.hashCode());

        // 2. Concurrent Worker Threads
        Thread t1 = new Thread(() -> {
            Logger logger = Logger.getInstance();
            logger.log(LogLevel.INFO, "Worker 1: Dispatching job payload.");
            logger.log(LogLevel.WARN, "Worker 1: Network response delay detected.");
        }, "Worker-1");

        Thread t2 = new Thread(() -> {
            Logger logger = Logger.getInstance();
            logger.log(LogLevel.INFO, "Worker 2: Initializing cache synchronization.");
            logger.log(LogLevel.ERROR, "Worker 2: Connection timed out on replica 3.");
        }, "Worker-2");

        Thread t3 = new Thread(() -> {
            Logger logger = Logger.getInstance();
            logger.log(LogLevel.INFO, "Worker 3: Heartbeat check healthy.");
        }, "Worker-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // 3. Confirm total count and output history
        System.out.println("\nTotal Recorded Logs: " + logger2.getCount());
        logger2.displayLogHistory();
    }
}