package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.factory;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.aws.AwsComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.aws.AwsDatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.aws.AwsStorageBucket;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;
import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.StorageBucket;

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
