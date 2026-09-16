package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product;

public interface ComputeInstance {
    void provision(String instanceName, int vCpu, int ramGb);

    void terminate();
}
