package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public interface CloudResourceFactory {
    ComputeInstance createComputeInstance();

    StorageBucket createStorageBucket();

    DatabaseInstance createDatabaseInstance();
}
