package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.google;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;

public class WidevineDrmSessionManager implements DrmSessionManager {
    @Override
    public boolean acquireLicense(String contentId, String keyServerUrl) {
        if (contentId == null || contentId.isEmpty() || keyServerUrl == null || keyServerUrl.isEmpty()) {
            System.err.println("Invalid content ID or key server URL.");
            return false;
        }
        System.out.println("[Google Widevine Modular] Key request dispatched for: " + contentId + " to: " + keyServerUrl);
        return true;
    }

    @Override
    public byte[] decryptPayload(byte[] encryptedBytes) {
        if (encryptedBytes == null || encryptedBytes.length == 0) return new byte[0];
        System.out.println("[Google Widevine Modular] Decrypted " + encryptedBytes.length + " bytes via OEMCrypto Level 1.");
        return new byte[encryptedBytes.length];
    }
}