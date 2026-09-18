package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;

public class PlayReadyDrmSessionManager implements DrmSessionManager {
    @Override
    public boolean acquireLicense(String contentId, String keyServerUrl) {
        if (contentId == null || contentId.isEmpty() || keyServerUrl == null || keyServerUrl.isEmpty()) {
            System.err.println("Invalid content ID or key server URL.");
            return false;
        }
        System.out.println("[Microsoft PlayReady] Acquired SL3000 hardware license for: " + contentId + " from: " + keyServerUrl);
        return true;
    }

    @Override
    public byte[] decryptPayload(byte[] encryptedBytes) {
        if (encryptedBytes == null || encryptedBytes.length == 0) return new byte[0];
        System.out.println("[Microsoft PlayReady] Decrypted " + encryptedBytes.length + " bytes via PlayReady Secure Path.");
        return new byte[encryptedBytes.length];
    }
}