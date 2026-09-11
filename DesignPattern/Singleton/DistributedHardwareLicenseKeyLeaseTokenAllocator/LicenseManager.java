package DesignPattern.Singleton.DistributedHardwareLicenseKeyLeaseTokenAllocator;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LicenseManager {
    private static final int MAX_SEATS = 5;
    private static volatile LicenseManager instance;

    private final ConcurrentHashMap<String, LicenseToken> activeTokens = new ConcurrentHashMap<>();

    private LicenseManager() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static LicenseManager getInstance() {
        if (instance == null) {
            synchronized (LicenseManager.class) {
                if (instance == null) {
                    instance = new LicenseManager();
                }
            }
        }
        return instance;
    }

    public synchronized LicenseToken acquireToken(String workerId, long durationMillis) {
        if (workerId == null || workerId.trim().isEmpty() || durationMillis <= 0) {
            throw new IllegalArgumentException("Invalid allocation parameters.");
        }

        if (getActiveTokenCount() >= MAX_SEATS) {
            return null; // No available tokens
        }
        String tokenId = UUID.randomUUID().toString();
        LicenseToken licenseToken = new LicenseToken(tokenId, workerId, System.currentTimeMillis(), durationMillis);
        activeTokens.put(tokenId, licenseToken);

        return licenseToken;

    }

    public boolean releaseToken(String tokenId) {
        if (tokenId == null) return false;
        return activeTokens.remove(tokenId) != null;
    }

    public int getActiveTokenCount() {
        activeTokens.values().removeIf(token -> !token.isValid());
        return activeTokens.size();
    }
}

