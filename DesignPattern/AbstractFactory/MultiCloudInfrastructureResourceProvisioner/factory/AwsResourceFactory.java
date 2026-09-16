package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws.AwsComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws.AwsDatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws.AwsStorageBucket;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

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
