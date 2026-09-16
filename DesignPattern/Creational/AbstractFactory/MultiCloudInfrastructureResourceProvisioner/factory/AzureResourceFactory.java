package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure.AzureComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure.AzureDatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure.AzureStorageBucket;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

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
