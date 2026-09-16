package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public interface CloudResourceFactory {
    ComputeInstance createComputeInstance();

    StorageBucket createStorageBucket();

    DatabaseInstance createDatabaseInstance();
}
