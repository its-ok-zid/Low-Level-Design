package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder.ComputeInstanceBuilder;

import java.util.ArrayList;

public class GcpInfrastructureFactory implements CloudInfrastructureFactory {


    @Override
    public ComputeInstance createComputeInstance(ComputeInstanceBuilder builder) {
        return builder.build();
    }

    @Override
    public StorageVolume createStorageVolume(String volumeId, int capacityGb) {
        return new GcpStorageVolume(volumeId, capacityGb, "gp3", new ArrayList<>());
    }
}
