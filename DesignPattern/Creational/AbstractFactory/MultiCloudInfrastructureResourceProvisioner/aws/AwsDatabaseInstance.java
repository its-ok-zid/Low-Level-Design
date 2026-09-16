package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.aws;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;

public class AwsDatabaseInstance implements DatabaseInstance {
    private String dbName;

    @Override
    public void deploy(String dbName, double storageGb) {
        this.dbName = dbName;
        System.out.println("Deploying AWS Database Instance: " + dbName + " with storage: " + storageGb + " GB");
    }

    @Override
    public String getConnectionString() {
        String connectionString = "jdbc:mysql://aws-database-instance/" + dbName;
        return connectionString;
    }
}
