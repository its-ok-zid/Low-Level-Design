package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public interface CloudResourceFactory {
    ComputeInstance createComputeInstance();

    StorageBucket createStorageBucket();

    DatabaseInstance createDatabaseInstance();
}
