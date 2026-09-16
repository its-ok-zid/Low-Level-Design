package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws.AwsComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws.AwsDatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws.AwsStorageBucket;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

public class AwsResourceFactory implements CloudResourceFactory {
    @Override
    public ComputeInstance createComputeInstance() {
        return new AwsComputeInstance();
    }

    @Override
    public StorageBucket createStorageBucket() {
        return new AwsStorageBucket();
    }

    @Override
    public DatabaseInstance createDatabaseInstance() {
        return new AwsDatabaseInstance();
    }
}
