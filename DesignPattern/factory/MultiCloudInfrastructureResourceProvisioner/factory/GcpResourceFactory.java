package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpDatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpStorageBucket;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

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
