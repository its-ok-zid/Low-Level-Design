package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.aws;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;

public class AwsComputeInstance implements ComputeInstance {
   private String instanceName;

    @Override
    public void provision(String instanceName, int vCpu, int ramGb) {
        this.instanceName = instanceName;
        System.out.println("Provisioning AWS EC2 instance: " + instanceName + " with " + vCpu + " vCPUs and " + ramGb + " GB RAM");
    }

    @Override
    public void terminate() {
        System.out.println("Terminating AWS EC2 instance: " + instanceName);
    }
}
