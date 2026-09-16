package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product;

public interface StorageBucket {
    void createBucket(String bucketName, String region);

    boolean upload(String key, byte[] data);
}
