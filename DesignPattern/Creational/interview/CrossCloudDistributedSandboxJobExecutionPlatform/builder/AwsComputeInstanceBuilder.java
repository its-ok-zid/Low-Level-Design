package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.AwsComputeInstance;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.AwsStorageVolume;

public class AwsComputeInstanceBuilder extends ComputeInstanceBuilder<AwsComputeInstanceBuilder> {

    private String subnetCidr;

    @Override
    protected AwsComputeInstanceBuilder self() {
        return this;
    }

    public AwsComputeInstanceBuilder subnetCidr(String subnetCidr) {
        this.subnetCidr = subnetCidr;
        return this;
    }

    public AwsComputeInstanceBuilder attachedVolume(AwsStorageVolume attachedVolume) {
        this.attachedVolume = attachedVolume;
        return this;
    }

    public String getSubnetCidr() {
        return subnetCidr;
    }

    @Override
    public AwsComputeInstance build() {
        validate();

        if (subnetCidr == null || subnetCidr.isBlank()) {
            throw new IllegalStateException("AWS Subnet CIDR is mandatory");
        }

        return new AwsComputeInstance(
                this.instanceId,
                this.cpuCores,
                this.ramGb,
                this.subnetCidr,
                this.tags,
                this.monitoringEnabled,
                (AwsStorageVolume) this.attachedVolume
        );
    }
}