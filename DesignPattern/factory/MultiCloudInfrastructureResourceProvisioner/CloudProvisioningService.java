package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.factory.CloudResourceFactory;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;
import java.util.Objects;

public class CloudProvisioningService {
    private final ComputeInstance computeInstance;
    private final StorageBucket storageBucket;
    private final DatabaseInstance databaseInstance;

    public CloudProvisioningService(CloudResourceFactory factory) {
        Objects.requireNonNull(factory, "CloudResourceFactory cannot be null");
        this.computeInstance = factory.createComputeInstance();
        this.storageBucket = factory.createStorageBucket();
        this.databaseInstance = factory.createDatabaseInstance();
    }

    public void deployCompleteStack(String stackName, String region) {
        System.out.println("Deploying complete stack: " + stackName + " in region: " + region);
        computeInstance.provision(stackName + "-compute", 4, 16);
        storageBucket.createBucket(stackName + "-storage", region);
        databaseInstance.deploy(stackName + "-db", 150);
    }

    public void terminateCompleteStack(String stackName, String region) {
        System.out.println("Terminating complete stack: " + stackName + " in region: " + region);
        computeInstance.terminate();
    }

    public void uploadDataToStorage(String key, byte[] data) {
        storageBucket.upload(key, data);
    }

    public void getDatabaseConnectionString() {
        System.out.println("Database Connection String: " + databaseInstance.getConnectionString());
    }
}