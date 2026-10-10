package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.ComputeInstance;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.StorageVolume;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ComputeInstanceBuilder<T extends ComputeInstanceBuilder<T>> {
    protected String instanceId;
    protected int cpuCores;
    protected int ramGb;
    protected Map<String, String> tags = new HashMap<>();
    protected boolean monitoringEnabled;
    protected StorageVolume attachedVolume;

    /**
     * Subclasses return 'this' cast to the generic parameter type T.
     */
    protected abstract T self();

    public T instanceId(String instanceId) {
        this.instanceId = instanceId;
        return self();
    }

    public T cpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
        return self();
    }

    public T ramGb(int ramGb) {
        this.ramGb = ramGb;
        return self();
    }

    public T tags(Map<String, String> tags) {
        this.tags = (tags != null) ? new HashMap<>(tags) : new HashMap<>();
        return self();
    }

    public T tag(String key, String value) {
        if (key != null && value != null) {
            this.tags.put(key, value);
        }
        return self();
    }

    public T monitoringEnabled(boolean monitoringEnabled) {
        this.monitoringEnabled = monitoringEnabled;
        return self();
    }

    public T attachedVolume(StorageVolume attachedVolume) {
        this.attachedVolume = attachedVolume;
        return self();
    }

    /**
     * Validates common constraints across all cloud providers.
     */
    protected void validate() {
        if (instanceId == null || instanceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Instance ID cannot be null or empty");
        }
        if (cpuCores <= 0) {
            throw new IllegalArgumentException("CPU cores must be greater than 0");
        }
        if (ramGb < 2 * cpuCores) {
            throw new IllegalArgumentException(
                String.format("RAM (%dGB) must be at least 2x CPU cores (%d cores requires >= %dGB)",
                    ramGb, cpuCores, cpuCores * 2)
            );
        }
    }

    /**
     * Concrete cloud builders implement this to instantiate provider-specific compute instances.
     */
    public abstract ComputeInstance build();
}