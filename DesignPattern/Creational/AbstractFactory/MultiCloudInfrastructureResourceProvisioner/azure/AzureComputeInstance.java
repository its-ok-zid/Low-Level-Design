package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.ComputeInstance;

public class AzureComputeInstance implements ComputeInstance {
    private String instanceName;

    @Override
    public void provision(String instanceName, int vCpu, int ramGb) {
        this.instanceName = instanceName;
        System.out.println("Provisioning Azure VM instance: " + instanceName + " with " + vCpu + " vCPUs and " + ramGb + " GB RAM");
    }

    @Override
    public void terminate() {
        System.out.println("Terminating Azure VM instance: " + instanceName);
    }
}
