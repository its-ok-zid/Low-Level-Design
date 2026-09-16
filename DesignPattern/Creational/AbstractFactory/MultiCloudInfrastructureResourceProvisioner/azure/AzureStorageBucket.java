package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public class AzureStorageBucket implements StorageBucket {

    private String bucketName;

    @Override
    public void createBucket(String bucketName, String region) {
        this.bucketName = bucketName;
        System.out.println("Creating Azure Storage Bucket: " + bucketName + " in region: " + region);
    }

    @Override
    public boolean upload(String key, byte[] data) {
        if (key == null || key.isEmpty() || data == null || data.length == 0) {
            System.out.println("Failed to upload data to Azure Storage Bucket: " + bucketName + ". Key or data is empty.");
            return false;
        }
        System.out.println("Uploading data to Azure Storage Bucket: " + bucketName + " with key: " + key + " and size: " + data.length + " bytes");
        return true;
    }
}
