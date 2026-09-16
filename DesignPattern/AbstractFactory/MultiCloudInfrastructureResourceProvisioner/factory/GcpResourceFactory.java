package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpDatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpStorageBucket;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public class GcpResourceFactory implements CloudResourceFactory {
    @Override
    public ComputeInstance createComputeInstance() {
        return new GcpComputeInstance();
    }

    @Override
    public StorageBucket createStorageBucket() {
        return new GcpStorageBucket();
    }

    @Override
    public DatabaseInstance createDatabaseInstance() {
        return new GcpDatabaseInstance();
    }

}
