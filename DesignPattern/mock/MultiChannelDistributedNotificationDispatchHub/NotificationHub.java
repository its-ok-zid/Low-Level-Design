package DesignPattern.mock.MultiChannelDistributedNotificationDispatchHub;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationHub {
    private static volatile NotificationHub instance;
    private static final int MAX_GLOBAL_LIMIT = 10;

    private final Set<String> processedKey = ConcurrentHashMap.newKeySet();
    private final AtomicInteger totalDispatches = new AtomicInteger(0);

    private NotificationHub() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static NotificationHub getInstance() {
        if (instance == null) {
            synchronized (NotificationHub.class) {
                if (instance == null) {
                    instance = new NotificationHub();
                }
            }
        }
        return instance;
    }

    public synchronized boolean dispatchNotification(NotificationChannelFactory factory,
                                                     String recipient,
                                                     String message,
                                                     String idempotencyKey) {
        if (factory == null || recipient == null || message == null || idempotencyKey == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (processedKey.contains(idempotencyKey)) {
            System.out.println("[BLOCKED] Duplicate idempotency key: " + idempotencyKey);
            return false;
        }
        if (totalDispatches.get() >= MAX_GLOBAL_LIMIT) {
            System.out.println("[REJECTED] Global rate limit reached (" + MAX_GLOBAL_LIMIT + ").");
            return false;
        }

        NotificationChannel channel = factory.createChannel();
        boolean sent = channel.send(recipient, message);
        if (sent) {
            processedKey.add(idempotencyKey);
            totalDispatches.incrementAndGet();
            System.out.println("[SENT] Handled via " + channel.getClass().getSimpleName() + " for " + recipient);
            return true;
        } else {
            System.out.println("[FAILED] Delivery rejected by " + channel.getClass().getSimpleName());
            return false;
        }
    }

    public int getTotalDispatches() {
        return totalDispatches.get();
    }
}