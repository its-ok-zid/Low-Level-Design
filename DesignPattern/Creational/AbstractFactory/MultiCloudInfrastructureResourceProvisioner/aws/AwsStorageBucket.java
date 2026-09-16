package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public class AwsStorageBucket implements StorageBucket {
    private String bucketName;
    @Override
    public void createBucket(String bucketName, String region) {
        this.bucketName= bucketName;
        System.out.println("Creating AWS Storage Bucket: " + bucketName + " in region: " + region);
    }

    @Override
    public boolean upload(String key, byte[] data) {
        if(key == null || key.isEmpty() || data == null || data.length == 0) {
            System.out.println("Failed to upload data to AWS Storage Bucket: " + bucketName + ". Data is empty.");
            return false;
        }
        System.out.println("Uploading data to AWS Storage Bucket: " + bucketName + " with key: " + key + " and size: " + data.length + " bytes");
        return true;
    }
}
