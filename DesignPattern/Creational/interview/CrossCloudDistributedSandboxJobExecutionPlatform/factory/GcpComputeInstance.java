package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import java.util.HashMap;
import java.util.Map;

public class GcpComputeInstance implements ComputeInstance {
    private final String instanceId;
    private final int cpuCores;
    private final int ramGb;
    private final String subnetCidr;
    private Map<String, String> tags;
    private boolean monitoringEnabled;
    private GcpStorageVolume attachedVolume;

    public GcpComputeInstance(String instanceId, int cpuCores, int ramGb, String subnetCidr, Map<String, String> tags, boolean monitoringEnabled, GcpStorageVolume attachedVolume) {
        this.instanceId = instanceId;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.subnetCidr = subnetCidr;
        this.tags = tags;
        this.monitoringEnabled = monitoringEnabled;
        this.attachedVolume = attachedVolume;
    }

    public GcpComputeInstance(GcpComputeInstance source, boolean deepCopy) {
        this.instanceId = source.instanceId;
        this.cpuCores = source.cpuCores;
        this.ramGb = source.ramGb;
        this.subnetCidr = source.subnetCidr;
        this.monitoringEnabled = source.monitoringEnabled;

        if (deepCopy) {
            this.attachedVolume = source.attachedVolume != null ? new GcpStorageVolume(source.attachedVolume, true) : null;
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
        if (volume instanceof GcpStorageVolume) {
            this.attachedVolume = (GcpStorageVolume) volume;
        } else {
            throw new IllegalArgumentException("Incompatible volume: Expected GCP EBS volume.");
        }
    }

    @Override
    public void executeTask(String script) {
        // Implementation for executing a task on the AWS compute instance
        System.out.println("Executing script on AWS Compute Instance: " + script);
    }

    @Override
    public ComputeInstance shallowCopy() {
        return new GcpComputeInstance(this, false);
    }

    @Override
    public ComputeInstance deepCopy() {
        return new GcpComputeInstance(this, true);
    }
}
