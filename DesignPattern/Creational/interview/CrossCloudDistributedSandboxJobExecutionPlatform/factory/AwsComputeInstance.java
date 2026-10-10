package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import java.util.HashMap;
import java.util.Map;

public class AwsComputeInstance implements ComputeInstance {
    private final String instanceId;
    private final int cpuCores;
    private final int ramGb;
    private final String subnetCidr;
    private Map<String, String> tags;
    private boolean monitoringEnabled;
    private AwsStorageVolume attachedVolume;

    public AwsComputeInstance(String instanceId, int cpuCores, int ramGb, String subnetCidr, Map<String, String> tags, boolean monitoringEnabled, AwsStorageVolume attachedVolume) {
        this.instanceId = instanceId;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.subnetCidr = subnetCidr;
        this.tags = tags;
        this.monitoringEnabled = monitoringEnabled;
        this.attachedVolume = attachedVolume;
    }

    public AwsComputeInstance(AwsComputeInstance source, boolean deepCopy) {
        this.instanceId = source.instanceId;
        this.cpuCores = source.cpuCores;
        this.ramGb = source.ramGb;
        this.subnetCidr = source.subnetCidr;
        this.monitoringEnabled = source.monitoringEnabled;

        if (deepCopy) {
            this.attachedVolume = source.attachedVolume != null ? new AwsStorageVolume(source.attachedVolume, true) : null;
            this.tags = (source.tags != null) ? new HashMap<>(source.tags) : new HashMap<>();
        }
    }

    @Override
    public String getInstanceId() {
        return this.instanceId;
    }

    @Override
    public int getCpuCores() {
        return this.cpuCores;
    }

    @Override
    public int getRamGb() {
        return this.ramGb;
    }

    @Override
    public String getSubnetCidr() {
        return this.subnetCidr;
    }

    @Override
    public Map<String, String> getTags() {
        return this.tags;
    }

    @Override
    public boolean isMonitoringEnabled() {
        return this.monitoringEnabled;
    }

    @Override
    public StorageVolume getAttachedVolume() {
        return this.attachedVolume;
    }

    @Override
    public void attachVolume(StorageVolume volume) {
        if (volume instanceof AwsStorageVolume) {
            this.attachedVolume = (AwsStorageVolume) volume;
        } else {
            throw new IllegalArgumentException("Incompatible volume: Expected AWS EBS volume.");
        }
    }

    @Override
    public void executeTask(String script) {
        // Implementation for executing a task on the AWS compute instance
        System.out.println("Executing script on AWS Compute Instance: " + script);
    }

    @Override
    public ComputeInstance shallowCopy() {
        return new AwsComputeInstance(this, false);
    }

    @Override
    public ComputeInstance deepCopy() {
        return new AwsComputeInstance(this, true);
    }
}
