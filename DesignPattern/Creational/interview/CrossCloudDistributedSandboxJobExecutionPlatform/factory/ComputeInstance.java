package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.prototype.Prototype;

import java.util.Map;

public interface ComputeInstance extends Prototype<ComputeInstance> {
    String getInstanceId();

    int getCpuCores();

    int getRamGb();

    String getSubnetCidr();

    Map<String, String> getTags();

    boolean isMonitoringEnabled();

    StorageVolume getAttachedVolume();

    void attachVolume(StorageVolume volume);

    void executeTask(String script);
}
