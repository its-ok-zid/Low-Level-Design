package DesignPattern.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product;

public interface DatabaseInstance {
    void deploy(String dbName, double storageGb);

    String getConnectionString();
}
