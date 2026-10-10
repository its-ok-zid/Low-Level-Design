package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.ComputeInstance;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.GcpComputeInstance;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.GcpStorageVolume;

public class GcpComputeInstanceBuilder extends ComputeInstanceBuilder<GcpComputeInstanceBuilder> {
    private String subnetCidr;

    @Override
    protected GcpComputeInstanceBuilder self() {
        return this;
    }

    public GcpComputeInstanceBuilder subnetCidr(String subnetCidr) {
        this.subnetCidr = subnetCidr;
        return this;
    }

    @Override
    public ComputeInstance build() {
        validate();
        if (subnetCidr == null || subnetCidr.isBlank()) {
            throw new IllegalStateException("GCP Subnet CIDR is mandatory");
        }
        return new GcpComputeInstance(
                this.instanceId, this.cpuCores, this.ramGb,
                this.subnetCidr, this.tags, this.monitoringEnabled,
                (GcpStorageVolume) this.attachedVolume
        );
    }
}