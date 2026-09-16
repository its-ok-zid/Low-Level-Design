package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure.AzureComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure.AzureDatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure.AzureStorageBucket;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

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
