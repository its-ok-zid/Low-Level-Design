package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.gcp;

import DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;

public class GcpComputeInstance implements ComputeInstance {

    private String instanceName;

    @Override
    public void provision(String instanceName, int vCpu, int ramGb) {
        this.instanceName = instanceName;
        System.out.println("Provisioning GCP Compute Engine instance: " + instanceName + " with " + vCpu + " vCPUs and " + ramGb + " GB RAM");
    }

    @Override
    public void terminate() {
        System.out.println("Terminating GCP Compute Engine instance: " + instanceName);
    }
}
