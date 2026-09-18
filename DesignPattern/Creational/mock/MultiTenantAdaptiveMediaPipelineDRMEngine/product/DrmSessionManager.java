package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product;

public interface DrmSessionManager {
    boolean acquireLicense(String contentId, String keyServerUrl);
    byte[] decryptPayload(byte[] encryptedBytes);
}
