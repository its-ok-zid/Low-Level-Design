package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.azure.AzureComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.azure.AzureDatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.azure.AzureStorageBucket;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public class AzureResourceFactory implements CloudResourceFactory {
    @Override
    public ComputeInstance createComputeInstance() {
        return new AzureComputeInstance();
    }

    @Override
    public StorageBucket createStorageBucket() {
        return new AzureStorageBucket();
    }

    @Override
    public DatabaseInstance createDatabaseInstance() {
        return new AzureDatabaseInstance();
    }
}
