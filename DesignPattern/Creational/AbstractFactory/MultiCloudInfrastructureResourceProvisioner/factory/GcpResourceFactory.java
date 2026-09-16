package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpDatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp.GcpStorageBucket;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

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
