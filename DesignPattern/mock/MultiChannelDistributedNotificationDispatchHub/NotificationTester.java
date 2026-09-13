package DesignPattern.mock.MultiChannelDistributedNotificationDispatchHub;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class NotificationTester {
    public static void main(String[] args) throws InterruptedException {
        testReflectionAttack();
        testConcurrentNotificationDispatch();
    }

    public static void testReflectionAttack() {
        System.out.println("--- Testing Reflection Attack on NotificationHub ---");
        NotificationHub hub = NotificationHub.getInstance();
        try {
            Constructor<NotificationHub> constructor = NotificationHub.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            NotificationHub rogue = constructor.newInstance();
            System.err.println("FAILED: Reflection bypassed Singleton! Rogue: " + rogue);
        } catch (InvocationTargetException e) {
            System.out.println("SUCCESS: Reflection blocked -> " + e.getCause().getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static void testConcurrentNotificationDispatch() throws InterruptedException {
        System.out.println("\n--- Testing Concurrent Notification Dispatch (15 Threads across Channels) ---");
        NotificationHub hub = NotificationHub.getInstance();
        Thread[] threads = new Thread[15];

        for (int i = 0; i < 15; i++) {
            final int idx = i;
            final String threadId = "Worker-" + (i + 1);

            threads[i] = new Thread(() -> {
                NotificationChannelFactory factory;
                String recipient;
                String message = "Event data from " + threadId;

                if (idx % 3 == 0) {
                    factory = new EmailChannelFactory();
                    recipient = "engineer" + idx + "@company.com";
                } else if (idx % 3 == 1) {
                    factory = new SmsChannelFactory();
                    recipient = "+155500011" + (idx % 10);
                } else {
                    factory = new PushChannelFactory();
                    recipient = "FCM_DEVICE_REGISTERED_TOKEN_" + idx;
                }

                // Simulate duplicate idempotency submission for Thread 13 matching Thread 1
                String idempotencyKey = (idx == 12) ? "SHARED-DUP-ID" : (idx == 0 ? "SHARED-DUP-ID" : UUID.randomUUID().toString());

                boolean success = hub.dispatchNotification(factory, recipient, message, idempotencyKey);
                if (success) {
                    System.out.println("[SUCCESS] " + threadId + " completed transmission.");
                } else {
                    System.out.println("[REJECTED/FAILED] " + threadId + " transmission dropped.");
                }
            }, threadId);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("\nFinal Successful Dispatches Count: " + hub.getTotalDispatches());
    }
}