package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder.ComputeInstanceBuilder;

public interface CloudInfrastructureFactory {
    ComputeInstance createComputeInstance(ComputeInstanceBuilder builder);

    StorageVolume createStorageVolume(String volumeId, int capacityGb);
}
