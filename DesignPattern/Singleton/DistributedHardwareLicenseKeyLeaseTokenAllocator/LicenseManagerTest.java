package DesignPattern.Singleton.DistributedHardwareLicenseKeyLeaseTokenAllocator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LicenseManagerTest {
    public static void main(String[] args) throws InterruptedException {
        testReflectionAttack();
        testConcurrentContention();
    }

    public static void testReflectionAttack() {
        System.out.println("Testing Reflection Attack on LicenseManager Singleton...");

        LicenseManager instance1 = LicenseManager.getInstance();

        try {
            Constructor<LicenseManager> constructor = LicenseManager.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            LicenseManager rogueInstance = constructor.newInstance();

            System.out.println("Instance 1: " + instance1);
            System.out.println("Rogue Instance: " + rogueInstance);

            System.err.println("FAILED: Reflection successfully bypassed Singleton!");
        } catch (InvocationTargetException e) {
            System.out.println("SUCCESS: Reflection blocked by constructor guard -> " + e.getCause().getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: Unexpected exception -> " + e.getMessage());
        }
    }

    public static void testConcurrentContention() throws InterruptedException {
        System.out.println("\n--- Testing Concurrent Seat Contention (8 Threads for 5 Seats) ---");

        LicenseManager manager = LicenseManager.getInstance();
        Thread[] workers = new Thread[8];

        for (int i = 1; i <= 8; i++) {
            final String workerId = "Worker-" + i;

            workers[i - 1] = new Thread(() -> {
                // Each thread attempts to acquire a 5-second lease
                LicenseToken token = manager.acquireToken(workerId, 5000);

                if (token != null) {
                    // Here allocatedTo is explicitly used to verify and log ownership
                    System.out.println("[ACQUIRED] Seat assigned to: " + token.getAllocatedTo()
                            + " | Token ID: " + token.getTokenId());
                } else {
                    System.out.println("[REJECTED] " + workerId + " was rejected (Seat capacity full).");
                }
            }, workerId);
        }

        // STEP 1: Launch all threads simultaneously
        for (Thread worker : workers) {
            worker.start();
        }

        // STEP 2: Block main thread until all worker threads finish execution
        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println("\nFinal Active Token Count in Pool: " + manager.getActiveTokenCount());
    }
}
