package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.prototype.Prototype;

import java.util.List;

public interface StorageVolume extends Prototype<StorageVolume> {
    String getVolumeId();

    int getCapacityGb();

    String getStorageType();

    List<String> getDiskBlocks();

    void writeData(String block);
}
