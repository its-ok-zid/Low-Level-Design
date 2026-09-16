package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product;

public interface ComputeInstance {
    void provision(String instanceName, int vCpu, int ramGb);

    void terminate();
}
